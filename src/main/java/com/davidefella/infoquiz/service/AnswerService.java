package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> saveAll(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }

}


