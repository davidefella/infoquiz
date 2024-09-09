package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.repository.ClassroomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    private ClassroomRepository classroomRepository;

    private UserInfoQuizService userInfoQuizService;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository, UserInfoQuizService userInfoQuizService) {
        this.classroomRepository = classroomRepository;
        this.userInfoQuizService = userInfoQuizService;
    }

    public List<Classroom> saveAll(List<Classroom> classrooms) {
        return classroomRepository.saveAll(classrooms);
    }

    public List<Classroom> findClassroomsByTeacherEmail(String email) {
        Optional<Teacher> teacherOpt = userInfoQuizService.findTeacherByEmail(email);

        if (teacherOpt.isEmpty()) {
            throw new EntityNotFoundException("Teacher not found with email: " + email);
        }

        return classroomRepository.findByTeachers(teacherOpt.get());
    }
}


