package org.marco.authdemo.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessListener {

    @EventListener
    public void onLoginEvent(InteractiveAuthenticationSuccessEvent event) {
        log.info("A user logged in");
        log.info(event.toString());
    }

}
