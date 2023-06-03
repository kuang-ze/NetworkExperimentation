package com.edu.networkexperimentation.model.request;

import com.edu.networkexperimentation.model.domain.Section;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseSection {
    private Long id;
    private String title;

    public ResponseSection(Section section) {
        id = section.getId();
        title = section.getTitle();
    }
}
