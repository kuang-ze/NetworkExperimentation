package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.Discussion;
import com.edu.networkexperimentation.model.domain.Reply;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ResponseDiscussion {
    private Long id;
    private String title;
    private String content;
    private Long publishUserID;
    private String publishUser;
    private Date publishTime;
    private ResponseReply latestReply;
    private List<ResponseReply> replies;

    public ResponseDiscussion(Discussion discussion) {
        id = discussion.getId();
        title = discussion.getTitle();
        content = discussion.getContent();
        publishUserID = discussion.getPublisherID();
        publishUser = discussion.getPublisherName();
        publishTime = discussion.getUpdateTime();
        replies = new ArrayList<>();
    }

    public void setLastReplyValue(Reply reply) {
        latestReply = new ResponseReply(reply);
    }

    public void addReply(Reply reply) {
        replies.add(new ResponseReply(reply));
    }
}
