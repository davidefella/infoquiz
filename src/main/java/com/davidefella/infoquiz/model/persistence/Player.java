package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Entità del giocatore
* */
@Data
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lastName", "firstName"})})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable=false)
    private String lastName;

    @Column(nullable=false)
    @NotNull
    private String firstName;

    public Player(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}