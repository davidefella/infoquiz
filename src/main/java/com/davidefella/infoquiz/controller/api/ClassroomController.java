package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.model.web.classroom.*;
import com.davidefella.infoquiz.model.web.classroom.mapper.ClassroomMapper;
import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.service.ClassroomService;
import com.davidefella.infoquiz.utility.LogConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_CLASSROOMS_V1)
    public ClassroomDTOResponseWrapper getClassroomForTeacher(Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String teacherEmail = jwtAuthToken.getName();

        log.info("{} Richiesta di ottenimento classi per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, teacherEmail);

        List<Classroom> classrooms;
        try {
            classrooms = classroomService.findClassroomsByTeacherEmail(teacherEmail);
            log.debug("{} Classi trovate per l'insegnante {}: {}", LogConstants.LOG_TAG_INFOQUIZ, teacherEmail, classrooms.size());
        } catch (Exception e) {
            log.error("{} Errore nel recuperare le classi per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, teacherEmail, e);
            throw e;
        }

        List<ClassroomDTOResponse> dtoClassrooms = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            ClassroomDTOResponse dto = ClassroomMapper.toClassroomDto(classroom);
            dtoClassrooms.add(dto);
        }

        log.info("{} Restituzione di {} classi per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, dtoClassrooms.size(), teacherEmail);
        return new ClassroomDTOResponseWrapper(dtoClassrooms);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_CLASSROOMS_V1 + "/{uuid}")
    public Classroom2StudentsDTOResponseWrapper getClassroomForTeacher(@PathVariable UUID uuid) {
        log.info("{} Richiesta di ottenimento dettagli classe con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);

        Classroom classroom;
        try {
            classroom = classroomService.findByUUID(uuid);
            log.debug("{} Classe trovata con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        } catch (EntityNotFoundException e) {
            log.warn("{} Classe con UUID {} non trovata", LogConstants.LOG_TAG_INFOQUIZ, uuid);
            throw e;
        } catch (Exception e) {
            log.error("{} Errore nel recuperare la classe con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid, e);
            throw e;
        }

        Classroom2StudentsDTOResponse dtoResponse = ClassroomMapper.toClassroomStudentsDto(classroom);

        log.info("{} Restituzione dettagli classe con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        return new Classroom2StudentsDTOResponseWrapper(dtoResponse);
    }
}
