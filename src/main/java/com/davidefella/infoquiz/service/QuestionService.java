package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.DuplicateUUIDException;
import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.repository.QuestionRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question questionItem) {
        log.info("{} Tentativo di salvataggio della domanda con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, questionItem.getUuid());
        try {
            Question savedQuestion = questionRepository.save(questionItem);
            log.info("{} Domanda salvata con successo con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, questionItem.getUuid());
            return savedQuestion;
        } catch (DataIntegrityViolationException ex) {
            log.error("{} Errore di duplicazione UUID per la domanda con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, questionItem.getUuid(), ex);
            throw new DuplicateUUIDException("A question with uuid " + questionItem.getUuid() + " already exists.");
        }
    }

    public List<Question> saveAll(List<Question> questions) {
        log.info("{} Inizio salvataggio di {} domande", LogConstants.LOG_TAG_INFOQUIZ, questions.size());
        List<Question> savedQuestions = questionRepository.saveAll(questions);
        log.info("{} Salvataggio completato di {} domande", LogConstants.LOG_TAG_INFOQUIZ, savedQuestions.size());
        return savedQuestions;
    }

    public List<Question> findAll() {
        log.info("{} Ricerca di tutte le domande", LogConstants.LOG_TAG_INFOQUIZ);
        List<Question> questions = questionRepository.findAll();
        log.info("{} Trovate {} domande", LogConstants.LOG_TAG_INFOQUIZ, questions.size());
        return questions;
    }

    public Optional<Question> findByUUID(UUID uuid) {
        log.info("{} Ricerca della domanda con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        Optional<Question> question = questionRepository.findByUuid(uuid);
        if (question.isPresent()) {
            log.info("{} Domanda trovata con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        } else {
            log.warn("{} Nessuna domanda trovata con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        }
        return question;
    }
}
