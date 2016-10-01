package org.chesscorp.club.model.game;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.people.ClubPlayer;
import org.junit.Test;

/**
 * Test of the chess ELO rating model.
 */
public class EloRatingTest {

    @Test
    public void testEmpty() {
        EloRating rating = new EloRating();
        Assertions.assertThat(rating.toString()).isNotEmpty();
    }

    @Test
    public void testEquality() {
        ClubPlayer white = new ClubPlayer("White");
        ClubPlayer black = new ClubPlayer("Black");
        ChessGame game1 = new ChessGame(white, black);
        ChessGame game2 = new ChessGame(white, black);

        Assertions.assertThat(game1).isEqualTo(game2);

        EloRating rating11 = new EloRating(white, game1, 1210);
        EloRating rating12 = new EloRating(black, game1, 1190);
        Assertions.assertThat(rating11).isNotEqualTo(rating12);

        EloRating rating21 = new EloRating(white, game2, 1200);
        EloRating rating22 = new EloRating(black, game2, 1200);
        Assertions.assertThat(rating21)
            .isNotEqualTo(rating11)
            .isNotEqualTo(rating12)
            .isNotEqualTo(rating22);
    }

    @Test
    public void testRepresentation() {
        ClubPlayer white = new ClubPlayer("White");
        ClubPlayer black = new ClubPlayer("Black");
        ChessGame game1 = new ChessGame(white, black);
        EloRating rating1 = new EloRating(white, game1, 1210);

        Assertions.assertThat(rating1.toString())
            .isNotEmpty()
            .contains("1210");
    }

}
