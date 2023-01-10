package com.jameseng.workshop.services;

import com.jameseng.workshop.dto.UserDTO;
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
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
    }

    /* public User findById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new ResourceNotFoundException(id));
} */
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User user = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new UserDTO(user);
    }

    public UserDTO insert(UserDTO userDto) {
        User user = new User();
        dtoToEntity(userDto, user);
        user = userRepository.save(user);
        return new UserDTO(user);
    }


    public UserDTO update(Long id, UserDTO userDto) {
        try {
            User user = userRepository.getReferenceById(id); // preparar objeto
            dtoToEntity(userDto, user);
            user = userRepository.save(user);
            return new UserDTO(user);
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

    private void dtoToEntity(UserDTO userDto, User user) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
    }
}
