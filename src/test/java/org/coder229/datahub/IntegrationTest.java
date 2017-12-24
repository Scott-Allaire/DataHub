package org.coder229.datahub;

import org.coder229.datahub.model.User;
import org.coder229.datahub.repository.UserRepository;
import org.coder229.datahub.service.TokenAuthenticationService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {
    @LocalServerPort
    protected int port;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TokenAuthenticationService tokenAuthenticationService;

    protected User getUser() {
        return userRepository.findByUsername("admin")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.username = "admin";
                        newUser.password = "password";
                        newUser.enabled = true;
                        return userRepository.save(newUser);
                    });
    }

    protected String getToken() {
        return tokenAuthenticationService.makeToken(getUser().username);
    }
}
