package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.contant.PaperConstant;
import com.edu.networkexperimentation.model.domain.Paper;
import com.edu.networkexperimentation.model.domain.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponsePaper {
    private Long id;
    private String title;
    private List<ResponseQuestion> xzQuestions;
    private List<ResponseQuestion> pdQuestions;
    private List<ResponseQuestion> tkQuestions;

    public ResponsePaper(Paper paper) {
        id = paper.getId();
        title = paper.getTitle();
    }

    public boolean addXzQuestion(Question question) {
        if (xzQuestions == null) xzQuestions = new ArrayList<>();
        if (question.getType().equals(PaperConstant.XZ_TYPE)) {
            xzQuestions.add(new ResponseQuestion(question));
            return true;
        }
        return false;
    }

    public boolean addPdQuestion(Question question) {
        if (pdQuestions == null) pdQuestions = new ArrayList<>();
        if (question.getType().equals(PaperConstant.PD_TYPE)) {
            pdQuestions.add(new ResponseQuestion(question));
            return true;
        }
        return false;
    }

    public boolean addTkQuestion(Question question) {
        if (tkQuestions == null) tkQuestions = new ArrayList<>();
        if (question.getType().equals(PaperConstant.TK_TYPE)) {
            tkQuestions.add(new ResponseQuestion(question));
            return true;
        }
        return false;
    }

}
