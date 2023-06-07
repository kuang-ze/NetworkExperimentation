package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.contant.PaperConstant;
import com.edu.networkexperimentation.model.domain.Question;
import lombok.Data;

@Data
public class ResponseQuestion {
    private Long id;
    private String title;
    private String type;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    public ResponseQuestion(Question question) {
        id = question.getId();
        title = question.getTitle();
        type = question.getType();
        if (question.getType().equals(PaperConstant.XZ_TYPE)) {
            choice1 = question.getChoice1();
            choice2 = question.getChoice2();
            choice3 = question.getChoice3();
            choice4 = question.getChoice4();
        }
    }

}
