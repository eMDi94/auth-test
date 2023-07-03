package org.marco.authdemo.userregistration.controllers.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.marco.authdemo.validation.annotations.FieldMatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirmation", message = "The password fields must match")
})
public class RegisterUserRequest {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String fiscalCode;

    @NotNull
    @Length(min = 8, max = 32)
    private String password;

    @NotNull
    @Length(min = 8, max = 32)
    private String passwordConfirmation;

    private MultipartFile document;

    public Optional<MultipartFile> getDocument() {
        return Optional.ofNullable(document);
    }
}
