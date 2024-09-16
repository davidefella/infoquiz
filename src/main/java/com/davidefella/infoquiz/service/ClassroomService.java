package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.repository.ClassroomRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserInfoQuizService userInfoQuizService;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository, UserInfoQuizService userInfoQuizService) {
        this.classroomRepository = classroomRepository;
        this.userInfoQuizService = userInfoQuizService;
    }

    public Classroom findByUUID(UUID uuid) {
        log.info("{} Ricerca della classroom con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        Classroom classroom = classroomRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with UUID: " + uuid));
        log.info("{} Classroom trovata con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        return classroom;
    }

    public List<Classroom> saveAll(List<Classroom> classrooms) {
        log.info("{} Inizio salvataggio di {} classrooms", LogConstants.LOG_TAG_INFOQUIZ, classrooms.size());
        List<Classroom> savedClassrooms = classroomRepository.saveAll(classrooms);
        log.info("{} Salvataggio completato di {} classrooms", LogConstants.LOG_TAG_INFOQUIZ, savedClassrooms.size());
        return savedClassrooms;
    }

    public List<Classroom> findClassroomsByTeacherEmail(String email) {
        log.info("{} Ricerca delle classrooms per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, email);
        Optional<Teacher> teacherOpt = userInfoQuizService.findTeacherByEmail(email);

        if (teacherOpt.isEmpty()) {
            log.warn("{} Nessun insegnante trovato con email: {}", LogConstants.LOG_TAG_INFOQUIZ, email);
            return new ArrayList<>();
        }

        List<Classroom> classrooms = classroomRepository.findByTeachers(teacherOpt.get());
        log.info("{} Trovate {} classrooms per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, classrooms.size(), email);
        return classrooms;
    }
}
