package com.edu.networkexperimentation.model.request;

import lombok.Data;

@Data
public class RequestReply {
    private String content;
    private Long publisherUserID;
    private Long belongDiscussionID;
    private Long belongReplyID;
    private int isRoot;
}
