package com.edu.networkexperimentation.model.response;


import com.edu.networkexperimentation.model.domain.Reply;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ResponseReply {
    private Long id;
    private String content;
    private Long publishUserID;
    private String publisherName;
    private Long belongDiscussionID;
    private Date publishTime;
    private int isRoot;
    private List<ResponseReply> replies;

    public ResponseReply(Reply reply) {
        id = reply.getId();
        content = reply.getContent();
        publishUserID = reply.getPublisherID();
        publisherName = reply.getPublisherName();
        publishTime = reply.getUpdateTime();
        isRoot = reply.getIsRoot();
        belongDiscussionID = reply.getBelongDiscussionID();
    }

    public void addReply(Reply reply) {
        if (replies == null) replies = new ArrayList<>();
        replies.add(new ResponseReply(reply));
    }

}
