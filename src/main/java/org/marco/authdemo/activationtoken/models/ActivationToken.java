package org.marco.authdemo.activationtoken.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.marco.authdemo.users.models.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ACTIVATION_TOKEN")
public class ActivationToken {

    @Id
    private String token;

    @ManyToOne // No lazy loading, we need the user
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;

    @Column(name = "VERIFIED")
    private Boolean verified;

    public ActivationToken(@NonNull String token, @NonNull User user) {
        this.token = token;
        this.user = user;
        verified = Boolean.FALSE;
    }

    public boolean isTokenVerified() {
        return Boolean.TRUE.equals(getVerified());
    }
}
