package org.marco.authdemo.users.services;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.users.exceptions.UserException;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackOn = UserException.class)
    public User registerUser(@NonNull User user) throws UserException {
        // Verify that we can insert the user
        Optional<User> u = userRepository.findByFiscalCodeOrEmailOrUsername(user.getFiscalCode(), user.getEmail(), user.getUsername());
        if (u.isPresent()) {
            log.error("A user with the the given username, fiscal code or email already exists");
            throw new UserException("Duplicated user");
        }
        return userRepository.save(user);

    }

    @Transactional
    public User confirmUser(@NonNull User user) {
        user.setIsActive(Boolean.TRUE);
        return userRepository.save(user);
    }

    @Transactional
    public Optional<byte[]> findUserSecretByUserId(@NonNull Long userId) {
        return userRepository.findById(userId).flatMap(user -> Optional.ofNullable(user.getSecret()));
    }
}
