package es.uca.iw.telefonuca.ticket.domain;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @NotEmpty
    @Column
    private UUID customerLineId;

    @NotEmpty
    @Column
    private String subject;

    @NotEmpty
    @Column
    private String status;

}
