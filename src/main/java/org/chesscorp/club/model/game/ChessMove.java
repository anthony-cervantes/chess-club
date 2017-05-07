package org.chesscorp.club.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Chess game single move.
 *
 * @author Yannick Kirschhoffer <alcibiade@alcibiade.org>
 */
@Entity
@Proxy(lazy = false)
@GenericGenerator(
    name = "chess_move_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "chess_move_seq"),
        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
        @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
    }
)
public class ChessMove {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chess_move_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GAME_ID", nullable = false)
    @JsonIgnore
    private ChessGame game;
    @Column(nullable = false, length = 12)
    private String pgn;
    @Column(nullable = false)
    private OffsetDateTime date;

    public ChessMove() {
    }

    public ChessMove(ChessGame game, OffsetDateTime date, String pgn) {
        this.game = game;
        this.pgn = pgn;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getPgn() {
        return pgn;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public ChessGame getGame() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessMove chessMove = (ChessMove) o;

        return (id != null ? id.equals(chessMove.id) : chessMove.id == null)
            && game.equals(chessMove.game)
            && pgn.equals(chessMove.pgn)
            && date.equals(chessMove.date);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + game.hashCode();
        result = 31 * result + pgn.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ChessMove{" +
            "id=" + id +
            ", pgn='" + pgn + '\'' +
            ", date=" + date +
            '}';
    }
}
