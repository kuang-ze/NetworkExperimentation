package com.edu.networkexperimentation.model.request;

import lombok.Data;

@Data
public class RequestAnswer {
    private String type;
    private String content;
    private Long questionID;
    private Long paperID;
}
