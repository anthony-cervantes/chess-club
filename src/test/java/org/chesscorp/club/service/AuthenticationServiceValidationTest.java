package org.chesscorp.club.service;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.Application;
import org.chesscorp.club.exception.AuthenticationFailedException;
import org.chesscorp.club.model.people.ClubPlayer;
import org.chesscorp.club.model.people.Session;
import org.chesscorp.club.model.token.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Rollback
@ActiveProfiles("validation")
public class AuthenticationServiceValidationTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test(expected = AuthenticationFailedException.class)
    @Transactional
    public void testRegistrationRefused() {
        authenticationService.signup("alcibiade@alcibiade.org", "password", "Alcibiade");
        authenticationService.signin("alcibiade@alcibiade.org", "password");
    }

    @Test
    @Transactional
    public void testRegistrationSuccess() {
        Token token = authenticationService.signup("alcibiade@alcibiade.org", "password", "Alcibiade");
        authenticationService.validateAccount(token.getText());
        String sessionToken = authenticationService.signin("alcibiade@alcibiade.org", "password");

        Session session = authenticationService.getSession(sessionToken);
        ClubPlayer player = (ClubPlayer) session.getAccount().getPlayer();

        Assertions.assertThat(session.getAccount().isValidated()).isTrue();
        Assertions.assertThat(player.getAvatarHash()).isEqualTo("8709d36fb91de42e2d9534e573a54a24");

        authenticationService.revoke(sessionToken);
    }

}
