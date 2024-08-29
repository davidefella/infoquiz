package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.DuplicateCodeException;
import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import javax.swing.text.html.Option;
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

    public Optional<Question> findByCode(String code) {
        return questionRepository.findByCode(code);
    }

    public List<Question> findByEvaluationIdRandomized(Long evaluationId) {
        List<Question> questions = findByEvaluationId(evaluationId);
        Collections.shuffle(questions); // Randomizza le domande
        return questions;
    }

    public List<Question> findByEvaluationId(Long evaluationId) {
        return questionRepository.findByEvaluationId(evaluationId);
    }

    public Optional<List<Question>> findByEvaluationCode(String evaluationCode) {
        return questionRepository.findByEvaluationCode(evaluationCode);
    }

    public Question save(Question questionItem) {

        try {
            return questionRepository.save(questionItem);

        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateCodeException("A question with code " + questionItem.getCode() + " already exists.");
        }
    }

    public List<Question> saveAll(List<Question> questions) {

        return questionRepository.saveAll(questions);
    }

    public void deleteById(Long id) {
         questionRepository.deleteById(id);
    }


}

