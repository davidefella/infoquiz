package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserInfoQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InfoQuizRole infoQuizRole;

    protected UserInfoQuiz(String code, String lastName, String firstName, String email, InfoQuizRole infoQuizRole) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.code = code;
        this.creationDateTime = LocalDateTime.now();
        this.infoQuizRole = infoQuizRole;
    }

    protected UserInfoQuiz(String lastName, String firstName, String email, InfoQuizRole infoQuizRole) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.creationDateTime = LocalDateTime.now();
        this.infoQuizRole = infoQuizRole;
    }
}
