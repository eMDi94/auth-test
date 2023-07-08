package org.marco.authdemo.authentication.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marco.authdemo.users.repositories.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final UserRepository userRepository;

    @EventListener
    public void onLoginEvent(InteractiveAuthenticationSuccessEvent event) {
        log.info("A user logged in");
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) event.getAuthentication().getPrincipal();

        userRepository.findByUsername(principal.getUsername())
                .ifPresent(user -> log.info(user.toString()));
    }

}
