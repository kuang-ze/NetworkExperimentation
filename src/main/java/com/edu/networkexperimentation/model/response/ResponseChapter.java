package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.Chapter;
import com.edu.networkexperimentation.model.domain.Section;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseChapter {
    private Long id;
    private String title;
    private ArrayList<ResponseSection> sections;

    public ResponseChapter(Chapter chapter) {
        id = chapter.getId();
        title = chapter.getTitle();
        sections = new ArrayList<>();
    }

    public void addSection(Section section) {
        sections.add(new ResponseSection(section));
    }
}
