package org.marco.authdemo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    public static final String FIND_BY_FISCAL_CODE = "Users.findByFiscalCode";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "id_sequence", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "FISCAL_CODE", nullable = false, unique = true)
    private String fiscalCode;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = Boolean.FALSE;

    public User(String firstName, String lastName, String email, String username, String fiscalCode, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.fiscalCode = fiscalCode;
        this.passwordHash = passwordHash;
    }

    public boolean isUserActive() {
        return Boolean.TRUE.equals(getIsActive());
    }
}
