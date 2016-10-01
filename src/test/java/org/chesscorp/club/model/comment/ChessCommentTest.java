package org.chesscorp.club.model.comment;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.game.ChessGame;
import org.chesscorp.club.model.people.ClubPlayer;
import org.junit.Test;

import java.time.OffsetDateTime;

/**
 * Test of the chess comment model.
 */
public class ChessCommentTest {

    @Test
    public void testEmpty() {
        ChessComment comment = new ChessComment();
        Assertions.assertThat(comment.toString()).isNotEmpty();
    }

    @Test
    public void testEquality() {
        OffsetDateTime time = OffsetDateTime.now();
        ClubPlayer white = new ClubPlayer("White");
        ClubPlayer black = new ClubPlayer("Black");
        ClubPlayer gray = new ClubPlayer("Gray");
        ChessGame game1 = new ChessGame(white, black);
        ChessGame game2 = new ChessGame(white, black);

        ChessComment comment11 = new ChessComment(white, game1, time, "Nice move !");
        ChessComment comment12 = new ChessComment(gray, game1, time, "Nice move !");
        ChessComment comment2 = new ChessComment(white, game2, time, "Nice move !");

        Assertions.assertThat(comment11).isNotEqualTo(comment12);
        Assertions.assertThat(comment2).isNotEqualTo(comment11);
    }

    @Test
    public void testRepresentation() {
        OffsetDateTime time = OffsetDateTime.now();
        ClubPlayer white = new ClubPlayer("White");
        ClubPlayer black = new ClubPlayer("Black");
        ChessGame game1 = new ChessGame(white, black);

        ChessComment comment = new ChessComment(white, game1, time, "Nice move !");
        Assertions.assertThat(comment.toString())
            .isNotEmpty()
            .contains("White");
    }

}
