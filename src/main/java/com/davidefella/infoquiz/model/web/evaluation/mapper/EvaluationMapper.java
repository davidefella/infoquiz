package com.davidefella.infoquiz.model.web.evaluation.mapper;

import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDTOResponse;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDetailsDTO;
import com.davidefella.infoquiz.model.web.question.QuestionDTOResponse;
import com.davidefella.infoquiz.model.web.question.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EvaluationMapper {

    @Autowired
    private QuestionMapper questionMapper;

    private EvaluationMapper(){}

    public static EvaluationDTOResponse toEvaluationDto(Evaluation evaluation) {
        return new EvaluationDTOResponse(
                evaluation.getUuid(),
                evaluation.getAssignedTeacher().getUuid(),
                evaluation.getEvaluationDate(),
                evaluation.getTitle(),
                evaluation.getDescription(),
                evaluation.isActive()
        );
    }

    public EvaluationDetailsDTO toEvaluationDetailsDTO(Evaluation evaluation) {
        List<QuestionDTOResponse> questionDTOs = new ArrayList<>();

        for (Question question : evaluation.getQuestions()) {
            QuestionDTOResponse questionDTO = questionMapper.toQuestionDTO(question);
            questionDTOs.add(questionDTO);
        }

        return new EvaluationDetailsDTO(
                evaluation.getUuid(),
                evaluation.getTitle(),
                questionDTOs
        );
    }

}
