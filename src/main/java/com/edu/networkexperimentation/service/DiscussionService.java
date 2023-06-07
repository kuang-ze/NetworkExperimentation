package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Discussion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.request.RequestDiscussion;
import com.edu.networkexperimentation.model.response.ResponseDiscussion;

import java.util.List;

/**
* @author 29764
* @description 针对表【discussion】的数据库操作Service
* @createDate 2023-05-29 21:00:59
*/
public interface DiscussionService extends IService<Discussion> {
    List<ResponseDiscussion> getAllDiscussion();

    ResponseDiscussion getDiscussionByID(Long id);

    Long publishDiscussion(RequestDiscussion discussion);
}
