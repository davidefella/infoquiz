package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.repository.AnswerRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> saveAll(List<Answer> answers) {
        log.info("{} Inizio salvataggio di {} risposte", LogConstants.LOG_TAG_INFOQUIZ, answers.size());
        List<Answer> savedAnswers = answerRepository.saveAll(answers);
        log.info("{} Salvataggio completato di {} risposte", LogConstants.LOG_TAG_INFOQUIZ, savedAnswers.size());
        return savedAnswers;
    }
}
