package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

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

    public Question save(Question questionItem) {
        return questionRepository.save(questionItem);
    }

    public List<Question> findByEvaluationIdRandomized(Long evaluationId) {
        List<Question> questions = findByEvaluationId(evaluationId);
        Collections.shuffle(questions); // Randomizza le domande
        return questions;
    }

    public List<Question> findByEvaluationId(Long evaluationId) {
        return questionRepository.findByEvaluationId(evaluationId);
    }
}

