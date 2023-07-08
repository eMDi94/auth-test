package org.marco.authdemo.activationtoken.services;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.marco.authdemo.activationtoken.repositories.ActivationTokenRepository;

public abstract class AbstractActivationTokenService implements IActivationTokenService {

    protected final ActivationTokenRepository activationTokenRepository;

    public AbstractActivationTokenService(ActivationTokenRepository activationTokenRepository) {
        this.activationTokenRepository = activationTokenRepository;
    }


    @Override
    @Transactional
    public boolean isTokenPresent(@NonNull String token) {
        return activationTokenRepository.existsById(token);
    }

}
