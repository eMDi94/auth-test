package org.marco.authdemo.repositories;

import org.marco.authdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFiscalCode(String fiscalCode);

    Optional<User> findByFiscalCodeOrEmailOrUsername(String fiscalCode, String email, String username);
}
