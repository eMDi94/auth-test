package org.marco.authdemo.services;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.dto.ConfirmUserRegistrationRequest;
import org.marco.authdemo.dto.RegisterUserRequest;
import org.marco.authdemo.exceptions.ApplicationException;
import org.marco.authdemo.models.ActivationToken;
import org.marco.authdemo.models.User;
import org.marco.authdemo.repositories.UserRepository;
import org.marco.authdemo.storage.IUserDocumentStorage;
import org.marco.authdemo.storage.exceptions.StorageException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final IUserDocumentStorage userDocumentStorage;
    private final PasswordEncoder passwordEncoder;
    private final ActivationJwtTokenService activationJwtTokenService;

    @Transactional(rollbackOn = ApplicationException.class)
    public void registerUser(@NonNull RegisterUserRequest registerUserRequest) throws ApplicationException {
        // Verify that the password and the confirmation matches
        if (!registerUserRequest.getPassword().equals(registerUserRequest.getPasswordConfirmation())) {
            log.error("The password and password confirmation do not match");
            throw new ApplicationException("Password mismatch");
        }

        // Verify that we can insert the user
        Optional<User> u = userRepository.findByFiscalCodeOrEmailOrUsername(registerUserRequest.getFiscalCode(), registerUserRequest.getEmail(), registerUserRequest.getUsername());
        if (u.isPresent()) {
            log.error("A user with the the given username, fiscal code or email already exists");
            throw new ApplicationException("Duplicated user");
        }


        String encodedPassword = passwordEncoder.encode(registerUserRequest.getPassword());
        User user = new User(registerUserRequest.getFirstName(), registerUserRequest.getLastName(), registerUserRequest.getEmail(), registerUserRequest.getUsername(), registerUserRequest.getFiscalCode(), encodedPassword);
        user = userRepository.save(user);
        activationJwtTokenService.createUserToken(user);

        if (registerUserRequest.getDocument() != null) {
            try {
                userDocumentStorage.loadDocument(user, registerUserRequest.getDocument());
            } catch (StorageException exception) {
                log.error(exception.getMessage(), exception);
                throw new ApplicationException(exception);
            }
        }
    }

    public boolean tokenExists(@NonNull String token) {
        return activationJwtTokenService.isTokenPresent(token);
    }

    @Transactional(rollbackOn = ApplicationException.class)
    public void confirmUser(@NonNull ConfirmUserRegistrationRequest request) throws ApplicationException {
        ActivationToken activationToken = activationJwtTokenService.findAndVerifyActivationToken(request.getToken());

        if (!request.getFiscalCode().equals(activationToken.getUser().getFiscalCode())) {
            log.error("The token refers to another user");
            throw new ApplicationException("Invalid token");
        }

        activationToken.getUser().setIsActive(Boolean.TRUE);
        userRepository.save(activationToken.getUser());
    }

}
