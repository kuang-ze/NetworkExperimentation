package com.edu.networkexperimentation.model.request;

import com.edu.networkexperimentation.model.domain.Material;
import lombok.Data;

@Data
public class ResponseMaterial {
    private Long id;
    private String title;
    private Long sectionID;
    private Long uploadUserID;

    public ResponseMaterial(Material material) {
        id = material.getId();
        title = material.getTitle();
        sectionID = material.getSectionID();
        uploadUserID = material.getUploadUser();
    }
}
