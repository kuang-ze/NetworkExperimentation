package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.request.RequestReply;
import com.edu.networkexperimentation.model.response.ResponseReply;

import java.util.List;

/**
* @author 29764
* @description 针对表【reply】的数据库操作Service
* @createDate 2023-05-29 21:00:59
*/
public interface ReplyService extends IService<Reply> {

    Long addReply(RequestReply reply);

    List<Reply> getReply(Long id);

    List<ResponseReply> getLatestReply();
}
