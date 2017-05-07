package org.chesscorp.club.model.comment;

import org.chesscorp.club.model.game.ChessGame;
import org.chesscorp.club.model.people.Player;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Comment related to a chess game.
 */
@Entity
@GenericGenerator(
    name = "chess_comment_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "chess_comment_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class ChessComment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chess_comment_seq")
    private Long id;

    @ManyToOne(optional = false)
    private Player author;

    @ManyToOne(optional = false)
    private ChessGame chessGame;

    @Column(nullable = false)
    private OffsetDateTime date;

    @Column(length = 4096, nullable = false)
    private String text;

    public ChessComment() {
    }

    public ChessComment(Player author, ChessGame chessGame, OffsetDateTime date, String text) {
        this.author = author;
        this.chessGame = chessGame;
        this.date = date;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Player getAuthor() {
        return author;
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ChessComment{" +
            "id=" + id +
            ", author=" + author +
            ", date=" + date +
            ", chessGame=" + chessGame +
            '}';
    }
}
