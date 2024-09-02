package com.davidefella.infoquiz.model.persistence;

import com.davidefella.infoquiz.model.persistence.users.Teacher;
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

    @ManyToOne
    private Teacher assignedTeacher;

    public Evaluation( String code, String title, LocalDate evaluationDate, String description, Teacher assignedTeacher, boolean isActive) {
        this.code = code;
        this.title = title;
        this.evaluationDate = evaluationDate;
        this.description = description;
        this.assignedTeacher = assignedTeacher;
        this.isActive = isActive;
    }

}