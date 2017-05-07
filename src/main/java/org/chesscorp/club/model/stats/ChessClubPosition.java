package org.chesscorp.club.model.stats;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Chess position reference.
 */
@Entity
@Table(name = "chess_position")
@GenericGenerator(
    name = "chess_position_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "chess_position_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class ChessClubPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chess_position_seq")
    private Long id;

    @Column(length = 70, unique = true, nullable = false)
    private String text;

    @Column(nullable = true)
    private Integer score;

    @Column(length = 128, nullable = true)
    private String expected;

    public ChessClubPosition() {
    }

    public ChessClubPosition(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "ChessClubPosition{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", score=" + score +
            ", expected='" + expected + '\'' +
            '}';
    }
}
