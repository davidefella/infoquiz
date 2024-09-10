package com.davidefella.infoquiz.model.web.students;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StudentDTOResponse {

    private UUID uuidStudent;
    private String lastName;
    private String firstName;
}
