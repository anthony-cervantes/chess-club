package org.chesscorp.club.model.robot;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Chess position reference.
 */
@Entity
@Table(name = "robot_cache", indexes = {
    @Index(columnList = "engine,parameters,position", unique = true)
})
@GenericGenerator(
    name = "robot_cache_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "robot_cache_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class RobotCacheEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "robot_cache_seq")
    private Long id;

    @Column(length = 16, nullable = false)
    private String engine;

    @Column(length = 64, nullable = false)
    private String parameters;

    @Column(length = 70, nullable = false)
    private String position;

    @Column(length = 12, nullable = true)
    private String pgnMoveText;

    public RobotCacheEntry() {
    }

    public RobotCacheEntry(String engine, String parameters, String position, String pgnMoveText) {
        this.engine = engine;
        this.parameters = parameters;
        this.position = position;
        this.pgnMoveText = pgnMoveText;
    }

    public Long getId() {
        return id;
    }

    public String getEngine() {
        return engine;
    }

    public String getParameters() {
        return parameters;
    }

    public String getPosition() {
        return position;
    }

    public String getPgnMoveText() {
        return pgnMoveText;
    }

    @Override
    public String toString() {
        return "RobotCacheEntry{" +
            "id=" + id +
            ", engine='" + engine + '\'' +
            ", parameters='" + parameters + '\'' +
            ", position='" + position + '\'' +
            ", pgnMoveText='" + pgnMoveText + '\'' +
            '}';
    }
}
