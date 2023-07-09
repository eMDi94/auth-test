package org.marco.authdemo.users.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.marco.authdemo.system.gdpr.attributes.StringAttributeConverter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    public static final String FIND_BY_FISCAL_CODE = "Users.findByFiscalCode";

    public enum NotificationMethod {
        EMAIL, WHATSAPP, SMS, SLACK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "id_sequence", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    @Convert(converter = StringAttributeConverter.class)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @Convert(converter = StringAttributeConverter.class)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Convert(converter = StringAttributeConverter.class)
    private String email;

    @Column(name = "USERNAME", nullable = false, unique = true)
    @Convert(converter = StringAttributeConverter.class)
    private String username;

    @Column(name = "FISCAL_CODE", nullable = false, unique = true)
    @Convert(converter = StringAttributeConverter.class)
    private String fiscalCode;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = Boolean.FALSE;

    @Column(name = "NOTIFICATION_METHOD", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationMethod notificationMethod = NotificationMethod.EMAIL;

    @Column(name = "IS_2FA_ENABLED")
    private Boolean is2FAEnabled;

    @Lob
    @Column(name = "SECRET", columnDefinition = "BLOB")
    private byte[] secret;

    public User(String firstName, String lastName, String email, String username, String fiscalCode, String passwordHash, Boolean is2FAEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.fiscalCode = fiscalCode;
        this.passwordHash = passwordHash;
        this.is2FAEnabled = is2FAEnabled;
    }

    public boolean isUserActive() {
        return Boolean.TRUE.equals(getIsActive());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public boolean is2FAEnabled() {
        return Boolean.TRUE.equals(is2FAEnabled);
    }
}
