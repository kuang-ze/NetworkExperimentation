package com.edu.networkexperimentation.model.request;

import com.edu.networkexperimentation.model.domain.Material;
import lombok.Data;

@Data
public class ResponseFile {
    private Long id;
    private String title;
    private Long publisherID;

    public ResponseFile(Material file) {
        id = file.getId();
        title = file.getTitle();
        publisherID = file.getUploadUser();
    }
}
