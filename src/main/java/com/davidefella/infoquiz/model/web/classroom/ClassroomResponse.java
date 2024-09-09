package com.davidefella.infoquiz.model.web.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClassroomResponse {

    private UUID uuid;
    private String code;
    private String name;
    private String countStudents;
}
