package com.davidefella.infoquiz.model.web.classroom.mapper;

import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.web.classroom.ClassroomResponse;

public class ClassroomMapper {

    private ClassroomMapper() {
    }

    public static ClassroomResponse toDto(Classroom classroom) {
        return new ClassroomResponse(
                classroom.getUuid(),
                classroom.getCode(),
                classroom.getName(),
                String.valueOf(classroom.getStudents().size())
        );
    }

}
