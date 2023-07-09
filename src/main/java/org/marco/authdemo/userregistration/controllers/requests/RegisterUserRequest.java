package org.marco.authdemo.userregistration.controllers.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.marco.authdemo.validation.annotations.FieldMatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirmation", message = "I campi password devono coincidere")
})
public class RegisterUserRequest {
    @NotBlank(message = "Il nome è obbligatorio")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String lastName;

    @Email(message = "Email non valida")
    private String email;

    @NotBlank(message = "Username non valido")
    private String username;

    @NotBlank(message = "Codice Fiscale non inserito")
    private String fiscalCode;

    @NotBlank(message = "La password deve essere compresa tra 8 e 32 caratteri")
    @Length(min = 8, max = 32)
    private String password;

    @NotBlank(message = "La password deve essere compresa tra 8 e 32 caratteri")
    @Length(min = 8, max = 32)
    private String passwordConfirmation;

    private MultipartFile document;

    private Boolean is2FAEnabled;

    public Optional<MultipartFile> getDocument() {
        return Optional.ofNullable(document);
    }
}
