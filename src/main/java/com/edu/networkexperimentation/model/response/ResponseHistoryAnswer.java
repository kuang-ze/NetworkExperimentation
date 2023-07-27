package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.contant.PaperConstant;
import com.edu.networkexperimentation.model.domain.Answer;
import com.edu.networkexperimentation.model.domain.Question;
import lombok.Data;

@Data
public class ResponseHistoryAnswer {
    private Long id;
    private String title;
    private int type;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String answer;
    private String correctAnswer;
    private boolean isTrue;

    public ResponseHistoryAnswer(Question  question, Answer answer) {
        id = question.getId();
        title = question.getTitle();
        type = Integer.parseInt(question.getType());
        switch (type) {
            case PaperConstant.XZ_TYPE:
                choice1 = question.getChoice1();
                choice2 = question.getChoice2();
                choice3 = question.getChoice3();
                choice4 = question.getChoice4();
                correctAnswer = String.valueOf(question.getCorrectChoice());
                break;
            case PaperConstant.PD_TYPE:
                correctAnswer = String.valueOf(question.getIsTrue());
                break;
            case PaperConstant.TK_TYPE:
                correctAnswer = question.getCorrectContent();
                break;
        }
        this.answer = answer.getContent();
        isTrue = answer.getIsTrue() == 1;
    }

}
