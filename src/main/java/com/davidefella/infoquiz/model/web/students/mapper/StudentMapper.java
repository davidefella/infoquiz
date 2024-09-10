package com.davidefella.infoquiz.model.web.students.mapper;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.web.students.StudentDTOResponse;

public class StudentMapper {

    private StudentMapper() {
    }

    public static StudentDTOResponse toStudentDto(Student student) {
        return new StudentDTOResponse(student.getUuid(),
                                      student.getLastName(),
                                      student.getFirstName());
    }
}