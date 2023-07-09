package org.marco.authdemo.userregistration.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfirmUserRegistrationRequest {
    @NotBlank(message = "Il codice fiscale è obbligatorio")
    private String fiscalCode;
    @NotBlank(message = "Contattare il supporto")
    private String token;

    public ConfirmUserRegistrationRequest(String token) {
        this.token = token;
    }
}
