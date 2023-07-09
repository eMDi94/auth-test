package org.marco.authdemo.users.services;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class UserSecretGenerator {

    public byte[] generateRandomSecret() {
        return UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
    }

}
