package com.davidefella.infoquiz.model.web.classroom.mapper;

import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.web.classroom.Classroom2StudentsDTOResponse;
import com.davidefella.infoquiz.model.web.classroom.ClassroomDTOResponse;
import com.davidefella.infoquiz.model.web.students.StudentDTOResponse;

import java.util.ArrayList;
import java.util.List;

public class ClassroomMapper {

    private ClassroomMapper() {
    }

    public static ClassroomDTOResponse toClassroomDto(Classroom classroom) {
        return new ClassroomDTOResponse(
                classroom.getUuid(),
                classroom.getCode(),
                classroom.getName(),
                String.valueOf(classroom.getStudents().size())
        );
    }

    public static Classroom2StudentsDTOResponse toClassroomStudentsDto(Classroom classroom) {
        return new Classroom2StudentsDTOResponse(
                classroom.getUuid(),
                convertListStudentToDTO(classroom.getStudents()));
    }

    private static List<StudentDTOResponse> convertListStudentToDTO(List<Student> students){
        List<StudentDTOResponse> studentsDTOResponse = new ArrayList<>();

        for(Student s : students){
            studentsDTOResponse.add(new StudentDTOResponse(s.getUuid(), s.getLastName(), s.getFirstName()));
        }

        return studentsDTOResponse;
    }

}