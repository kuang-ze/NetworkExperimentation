package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.Assignment;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseAssignment {
    private Long id;
    private String title;
    private Long gradeID;
    private Date deadline;

    public ResponseAssignment(Assignment assignment) {
        id = assignment.getId();
        title = assignment.getTitle();
        gradeID = assignment.getGradeID();
        deadline = assignment.getDeadline();
    }

}
