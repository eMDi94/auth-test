package org.marco.authdemo.userregistration;

import org.junit.jupiter.api.Test;
import org.marco.authdemo.AuthdemoApplication;
import org.marco.authdemo.activationtoken.models.ActivationToken;
import org.marco.authdemo.activationtoken.repositories.ActivationTokenRepository;
import org.marco.authdemo.authentication.configs.SecurityConfig;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@SpringBootTest(classes = AuthdemoApplication.class)
@AutoConfigureMockMvc
public class UserRegistrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivationTokenRepository activationTokenRepository;

    @Test
    void getRegisterUser() throws Exception {
        mockMvc
                .perform(get("/registration/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void postRegisterUser() throws Exception {
        mockMvc
                .perform(post("/registration/register")
                        .contentType("multipart/form-data")
                        .with(csrf())
                        .param("firstName", "Marco")
                        .param("lastName", "Dalai")
                        .param("email", "mdalai2394@gmail.com")
                        .param("username", "mdalai")
                        .param("fiscalCode", "DLAMRC94A23E253T")
                        .param("password", "password")
                        .param("passwordConfirmation", "password"))
                .andExpect(status().is3xxRedirection());

        User user = userRepository.findByUsername("mdalai").get();

        ActivationToken probe = new ActivationToken();
        probe.setUser(user);
        List<ActivationToken> tokens = activationTokenRepository.findAll(Example.of(probe));
        activationTokenRepository.deleteAll(tokens);
        userRepository.delete(user);
    }

}
