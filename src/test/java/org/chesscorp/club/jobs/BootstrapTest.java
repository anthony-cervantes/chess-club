package org.chesscorp.club.jobs;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.Application;
import org.chesscorp.club.model.people.RobotPlayer;
import org.chesscorp.club.persistence.AccountRepository;
import org.chesscorp.club.persistence.ChessGameRepository;
import org.chesscorp.club.persistence.PlayerRepository;
import org.chesscorp.club.persistence.RobotRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test the bootstrap process that initializes the storage contents.
 * <br/>
 * Note that this test has to manually cleanup all its data because the bootstrap
 * will create data out of the test's transaction scope.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Rollback(value = false)
@ActiveProfiles(profiles = "bootstrap")
public class BootstrapTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private ChessGameRepository chessGameRepository;

    @After
    @Transactional
    public void cleanup() {
        accountRepository.deleteAll();
        chessGameRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSampleData() {
        Assertions.assertThat(playerRepository.findAll())
            .hasAtLeastOneElementOfType(RobotPlayer.class);
        Assertions.assertThat(accountRepository.findAll()).isEmpty();
        Assertions.assertThat(robotRepository.findAll()).hasSize(15);
        Assertions.assertThat(chessGameRepository.findAll()).isEmpty();
    }
}
