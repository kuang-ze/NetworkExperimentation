package com.edu.networkexperimentation.model.request;


import com.edu.networkexperimentation.model.domain.Reply;
import lombok.Data;

@Data
public class ResponseReply {
    private Long id;
    private String content;
    private Long publishUserID;
    private int floor;
    private int isRoot;

    public ResponseReply(Reply reply) {
        id = reply.getId();
        content = reply.getContent();
        publishUserID = reply.getPublisherID();
        floor = reply.getFloor();
        isRoot = reply.getIsRoot();
    }
}
