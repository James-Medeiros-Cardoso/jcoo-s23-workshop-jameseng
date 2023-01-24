package com.jameseng.workshop.services;

import com.jameseng.workshop.dto.ProductDTO;
import com.jameseng.workshop.entities.Product;
import com.jameseng.workshop.repositories.CategoryRepository;
import com.jameseng.workshop.repositories.ProductRepository;
import com.jameseng.workshop.services.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
}
