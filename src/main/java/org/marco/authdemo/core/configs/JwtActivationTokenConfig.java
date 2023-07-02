package org.marco.authdemo.core.configs;

import com.auth0.jwt.algorithms.Algorithm;
import org.marco.authdemo.core.repositories.ActivationTokenRepository;
import org.marco.authdemo.core.services.users.ActivationJwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class JwtActivationTokenConfig {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration-interval}")
    private long expirationInterval;

    @Bean
    public ActivationJwtTokenService activationJwtTokenService(Clock clock, ActivationTokenRepository activationTokenRepository) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return new ActivationJwtTokenService(issuer, expirationInterval, clock, activationTokenRepository, algorithm);
    }

}
