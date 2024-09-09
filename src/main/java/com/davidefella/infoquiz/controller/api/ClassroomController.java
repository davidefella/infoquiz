package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.model.web.classroom.ClassroomResponseWrapper;
import com.davidefella.infoquiz.model.web.classroom.mapper.ClassroomMapper;
import com.davidefella.infoquiz.model.web.classroom.ClassroomResponse;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ClassroomController {

    private ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_CLASSROOMS_V1)
    public ClassroomResponseWrapper getClassroomForTeacher(Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;

        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail(jwtAuthToken.getName());

        List<ClassroomResponse> dtoClassrooms = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            ClassroomResponse dto = ClassroomMapper.toDto(classroom);
            dtoClassrooms.add(dto);
        }

        return new ClassroomResponseWrapper(dtoClassrooms);
    }
}