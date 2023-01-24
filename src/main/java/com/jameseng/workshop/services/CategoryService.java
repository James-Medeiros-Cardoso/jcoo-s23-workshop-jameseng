package com.jameseng.workshop.services;

import com.jameseng.workshop.dto.CategoryDTO;
import com.jameseng.workshop.entities.Category;
import com.jameseng.workshop.repositories.CategoryRepository;
import com.jameseng.workshop.services.exeptions.DatabaseException;
import com.jameseng.workshop.services.exeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "CategoryService/Entity not found. id = " + id));
        //return category.get();
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDto) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryDto.getName());
            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("CategoryService/Entity not found. id = " + id);
        }
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("CategoryService/Id not found = " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("CategoryService/Integrity violation.");
        }
    }
}