package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.DuplicateUUIDException;
import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    public Optional<Question> findByUUID(UUID uuid) {
        return questionRepository.findByUuid(uuid);
    }

    public List<Question> findByEvaluationIdRandomized(Long evaluationId) {
        List<Question> questions = findByEvaluationId(evaluationId);
        Collections.shuffle(questions); // Randomizza le domande
        return questions;
    }

    public List<Question> findByEvaluationId(Long evaluationId) {
        return questionRepository.findByEvaluationId(evaluationId);
    }

    public Optional<List<Question>> findByEvaluationUUID(UUID uuid) {
        return questionRepository.findByEvaluationUuid(uuid);
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