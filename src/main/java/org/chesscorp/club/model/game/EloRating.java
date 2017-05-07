package org.chesscorp.club.model.game;

import org.chesscorp.club.model.people.Player;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Player data model. Can be used for actual players as well as robot players.
 *
 * @author Yannick Kirschhoffer <alcibiade@alcibiade.org>
 */
@Entity
@GenericGenerator(
    name = "elo_rating_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "elo_rating_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class EloRating implements Comparable<EloRating> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elo_rating_seq")
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime scoreDate;

    @ManyToOne(optional = false)
    private Player player;

    @ManyToOne(optional = false)
    private ChessGame chessGame;

    @Column(nullable = false)
    private Integer eloRating;

    public EloRating() {
    }

    public EloRating(Player player, ChessGame chessGame, Integer eloRating) {
        this.player = player;
        this.chessGame = chessGame;
        this.eloRating = eloRating;
        this.scoreDate = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getScoreDate() {
        return scoreDate;
    }

    public Player getPlayer() {
        return player;
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    public Integer getEloRating() {
        return eloRating;
    }

    @Override
    public String toString() {
        return "EloRating{" +
            "id=" + id +
            ", player=" + player +
            ", chessGame=" + chessGame +
            ", eloRating=" + eloRating +
            '}';
    }

    @Override
    public int compareTo(EloRating o) {
        int result = scoreDate.compareTo(o.scoreDate);

        if (result == 0) {
            result = id.compareTo(o.id);
        }

        return result;
    }
}
