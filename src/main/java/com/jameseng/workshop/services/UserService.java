package com.jameseng.workshop.services;

import com.jameseng.workshop.entities.User;
import com.jameseng.workshop.repositories.UserRepository;
import com.jameseng.workshop.services.exeptions.DatabaseException;
import com.jameseng.workshop.services.exeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        try {
            User entity = userRepository.getReferenceById(id); // preparar objeto
            updateUser(entity, user);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            //e.printStackTrace();
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage()); // exceção da minha camada de serviço
        }
    }

    private void updateUser(User entity, User user) {
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
    }
}
