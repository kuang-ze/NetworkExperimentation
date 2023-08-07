package com.edu.networkexperimentation.model.request;

import lombok.Data;

@Data
public class RequestDiscussion {
    private String title;
    private String content;
    private Long publishUserID;
    private String publishUserName;
}
