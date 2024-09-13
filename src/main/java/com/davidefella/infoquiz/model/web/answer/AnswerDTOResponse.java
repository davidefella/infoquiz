package com.davidefella.infoquiz.model.web.answer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AnswerDTOResponse {

    private UUID answerUuid;
    private String answerText;
    private boolean isCorrect;
}
