package org.chesscorp.club.model.game;

import org.alcibiade.chess.model.ChessGameStatus;
import org.chesscorp.club.model.people.Player;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Chess game data model.
 *
 * @author Yannick Kirschhoffer <alcibiade@alcibiade.org>
 */
@Entity
@Proxy(lazy = false)
@Table(
    indexes = {
        @Index(columnList = "white_player_id,black_player_id", unique = false)
    }
)
@GenericGenerator(
    name = "chess_game_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "chess_game_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class ChessGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chess_game_seq")
    private Long id;
    @ManyToOne(optional = false)
    private Player whitePlayer;
    @ManyToOne(optional = false)
    private Player blackPlayer;
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<ChessMove> moves;
    @Column(nullable = false)
    private OffsetDateTime startDate;
    @Column(nullable = false)
    private ChessGameStatus status;

    // Extra PGN informations
    @Column(nullable = true, length = 128)
    private String site;
    @Column(nullable = true, length = 128)
    private String event;
    @Column(nullable = true, length = 12)
    private String round;

    public ChessGame() {
    }

    public ChessGame(Player whitePlayer, Player blackPlayer) {
        this(whitePlayer, blackPlayer, new ArrayList<>(), ChessGameStatus.OPEN, OffsetDateTime.now());
    }

    public ChessGame(Player whitePlayer, Player blackPlayer, List<ChessMove> moves, OffsetDateTime startDate,
                     ChessGameStatus status, String site, String event, String round) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.moves = moves;
        this.startDate = startDate;
        this.status = status;
        this.site = site;
        this.event = event;
        this.round = round;
    }

    public ChessGame(Player whitePlayer, Player blackPlayer, List<ChessMove> moves, ChessGameStatus status, OffsetDateTime startDate) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.moves = moves;
        this.status = status;
        this.startDate = startDate;
    }

    public ChessGame(ChessGame game, ChessMove move, ChessGameStatus status) {
        this.id = game.id;
        this.whitePlayer = game.whitePlayer;
        this.blackPlayer = game.blackPlayer;
        this.startDate = game.getStartDate();

        // Moves is a clone of the original list that is updated
        this.moves = new ArrayList<>(game.moves);
        this.moves.add(move);

        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public List<ChessMove> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public ChessGameStatus getStatus() {
        return status;
    }

    public void setStatus(ChessGameStatus status) {
        this.status = status;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public String getSite() {
        return site;
    }

    public String getEvent() {
        return event;
    }

    public String getRound() {
        return round;
    }

    public Player getNextPlayer() {
        boolean whiteIsNext = moves.size() % 2 == 0;
        return whiteIsNext ? whitePlayer : blackPlayer;
    }

    public ChessMove addMove(OffsetDateTime moveDate, String movePgn) {
        ChessMove move = new ChessMove(this, moveDate, movePgn);
        this.moves.add(move);
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessGame chessGame = (ChessGame) o;

        return (id != null ? id.equals(chessGame.id) : chessGame.id == null)
            && whitePlayer.equals(chessGame.whitePlayer)
            && blackPlayer.equals(chessGame.blackPlayer)
            && startDate.equals(chessGame.startDate);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + whitePlayer.hashCode();
        result = 31 * result + blackPlayer.hashCode();
        result = 31 * result + startDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
            "id='" + id + '\'' +
            ", whitePlayer=" + whitePlayer +
            ", blackPlayer=" + blackPlayer +
            ", movesCount=" + moves.size() +
            ", startDate=" + startDate +
            ", status=" + status +
            '}';
    }
}
