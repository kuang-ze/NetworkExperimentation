package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.Paper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseHistoryPaper {
    private Long id;
    private String title;
    private int isAnswered;
    private List<ResponseHistoryAnswer> xzAnswers;
    private List<ResponseHistoryAnswer> pdAnswers;
    private List<ResponseHistoryAnswer> tkAnswers;
    public ResponseHistoryPaper(Paper paper) {
        id = paper.getId();
        title = paper.getTitle();
        isAnswered = paper.getIsAnswered() == null ? 0 : paper.getIsAnswered();
    }

    public void addXzAnswer(ResponseHistoryAnswer answer) {
        if (xzAnswers == null) xzAnswers = new ArrayList<>();
        xzAnswers.add(answer);
    }

    public void addPdAnswer(ResponseHistoryAnswer answer) {
        if (pdAnswers == null) pdAnswers = new ArrayList<>();
        pdAnswers.add(answer);
    }

    public void addTkAnswer(ResponseHistoryAnswer answer) {
        if (tkAnswers == null) tkAnswers = new ArrayList<>();
        tkAnswers.add(answer);
    }

}
