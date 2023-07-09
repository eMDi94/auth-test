package org.marco.authdemo.userregistration.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.activationtoken.models.ActivationToken;
import org.marco.authdemo.userregistration.controllers.requests.ConfirmUserRegistrationRequest;
import org.marco.authdemo.userregistration.controllers.requests.RegisterUserRequest;
import org.marco.authdemo.userregistration.exceptions.UserRegistrationException;
import org.marco.authdemo.userregistration.services.UserRegistrationService;
import org.marco.authdemo.users.exceptions.UserException;
import org.marco.authdemo.users.exceptions.UserStorageException;
import org.marco.authdemo.users.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
@RequestMapping("/registration/")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private static class Pages {
        public static final String REGISTER_PAGE = "registration/register";
        public static final String REGISTERED_PAGE = "registration/registered";
        public static final String CONFIRM_REGISTRATION = "registration/confirm_registration";
        public static final String CONFIRMED = "registration/registration_confirmed";
    }

    private static class Redirects {
        public static final String REDIRECT_TO_REGISTER = "redirect:/registration/register";
        public static final String REDIRECT_TO_REGISTERED = "redirect:/registration/registered";
        public static final String REDIRECT_TO_CONFIRMED = "redirect:/registration/confirmed?user-id=";
        public static final String REDIRECT_TO_CONFIRM = "redirect:/registration/confirm-registration?token=";
    }

    private final UserRegistrationService userRegistrationService;

    @GetMapping("register")
    public String getRegisterUser(Model model) {
        model.addAttribute("user", new RegisterUserRequest());
        return Pages.REGISTER_PAGE;
    }

    @PostMapping("register")
    public String postRegisterUser(@Valid RegisterUserRequest user) {
        try {
            ActivationToken token = userRegistrationService.registerUser(user);
            // TODO: To change when email is implemented, short-circuited to the fiscal code page confirmation
            return Redirects.REDIRECT_TO_CONFIRM + token.getToken();
            //return Redirects.REDIRECT_TO_REGISTERED;
        } catch (UserRegistrationException | UserException | UserStorageException e) {
            log.error(e.getMessage(), e);
            return Redirects.REDIRECT_TO_REGISTER;
        }
    }

    @GetMapping("registered")
    public String registered() {
        return Pages.REGISTERED_PAGE;
    }

    @GetMapping("confirm-registration")
    public String getConfirmRegistration(Model model, @RequestParam(name = "token") String token) {
        boolean isTokenPresent = userRegistrationService.tokenExists(token);
        model.addAttribute("isTokenPresent", isTokenPresent);
        ConfirmUserRegistrationRequest request = new ConfirmUserRegistrationRequest(token);
        model.addAttribute("request", request);
        return Pages.CONFIRM_REGISTRATION;
    }

    @PostMapping("confirm-registration")
    public String confirmRegistration(@Valid ConfirmUserRegistrationRequest request) {
        try {
            User user = userRegistrationService.confirmUser(request);
            return Redirects.REDIRECT_TO_CONFIRMED + user.getId();
        } catch (UserRegistrationException e) {
            log.error(e.getMessage(), e);
            return Redirects.REDIRECT_TO_CONFIRM + request.getToken();
        }
    }

    @GetMapping("confirmed")
    public String confirmed(@RequestParam(name = "user-id") Long userId, Model model) {
        userRegistrationService.getQRCodeSecret(userId, 250, 250)
                .ifPresent(qrCode -> {
                    String imageString = Base64.getEncoder().encodeToString(qrCode);
                    model.addAttribute("qrCode", imageString);
                });
        return Pages.CONFIRMED;
    }
}
