package org.marco.authdemo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.dto.ConfirmUserRegistrationRequest;
import org.marco.authdemo.dto.RegisterUserRequest;
import org.marco.authdemo.exceptions.ApplicationException;
import org.marco.authdemo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    }

    private static class Redirects {
        public static final String REDIRECT_TO_REGISTER = "redirect:/users/register";
        public static final String REDIRECT_TO_REGISTERED = "redirect:/users/registered";
    }

    private final UserService userService;

    @GetMapping("register")
    public String getRegisterUser(Model model) {
        model.addAttribute("user", new RegisterUserRequest());
        return Pages.REGISTER_PAGE;
    }

    @PostMapping("register")
    public String postRegisterUser(@Valid RegisterUserRequest user, BindingResult bindingResult, Model model) {
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
        model.addAttribute("request", new ConfirmUserRegistrationRequest());
        return Pages.CONFIRM_REGISTRATION;
    }
}
