package org.chesscorp.club.model.game;

import org.alcibiade.chess.model.ChessGameStatus;
import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.people.ClubPlayer;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;

/**
 * Test of the chess game internal model.
 */
public class ChessGameTest {

    @Test
    public void testEquality() {
        OffsetDateTime now = OffsetDateTime.now();
        ChessGame game1 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"), new ArrayList<>(), ChessGameStatus.OPEN, now);
        ChessGame game2 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"), new ArrayList<>(), ChessGameStatus.OPEN, now);
        Assertions.assertThat(game1).isEqualTo(game2);

        ChessGame game3 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Gray"), new ArrayList<>(), ChessGameStatus.OPEN, now);
        Assertions.assertThat(game1).isEqualTo(game3);
    }

    @Test
    public void testRepresentation() {
        ChessGame game1 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        Assertions.assertThat(game1.toString())
            .isNotEmpty()
            .contains("White", "Black");
    }

}
