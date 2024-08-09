package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Rappresenta l'entità del quiz con i macro dati associati.
 */
@Data
@Entity
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String code; // Alternativo a id del DB

    private LocalDate evaluationDate;
    private String title;
    private String description;
    private boolean isActive;

    public Evaluation( String code, LocalDate evaluationDate, String title, String description, boolean isActive) {
        this.evaluationDate = evaluationDate;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
        this.code = code;
    }

}