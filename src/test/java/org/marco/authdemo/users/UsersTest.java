package org.marco.authdemo.users;

import org.junit.jupiter.api.Test;
import org.marco.authdemo.AuthdemoApplication;
import org.marco.authdemo.users.exceptions.UserException;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.repositories.UserRepository;
import org.marco.authdemo.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = AuthdemoApplication.class)
public class UsersTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRepositoryUser() {
        String encodedPassword = passwordEncoder.encode("my-super-secret-password");
        User user = new User("firstName", "lastName", "email@test.com", "username", "sono-un-codice-fiscale", encodedPassword);

        user = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.map(User::getFiscalCode).orElse(""), "sono-un-codice-fiscale");

        userRepository.delete(user);
    }

    @Test
    void testUserRegistration() throws UserException {
        String encodedPassword = passwordEncoder.encode("my-super-secret-password");
        User user = new User("firstName", "lastName", "email@test.com", "username", UUID.randomUUID().toString(), encodedPassword);

        user = userService.registerUser(user);

        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.map(User::getFiscalCode).orElse(""), "sono-un-codice-fiscale");

        userRepository.delete(user);
    }

}
