package com.davidefella.infoquiz.model.persistence;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID uuid;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER)
    private List<Student> students;

    @ManyToMany
    private List<Teacher> teachers = new ArrayList<>();  // Inizializzazione della lista

    public Classroom(UUID uuid, String code, String name, List<Student> students, List<Teacher> teachers) {
        this.uuid = uuid;
        this.code = code;
        this.name = name;
        this.students = students;
        this.teachers = teachers != null ? teachers : new ArrayList<>();  // Assicurati che non sia null
    }

    public Classroom(String code, String name, List<Student> students, List<Teacher> teachers) {
        this.uuid = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.students = students;
        this.teachers = teachers != null ? teachers : new ArrayList<>();  // Assicurati che non sia null
    }

}