package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.model.request.RequestReply;
import com.edu.networkexperimentation.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("reply")
public class ReplyController {
    @Resource
    private ReplyService replyService;

    @PostMapping("/add")
    public BaseResponse<Long> addReply(@RequestBody RequestReply reply) {
        Long result = replyService.addReply(reply);
        return ResultUtils.success(result);
    }
}
