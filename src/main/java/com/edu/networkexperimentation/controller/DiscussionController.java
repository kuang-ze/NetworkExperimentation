package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.contant.UserConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.request.RequestDiscussion;
import com.edu.networkexperimentation.model.response.ResponseDiscussion;
import com.edu.networkexperimentation.model.response.ResponseUser;
import com.edu.networkexperimentation.service.DiscussionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("discussion")
public class DiscussionController {

    @Resource
    private DiscussionService discussionService;

    @GetMapping("/all")
    public BaseResponse<List<ResponseDiscussion>> getAllDiscussion(HttpServletRequest request) {
        return ResultUtils.success(discussionService.getAllDiscussion());
    }

    @GetMapping("/{id}")
    public BaseResponse<ResponseDiscussion> getDiscussionById(@PathVariable("id") Long id, HttpServletRequest request) {
        return ResultUtils.success(discussionService.getDiscussionByID(id));
    }


    @PostMapping("/publish")
    public BaseResponse<Long> publishDiscussion(@RequestBody RequestDiscussion discussion,
                                                HttpServletRequest request) {
//        ResponseUser user = (ResponseUser) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
//        if (user == null) {
//            throw new BusinessException(ErrorCode.NO_AUTH, "未登录");
//        }

        Long result = discussionService.publishDiscussion(discussion);

        return ResultUtils.success(result);
    }
}
