package org.marco.authdemo.controllers.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.controllers.users.requests.ConfirmUserRegistrationRequest;
import org.marco.authdemo.controllers.users.requests.RegisterUserRequest;
import org.marco.authdemo.core.exceptions.ApplicationException;
import org.marco.authdemo.core.services.users.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users/")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private static class Pages {
        public static final String REGISTER_PAGE = "users/register";
        public static final String REGISTERED_PAGE = "users/registered";
        public static final String CONFIRM_REGISTRATION = "users/confirm_registration";
        public static final String CONFIRMED = "users/registration_confirmed";
    }

    private static class Redirects {
        public static final String REDIRECT_TO_REGISTER = "redirect:/users/register";
        public static final String REDIRECT_TO_REGISTERED = "redirect:/users/registered";
        public static final String REDIRECT_TO_CONFIRMED = "redirect:/users/confirmed";
        public static final String REDIRECT_TO_CONFIRM = "redirect:/users/confirm-registration?token=";
    }

    private final UserService userService;

    @GetMapping("register")
    public String getRegisterUser(Model model) {
        model.addAttribute("user", new RegisterUserRequest());
        return Pages.REGISTER_PAGE;
    }

    @PostMapping("register")
    public String postRegisterUser(@Valid RegisterUserRequest user) {
        try {
            userService.registerUser(user);
            return Redirects.REDIRECT_TO_REGISTERED;
        } catch (ApplicationException e) {
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
        boolean isTokenPresent = userService.tokenExists(token);
        model.addAttribute("isTokenPresent", isTokenPresent);
        ConfirmUserRegistrationRequest request = new ConfirmUserRegistrationRequest(token);
        model.addAttribute("request", request);
        return Pages.CONFIRM_REGISTRATION;
    }

    @PostMapping("confirm-registration")
    public String confirmRegistration(@Valid ConfirmUserRegistrationRequest request) {
        try {
            userService.confirmUser(request);
            return Redirects.REDIRECT_TO_CONFIRMED;
        } catch (ApplicationException e) {
            log.error(e.getMessage(), e);
            return Redirects.REDIRECT_TO_CONFIRM + request.getToken();
        }
    }

    @GetMapping("confirmed")
    public String confirmed() {
        return Pages.CONFIRMED;
    }
}