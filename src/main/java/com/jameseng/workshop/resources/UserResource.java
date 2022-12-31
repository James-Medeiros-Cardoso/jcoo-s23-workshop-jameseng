package com.jameseng.workshop.resources;

import com.jameseng.workshop.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findAll() {
        User user = new User(1L, "James", "james-medeiros@hotmail.com", "51997668072", "031088");
        return ResponseEntity.ok().body(user);
    }
}
