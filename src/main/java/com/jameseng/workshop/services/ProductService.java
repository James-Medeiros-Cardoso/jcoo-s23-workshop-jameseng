package com.jameseng.workshop.services;

import com.jameseng.workshop.dto.CategoryDTO;
import com.jameseng.workshop.dto.ProductDTO;
import com.jameseng.workshop.entities.Category;
import com.jameseng.workshop.entities.Product;
import com.jameseng.workshop.repositories.CategoryRepository;
import com.jameseng.workshop.repositories.ProductRepository;
import com.jameseng.workshop.services.exeptions.DatabaseException;
import com.jameseng.workshop.services.exeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> list = productRepository.findAll();
        return list.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        //Optional<Product> product = productRepository.findById(id);
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "ProductService/Entity not found. id = " + id));
        //return product.get();
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDto) {
        Product product = new Product();
        copyDtoToEntity(productDto, product);
        product = productRepository.save(product);
        return new ProductDTO(product, product.getCategories());
    }

    public ProductDTO update(Long id, ProductDTO productDto) {
        try {
            Product product = productRepository.getReferenceById(id);
            copyDtoToEntity(productDto, product);
            product = productRepository.save(product);
            return new ProductDTO(product, product.getCategories());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ProductService/Id not found = " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("ProductService/Integrity violation.");
        }
    }

    public void delete(Long id) {
        try {
            //Product product = productRepository.getReferenceById(id);
            //product.getCategories().clear();
            productRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ProductService/Id not found = " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("ProductService/Integrity violation.");
        }
    }

    private void copyDtoToEntity(ProductDTO productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImgUrl(productDto.getImgUrl());

        product.getCategories().clear();
        for (CategoryDTO categoryDto : productDto.getCategories()) {
            try {
                Category category = categoryRepository.getReferenceById(categoryDto.getId());
                product.getCategories().add(category);
            } catch (EntityNotFoundException e) {
                throw new ResourceNotFoundException("ProductService/Category.Id not found = " + categoryDto.getId());
            }
        }
    }
}