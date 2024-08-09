package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    public List<Answer> getAllCorrectAnswerByEvaluationID(long evaluationID){
        return answerRepository.findByIsCorrectTrueAndQuestionEvaluationId(evaluationID);
    }
}


