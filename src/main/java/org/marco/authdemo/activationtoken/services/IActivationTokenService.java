package org.marco.authdemo.activationtoken.services;

import org.marco.authdemo.activationtoken.models.ActivationToken;
import org.marco.authdemo.users.models.User;

import java.util.Optional;

public interface IActivationTokenService {
    ActivationToken createUserToken(User user);
    Optional<ActivationToken> findAndVerifyActivationToken(String token);
    boolean isTokenPresent(String token);
}
