package org.marco.authdemo.core.services.users;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.core.exceptions.ApplicationException;
import org.marco.authdemo.core.models.ActivationToken;
import org.marco.authdemo.core.models.User;
import org.marco.authdemo.core.repositories.ActivationTokenRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@Slf4j
public class ActivationJwtTokenService {

    public static final class CustomClaims {
        public static final String USERNAME_CLAIM = "username";
    }

    private final String issuer;
    private final long expirationIntervalInMinutes;
    private final Clock clock;
    private final ActivationTokenRepository activationTokenRepository;
    private final JWTVerifier verifier;
    private final Algorithm algorithm;

    public ActivationJwtTokenService(String issuer, long expirationIntervalInMinutes, Clock clock, ActivationTokenRepository activationTokenRepository, Algorithm algorithm) {
        this.issuer = issuer;
        this.expirationIntervalInMinutes = expirationIntervalInMinutes;
        this.clock = clock;
        this.activationTokenRepository = activationTokenRepository;
        this.algorithm = algorithm;
        verifier = JWT.require(getAlgorithm()).withIssuer(issuer).build();
    }

    @Transactional
    public ActivationToken createUserToken(@NonNull User user) {
        Instant now = getClock().instant();
        String token = JWT.create()
                .withIssuer(getIssuer())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(getExpirationIntervalInMinutes(), ChronoUnit.MINUTES))
                .withClaim(CustomClaims.USERNAME_CLAIM, user.getUsername())
                .sign(getAlgorithm());

        ActivationToken activationToken = new ActivationToken(token, user);
        return activationTokenRepository.save(activationToken);
    }

    @Transactional(rollbackOn = ApplicationException.class)
    public ActivationToken findAndVerifyActivationToken(@NonNull String token) throws ApplicationException {
        ActivationToken activationToken = activationTokenRepository.findById(token)
                .orElseThrow(() -> {
                    log.error("Activation token not found");
                    return new ApplicationException("Activation token not found");
                });

        try {
            DecodedJWT decodedJWT = getVerifier().verify(token);
            Instant expiredInstant = decodedJWT.getExpiresAtAsInstant();
            if (!expiredInstant.isAfter(getClock().instant())) {
                log.error("The token is already expired");
                throw new ApplicationException("Expired token");
            }
            if (!getIssuer().equals(decodedJWT.getIssuer())) {
                log.error("The token is not issued by us");
                throw new ApplicationException("Issuer mismatch");
            }

            User user = activationToken.getUser();
            String username = decodedJWT.getClaim(CustomClaims.USERNAME_CLAIM).asString();
            if (!user.getUsername().equals(username)) {
                log.error("Token - User mismatch");
                throw new ApplicationException("Invalid token");
            }

            activationToken.setVerified(Boolean.TRUE);
            return activationTokenRepository.save(activationToken);
        } catch (JWTVerificationException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException("Jwt token not verified");
        }
    }

    public boolean isTokenPresent(@NonNull String token) {
        return activationTokenRepository.existsById(token);
    }

}
