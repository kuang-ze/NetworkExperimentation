package com.edu.networkexperimentation.model.request;

import lombok.Data;

@Data
public class RequestPaper {
    private String title;
    private int xzSum;
    private int pdSum;
    private int tkSum;
    private Long userID;
}
