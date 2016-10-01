package org.chesscorp.club.model.game;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.people.ClubPlayer;
import org.junit.Test;

/**
 * Test of the chess game internal model.
 */
public class ChessGameTest {

    @Test
    public void testEquality() {
        ChessGame game1 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        ChessGame game2 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        Assertions.assertThat(game1).isEqualTo(game2);

        ChessGame game3 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Gray"));
        Assertions.assertThat(game1).isNotEqualTo(game3);
    }

    @Test
    public void testRepresentation() {
        ChessGame game1 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        Assertions.assertThat(game1.toString())
            .isNotEmpty()
            .contains("White", "Black");
    }

}
