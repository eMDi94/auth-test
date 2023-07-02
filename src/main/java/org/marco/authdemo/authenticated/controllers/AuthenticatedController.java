package org.marco.authdemo.authenticated.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/authenticated/")
public class AuthenticatedController {

    private static final class Pages {
        public static final String AUTHENTICATED = "authenticated/index";
    }

    @GetMapping("index")
    public String index() {
        return Pages.AUTHENTICATED;
    }

}
