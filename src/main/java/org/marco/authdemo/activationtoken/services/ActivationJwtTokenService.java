package org.marco.authdemo.activationtoken.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.activationtoken.models.ActivationToken;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.activationtoken.repositories.ActivationTokenRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
public class ActivationJwtTokenService extends AbstractActivationTokenService {

    public static final class CustomClaims {
        public static final String USERNAME_CLAIM = "username";
    }

    private final String issuer;
    private final long expirationIntervalInMinutes;
    private final Clock clock;
    private final JWTVerifier verifier;
    private final Algorithm algorithm;

    public ActivationJwtTokenService(String issuer, long expirationIntervalInMinutes, Clock clock, ActivationTokenRepository activationTokenRepository, Algorithm algorithm) {
        super(activationTokenRepository);
        this.issuer = issuer;
        this.expirationIntervalInMinutes = expirationIntervalInMinutes;
        this.clock = clock;
        this.algorithm = algorithm;
        verifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    @Override
    @Transactional
    public ActivationToken createUserToken(@NonNull User user) {
        Instant now = clock.instant();
        String token = JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(expirationIntervalInMinutes, ChronoUnit.MINUTES))
                .withClaim(CustomClaims.USERNAME_CLAIM, user.getUsername())
                .sign(algorithm);

        ActivationToken activationToken = new ActivationToken(token, user);
        return activationTokenRepository.save(activationToken);
    }

    @Override
    @Transactional
    public Optional<ActivationToken> findAndVerifyActivationToken(@NonNull String token) {
        return activationTokenRepository.findById(token)
                .flatMap(activationToken -> {
                    try {
                        DecodedJWT decodedJWT = verifier.verify(token);
                        Instant expiredInstant = decodedJWT.getExpiresAtAsInstant();
                        if (!expiredInstant.isAfter(clock.instant())) {
                            log.error("The token is already expired");
                            return Optional.empty();
                        }
                        if (!issuer.equals(decodedJWT.getIssuer())) {
                            log.error("The token is not issued by us");
                            return Optional.empty();
                        }

                        User user = activationToken.getUser();
                        String username = decodedJWT.getClaim(CustomClaims.USERNAME_CLAIM).asString();
                        if (!user.getUsername().equals(username)) {
                            log.error("Token - User mismatch");
                            return Optional.empty();
                        }

                        activationToken.setVerified(Boolean.TRUE);
                        return Optional.of(activationTokenRepository.save(activationToken));
                    } catch (JWTVerificationException e) {
                        log.error(e.getMessage(), e);
                        return Optional.empty();
                    }
                })
                .or(() -> {
                    log.error("Token not found");
                    return Optional.empty();
                });
    }

}
