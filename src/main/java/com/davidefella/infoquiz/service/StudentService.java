package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Student;
import com.davidefella.infoquiz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findByLastNameAndFirstName(String lastName, String firstName) {
        return studentRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    /*
    * TODO: Add check fields
    * */
    public List<Student> saveAll(List<Student> students) {
        return studentRepository.saveAll(students);
    }


    public Student save(String firstName, String lastName) {

        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null");
        }

        Student student = new Student(lastName, firstName);
        return studentRepository.save(student);
    }

    /* Utile per test e DataLoader */
    public Student save(Student p ){
        return studentRepository.save(p);
    }

}
