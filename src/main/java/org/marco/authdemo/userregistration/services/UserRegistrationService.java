package org.marco.authdemo.userregistration.services;

import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.activationtoken.models.ActivationToken;
import org.marco.authdemo.activationtoken.services.IActivationTokenService;
import org.marco.authdemo.userregistration.controllers.requests.ConfirmUserRegistrationRequest;
import org.marco.authdemo.userregistration.controllers.requests.RegisterUserRequest;
import org.marco.authdemo.userregistration.exceptions.UserRegistrationException;
import org.marco.authdemo.users.exceptions.UserException;
import org.marco.authdemo.users.exceptions.UserStorageException;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.services.IUserDocumentStorage;
import org.marco.authdemo.users.services.UserSecretGenerator;
import org.marco.authdemo.users.services.UserService;
import org.marco.authdemo.users.utils.QRCodeUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserService userService;
    private final IUserDocumentStorage userDocumentStorage;
    private final IActivationTokenService activationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserSecretGenerator userSecretGenerator;

    @Transactional(rollbackOn = {UserRegistrationException.class, UserException.class, UserStorageException.class})
    public ActivationToken registerUser(@NonNull RegisterUserRequest request) throws UserRegistrationException, UserException, UserStorageException {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getUsername(), request.getFiscalCode(), encodedPassword, request.getIs2FAEnabled());
        if (user.is2FAEnabled()) {
            byte[] secret = userSecretGenerator.generateRandomSecret();
            user.setSecret(secret);
        }
        user = userService.registerUser(user);
        ActivationToken token = activationTokenService.createUserToken(user);

        if (request.getDocument().isPresent()) {
            userDocumentStorage.loadDocument(user, request.getDocument().get());
        }

        return token;
    }

    @Transactional
    public boolean tokenExists(@NonNull String token) {
        return activationTokenService.isTokenPresent(token);
    }

    @Transactional(rollbackOn = UserRegistrationException.class)
    public User confirmUser(@NonNull ConfirmUserRegistrationRequest request) throws UserRegistrationException {
        ActivationToken activationToken = activationTokenService.findAndVerifyActivationToken(request.getToken())
                .orElseThrow(() -> {
                    log.error("Token not found");
                    return new UserRegistrationException("Invalid token");
                });

        if (!request.getFiscalCode().equals(activationToken.getUser().getFiscalCode())) {
            log.error("The token refers to another user");
            throw new UserRegistrationException("Invalid token");
        }

        return userService.confirmUser(activationToken.getUser());
    }

    @Transactional
    public Optional<byte[]> getQRCodeSecret(@NonNull Long userId, int width, int height) {
        return userService.findUserSecretByUserId(userId)
                .flatMap(secret -> {
                    try {
                        byte[] qrCode = QRCodeUtils.generateQRCode(secret, width, height);
                        return Optional.of(qrCode);
                    } catch (WriterException | IOException e) {
                        log.error(e.getMessage(), e);
                        return Optional.empty();
                    }
                });
    }
}
