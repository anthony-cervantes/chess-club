package org.chesscorp.club.service;

/**
 * System initialization operations.
 */
public interface BootstrapService {

    void populate(boolean robots, boolean players, boolean games);

    void fixPgnNotationInGames();
}
