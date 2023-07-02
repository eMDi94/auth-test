package org.marco.authdemo.activationtoken.repositories;

import org.marco.authdemo.activationtoken.models.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, String> {
}
