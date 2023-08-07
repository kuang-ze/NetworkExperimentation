package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.contant.PaperConstant;
import com.edu.networkexperimentation.model.domain.Paper;
import com.edu.networkexperimentation.model.domain.Question;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
public class ResponsePaper {
    private Long id;
    private String title;
    private int isAnswered;
    private List<ResponseQuestion> xzQuestions;
    private List<ResponseQuestion> pdQuestions;
    private List<ResponseQuestion> tkQuestions;

    public ResponsePaper(Paper paper) {
        id = paper.getId();
        title = paper.getTitle();
        isAnswered = paper.getIsAnswered() == null ? 0 : paper.getIsAnswered();
    }

    public boolean addXzQuestion(Question question) {
        if (xzQuestions == null) xzQuestions = new ArrayList<>();
        if (question.getType().equals(String.valueOf(PaperConstant.XZ_TYPE))) {
            log.info("生成选择题:\t" + question.getType());
            xzQuestions.add(new ResponseQuestion(question));
            return true;
        }
        return false;
    }

    public boolean addPdQuestion(Question question) {
        if (pdQuestions == null) pdQuestions = new ArrayList<>();
        if (question.getType().equals(String.valueOf(PaperConstant.PD_TYPE))) {
            log.info("生成判断题:\t" + question.getType());
            pdQuestions.add(new ResponseQuestion(question));
            return true;
        }
        return false;
    }

    public boolean addTkQuestion(Question question) {
        if (tkQuestions == null) tkQuestions = new ArrayList<>();
        if (question.getType().equals(String.valueOf(PaperConstant.TK_TYPE))) {
            log.info("生成填空题:\t" + question.getType());
            tkQuestions.add(new ResponseQuestion(question));
            return true;
        }
        return false;
    }

}
