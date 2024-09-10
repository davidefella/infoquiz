package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.DuplicateUUIDException;
import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> findByUUID(UUID uuid) {
        return questionRepository.findByUuid(uuid);
    }

    public Optional<List<Question>> findByEvaluationUUID(UUID uuid) {
        return questionRepository.findByEvaluationUuid(uuid);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question questionItem) {

        try {
            return questionRepository.save(questionItem);

        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateUUIDException("A question with uuid " + questionItem.getUuid() + " already exists.");
        }
    }

    public List<Question> saveAll(List<Question> questions) {

        return questionRepository.saveAll(questions);
    }
}