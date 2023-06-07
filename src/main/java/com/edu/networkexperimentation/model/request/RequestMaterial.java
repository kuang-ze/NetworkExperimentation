package com.edu.networkexperimentation.model.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestMaterial {
    private Long sectionID;
    private String title;
}
