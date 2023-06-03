package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.model.request.RequestDiscussion;
import com.edu.networkexperimentation.model.request.ResponseDiscussion;
import com.edu.networkexperimentation.service.DiscussionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("discussion")
public class DiscussionController {

    @Resource
    private DiscussionService discussionService;

    @GetMapping("/all")
    public BaseResponse<List<ResponseDiscussion>> getAllDiscussion(HttpServletRequest request) {
        return ResultUtils.success(discussionService.getAllDiscussion());
    }

    @PostMapping("/publish")
    public BaseResponse<Long> publishDiscussion(@RequestBody RequestDiscussion discussion) {
        return ResultUtils.success(1L);
    }
}
