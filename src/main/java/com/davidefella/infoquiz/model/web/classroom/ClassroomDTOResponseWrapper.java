package com.davidefella.infoquiz.model.web.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClassroomDTOResponseWrapper {
    private List<ClassroomDTOResponse> classrooms;
}
