package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserInfoQuizService userInfoQuizService;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository, UserInfoQuizService userInfoQuizService) {
        this.classroomRepository = classroomRepository;
        this.userInfoQuizService = userInfoQuizService;
    }

    public Optional<Classroom> findByUUID(UUID uuid){
        return classroomRepository.findByUuid(uuid);
    }

    public List<Classroom> saveAll(List<Classroom> classrooms) {
        return classroomRepository.saveAll(classrooms);
    }

    public List<Classroom> findClassroomsByTeacherEmail(String email) {
        Teacher teacher = userInfoQuizService.findTeacherByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with email: " + email));

        return classroomRepository.findByTeachers(teacher);
    }
}