package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.mapper.ReplyMapper;
import com.edu.networkexperimentation.model.domain.Discussion;
import com.edu.networkexperimentation.model.domain.Reply;
import com.edu.networkexperimentation.model.request.ResponseDiscussion;
import com.edu.networkexperimentation.service.DiscussionService;
import com.edu.networkexperimentation.mapper.DiscussionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 29764
* @description 针对表【discussion】的数据库操作Service实现
* @createDate 2023-05-29 21:00:59
*/
@Service
public class DiscussionServiceImpl extends ServiceImpl<DiscussionMapper, Discussion>
    implements DiscussionService{

    @Resource
    private ReplyMapper replyMapper;

    @Override
    public List<ResponseDiscussion> getAllDiscussion() {
        List<Discussion> discussions = this.list(null);
        List<ResponseDiscussion> responseDiscussions = new ArrayList<>();
        discussions.forEach(item->{
            ResponseDiscussion discussion = new ResponseDiscussion(item);
            QueryWrapper<Reply> wrapper = new QueryWrapper<>();
            wrapper.eq("belongDiscussionID", item.getId());
            List<Reply> replies = replyMapper.selectList(wrapper);
            replies.forEach(discussion::addReply);
            responseDiscussions.add(discussion);
        });
        return responseDiscussions;
    }
}




