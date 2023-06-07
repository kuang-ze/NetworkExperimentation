package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.Discussion;
import com.edu.networkexperimentation.model.domain.Reply;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDiscussion {
    private Long id;
    private String title;
    private String content;
    private Long publishUserID;
    private List<ResponseReply> replies;

    public ResponseDiscussion(Discussion discussion) {
        id = discussion.getId();
        title = discussion.getTitle();
        content = discussion.getContent();
        publishUserID = discussion.getPublisherID();
        replies = new ArrayList<>();
    }

    public void addReply(Reply reply) {
        replies.add(new ResponseReply(reply));
    }
}
