package com.edu.networkexperimentation.model.response;


import com.edu.networkexperimentation.model.domain.Grade;
import lombok.Data;

@Data
public class ResponseGrade {
    private Long id;
    private String title;

    public ResponseGrade(Grade grade) {
        id = grade.getId();
        title = grade.getTitle();
    }
}
