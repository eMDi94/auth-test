package org.marco.authdemo.controllers.users.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterUserRequest {
    @NotNull private String firstName;
    @NotNull private String lastName;
    @NotNull private String email;
    @NotNull private String username;
    @NotNull private String fiscalCode;
    @NotNull private String password;
    @NotNull private String passwordConfirmation;
    private MultipartFile document;
}
