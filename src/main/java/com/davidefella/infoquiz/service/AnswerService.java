package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Optional<Answer> findByUUID(UUID uuid) {
        return answerRepository.findByUuid(uuid);
    }

    //TODO: Rimuovere
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> saveAll(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }

}


