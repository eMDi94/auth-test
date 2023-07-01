package org.marco.authdemo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfirmUserRegistrationRequest {
    @NotNull private String fiscalCode;
    @NotNull private String token;

    public ConfirmUserRegistrationRequest(String token) {
        this.token = token;
    }
}
