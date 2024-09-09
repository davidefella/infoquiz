package com.davidefella.infoquiz.model.web.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClassroomResponseWrapper {
    private List<ClassroomResponse> classrooms;
}
