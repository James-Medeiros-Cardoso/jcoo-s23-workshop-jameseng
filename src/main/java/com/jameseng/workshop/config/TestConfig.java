package com.jameseng.workshop.config;

import com.jameseng.workshop.entities.User;
import com.jameseng.workshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test") // spring.profiles.active=test
public class TestConfig implements CommandLineRunner { // para perfil de teste

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "James", "james-medeiros@hotmail.com", "51997668072", "031088");
        User u2 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u3 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "654321");
        userRepository.saveAll(Arrays.asList(u1, u2, u3));

    }
}
