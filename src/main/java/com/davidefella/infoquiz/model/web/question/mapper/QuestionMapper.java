package com.davidefella.infoquiz.model.web.question.mapper;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.model.web.answer.mapper.AnswerMapper;
import com.davidefella.infoquiz.model.web.question.QuestionDTOResponse;
import com.davidefella.infoquiz.model.web.answer.AnswerDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    public QuestionDTOResponse toQuestionDTO(Question question) {
        List<AnswerDTOResponse> answerDTOs = new ArrayList<>();

        for (Answer answer : question.getAnswers()) {
            AnswerDTOResponse answerDTO = answerMapper.toAnswerDTO(answer);
            answerDTOs.add(answerDTO);
        }

        return new QuestionDTOResponse(
                question.getUuid(),
                question.getQuestionText(),
                answerDTOs
        );
    }
}
