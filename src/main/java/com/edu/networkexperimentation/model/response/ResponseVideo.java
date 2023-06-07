package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.Video;
import lombok.Data;

@Data
public class ResponseVideo {
    private Long id;
    private String title;
    private Long sectionID;
    private Long uploadUserID;

    public ResponseVideo(Video video) {
        id = video.getId();
        title = video.getTitle();
        sectionID = video.getSectionID();
        uploadUserID = video.getUploadUser();
    }

}
