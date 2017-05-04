package org.chesscorp.club.controllers;

import org.chesscorp.club.Application;
import org.chesscorp.club.exception.AuthenticationFailedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
@ActiveProfiles("validation")
public class AuthenticationControllerValidationTest {

    @Autowired
    private AuthenticationController authenticationController;

    @Test(expected = AuthenticationFailedException.class)
    @Transactional
    public void testSignUpAndSignIn() {
        authenticationController.signup("a@b.c", "Password1", "A");
        authenticationController.signin("a@b.c", "Password1");
    }

    @Test
    @Transactional
    public void testSignUp() {
        authenticationController.signup("a@b.c", "Password1", "A");
    }
}
