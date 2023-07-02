package org.marco.authdemo.users.repositories;

import org.marco.authdemo.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFiscalCodeOrEmailOrUsername(String fiscalCode, String email, String username);
    Optional<User> findByUsername(String username);
}
