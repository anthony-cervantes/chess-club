package org.chesscorp.club.model.token;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Single system token.
 *
 * @author Yannick Kirschhoffer <alcibiade@alcibiade.org>
 */
@Entity
@GenericGenerator(
    name = "token_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "token_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    private Long id;
    @Column(nullable = false)
    private TokenType type;
    @Column(nullable = false, length = 36)
    private String text;
    @Column(nullable = false)
    private OffsetDateTime issueDate;
    @Column(nullable = true)
    private OffsetDateTime expirationDate;
    @Column(nullable = true, length = 32)
    private String systemReference;
    @Column(nullable = false)
    private Integer usages;

    public Token() {
    }

    public Token(TokenType type, String text,
                 OffsetDateTime issueDate, OffsetDateTime expirationDate,
                 String systemReference) {
        this.type = type;
        this.text = text;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.systemReference = systemReference;
        this.usages = 0;
    }

    public Long getId() {
        return id;
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public OffsetDateTime getIssueDate() {
        return issueDate;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getSystemReference() {
        return systemReference;
    }

    public Integer getUsages() {
        return usages;
    }

    public void setUsages(Integer usages) {
        this.usages = usages;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", systemReference='" + systemReference + '\'' +
                ", usages='" + usages + '\'' +
                '}';
    }
}
