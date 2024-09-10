package com.davidefella.infoquiz.model.web.students;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentDTOResponseWrapper {
    private List<StudentDTOResponse> students;
}
