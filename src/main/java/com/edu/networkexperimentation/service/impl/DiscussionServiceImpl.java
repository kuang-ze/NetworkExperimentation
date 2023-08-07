package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.mapper.ReplyMapper;
import com.edu.networkexperimentation.mapper.SectionMapper;
import com.edu.networkexperimentation.model.domain.Answer;
import com.edu.networkexperimentation.model.domain.Discussion;
import com.edu.networkexperimentation.model.domain.Reply;
import com.edu.networkexperimentation.model.domain.Section;
import com.edu.networkexperimentation.model.request.RequestDiscussion;
import com.edu.networkexperimentation.model.response.ResponseDiscussion;
import com.edu.networkexperimentation.model.response.ResponseReply;
import com.edu.networkexperimentation.service.DiscussionService;
import com.edu.networkexperimentation.mapper.DiscussionMapper;
import com.edu.networkexperimentation.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 29764
 * @description 针对表【discussion】的数据库操作Service实现
 * @createDate 2023-05-29 21:00:59
 */
@Service
@Slf4j
public class DiscussionServiceImpl extends ServiceImpl<DiscussionMapper, Discussion>
        implements DiscussionService {

    @Resource
    private ReplyService replyService;

    @Resource
    private DiscussionMapper discussionMapper;


    @Override
    public List<ResponseDiscussion> getAllDiscussion() {
        QueryWrapper<Discussion> discussionQueryWrapper = new QueryWrapper<>();
        List<Discussion> discussions = discussionMapper.selectList(
                discussionQueryWrapper.orderByDesc("updateTime").last("limit 30")
        );
        List<ResponseDiscussion> responseDiscussions = new ArrayList<>();
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        discussions.forEach(item -> {
            wrapper.clear();
            wrapper.orderByDesc("updateTime").last("limit 1");
            List<Reply> replies = replyService.list(wrapper);
            ResponseDiscussion discussion = new ResponseDiscussion(item);
            discussion.setLastReplyValue(replies.get(0));
            responseDiscussions.add(discussion);
        });
        return responseDiscussions;
    }

    @Override
    public ResponseDiscussion getDiscussionByID(Long id) {
        ResponseDiscussion discussion = new ResponseDiscussion(this.getById(id));
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        wrapper.eq("belongDiscussionID", discussion.getId());
        wrapper.eq("isRoot", 1);
        List<Reply> items = replyService.list(wrapper);
        List<ResponseReply> replies = new ArrayList<>();
        items.forEach(item -> {
            ResponseReply reply = new ResponseReply(item);
            replyService.getReply(reply.getId()).forEach(reply::addReply);
            replies.add(reply);
        });
        discussion.setReplies(replies);
        return discussion;
    }

    @Override
    public Long publishDiscussion(RequestDiscussion discussion) {
        Discussion item = new Discussion();
        item.setTitle(discussion.getTitle());
        item.setContent(discussion.getContent());
        item.setPublisherID(discussion.getPublishUserID());
        item.setPublisherName(discussion.getPublishUserName());

        log.info("pub:\t" + item.getPublisherName() + "\t" + discussion.getPublishUserName());

        try {
            this.save(item);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "发表失败");
        }

        return item.getId();
    }
}




