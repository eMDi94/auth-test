package org.marco.authdemo.repositories;

import org.marco.authdemo.models.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, String> {
}
