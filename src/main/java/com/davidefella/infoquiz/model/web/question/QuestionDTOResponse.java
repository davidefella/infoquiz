package com.davidefella.infoquiz.model.web.question;

import com.davidefella.infoquiz.model.web.answer.AnswerDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class QuestionDTOResponse {

    private UUID questionUuid;
    private String questionText;
    private List<AnswerDTOResponse> answers;

}
