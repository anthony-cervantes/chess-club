package org.chesscorp.club.service;

import org.chesscorp.club.model.people.Session;
import org.chesscorp.club.model.token.Token;

/**
 * Authentication related operations.
 */
public interface AuthenticationService {

    Token signup(String email, String password, String displayName);

    String signin(String email, String password);

    void updatePassword(String email, String previousPassword, String newPassword);

    Session getSession(String token);

    void revoke(String token);

    void validateAccount(String tokenText);
}
