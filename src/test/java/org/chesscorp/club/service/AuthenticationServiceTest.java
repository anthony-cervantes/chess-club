package org.chesscorp.club.service;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.Application;
import org.chesscorp.club.exception.AuthenticationFailedException;
import org.chesscorp.club.exception.NotAuthenticatedException;
import org.chesscorp.club.model.people.ClubPlayer;
import org.chesscorp.club.model.people.Session;
import org.chesscorp.club.persistence.TokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenRepository tokenRepository;

    @Test(expected = NotAuthenticatedException.class)
    @Transactional
    public void testRegistration() {
        authenticationService.signup("alcibiade@alcibiade.org", "password", "Alcibiade");
        Assertions.assertThat(tokenRepository.findAll()).hasSize(1);
        String sessionToken = authenticationService.signin("alcibiade@alcibiade.org", "password");

        Session session = authenticationService.getSession(sessionToken);
        ClubPlayer player = (ClubPlayer) session.getAccount().getPlayer();

        Assertions.assertThat(session.getAccount().isValidated()).isFalse();
        Assertions.assertThat(player.getAvatarHash()).isEqualTo("8709d36fb91de42e2d9534e573a54a24");

        authenticationService.revoke(sessionToken);
        authenticationService.getSession(sessionToken);
    }

    @Test
    @Transactional
    public void testPasswordUpdate() {
        authenticationService.signup("alcibiade@alcibiade.org", "password", "Alcibiade");
        authenticationService.signin("alcibiade@alcibiade.org", "password");

        authenticationService.updatePassword("alcibiade@alcibiade.org", "password", "anotherPassword");

        try {
            authenticationService.signin("alcibiade@alcibiade.org", "password");
            Assertions.fail("Authentication should have failed with the original password");
        } catch (AuthenticationFailedException e) {
            // We expect to have this exception raised.
        }

        authenticationService.signin("alcibiade@alcibiade.org", "anotherPassword");
    }

}
