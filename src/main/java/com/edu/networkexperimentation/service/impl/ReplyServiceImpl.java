package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Reply;
import com.edu.networkexperimentation.model.request.RequestReply;
import com.edu.networkexperimentation.model.response.ResponseReply;
import com.edu.networkexperimentation.service.ReplyService;
import com.edu.networkexperimentation.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 29764
* @description 针对表【reply】的数据库操作Service实现
* @createDate 2023-05-29 21:00:59
*/
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply>
    implements ReplyService{

    @Override
    public Long addReply(RequestReply reply) {
        Reply item = new Reply();
        item.setContent(reply.getContent());
        item.setBelongReplyID(reply.getBelongReplyID());
        item.setBelongDiscussionID(reply.getBelongDiscussionID());
        item.setPublisherID(reply.getPublisherUserID());
        item.setIsRoot(reply.getIsRoot());

        try {
            this.save(item);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "回复创建失败");
        }

        return item.getId();
    }

    @Override
    public List<Reply> getReply(Long id) {
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        wrapper.eq("belongReplyID", id);
        wrapper.eq("isRoot", 0);
        return this.list(wrapper);
    }
}




