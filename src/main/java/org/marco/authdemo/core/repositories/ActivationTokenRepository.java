package org.marco.authdemo.core.repositories;

import org.marco.authdemo.core.models.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, String> {
}
