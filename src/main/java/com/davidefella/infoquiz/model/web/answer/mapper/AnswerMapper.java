package com.davidefella.infoquiz.model.web.answer.mapper;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.model.web.answer.AnswerDTOResponse;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public AnswerDTOResponse toAnswerDTO(Answer answer) {
        return new AnswerDTOResponse(
                answer.getUuid(),
                answer.getAnswerText(),
                answer.isCorrect()
        );
    }
}
