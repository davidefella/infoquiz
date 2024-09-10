package com.davidefella.infoquiz.model.web.classroom;

import com.davidefella.infoquiz.model.web.students.StudentDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Classroom2StudentsDTOResponse {

    private UUID uuid;
    private List<StudentDTOResponse> students;
}
