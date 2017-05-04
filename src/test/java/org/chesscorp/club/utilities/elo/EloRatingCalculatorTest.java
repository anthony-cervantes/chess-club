package org.chesscorp.club.utilities.elo;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ELO rating tests.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
public class EloRatingCalculatorTest {

    @Autowired
    private EloRatingCalculator eloRatingCalculator;

    @Test
    public void testGameWon() {
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1200, 1200, 1.0)).isEqualTo(10);
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1000, 1200, 1.0)).isEqualTo(15);
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1500, 1200, 1.0)).isEqualTo(3);
    }

    @Test
    public void testGameLost() {
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1200, 1200, 0.0)).isEqualTo(-10);
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1000, 1200, 0.0)).isEqualTo(-5);
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1500, 1200, 0.0)).isEqualTo(-17);
    }

    @Test
    public void testGamePat() {
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1200, 1200, 0.5)).isEqualTo(0);
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1000, 1200, 0.5)).isEqualTo(5);
        Assertions.assertThat(eloRatingCalculator.computeRatingDelta(1500, 1200, 0.5)).isEqualTo(-7);
    }

}
