package org.chesscorp.club.model.game;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.people.ClubPlayer;
import org.junit.Test;

import java.time.OffsetDateTime;

/**
 * Validate the move model.
 */
public class ChessMoveTest {

    @Test
    public void testEquality() {
        OffsetDateTime moveTime = OffsetDateTime.now();

        ChessGame game1 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        ChessMove move11 = game1.addMove(moveTime, "e4");
        ChessMove move12 = game1.addMove(moveTime, "e5");
        Assertions.assertThat(move11).isNotEqualTo(move12);

        ChessGame game2 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        ChessMove move21 = game2.addMove(moveTime, "e4");
        Assertions.assertThat(move11).isNotEqualTo(move21);
    }

    @Test
    public void testRepresentation() {
        ChessGame game1 = new ChessGame(new ClubPlayer("White"), new ClubPlayer("Black"));
        ChessMove move11 = game1.addMove(OffsetDateTime.now(), "e4");
        Assertions.assertThat(move11.toString()).contains("e4");
    }
}
