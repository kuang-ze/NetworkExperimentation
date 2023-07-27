package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.model.request.RequestTimes;
import com.edu.networkexperimentation.service.TimesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("times")
public class TimesController {
    @Resource
    private TimesService timesService;

    @PostMapping("/look")
    public BaseResponse<Boolean> look(@RequestBody RequestTimes times) {
        timesService.save(times.getStuid(),times.getChapterid(),times.getInterid());
        timesService.updateScan(times.getStuid(),times.getChapterid(),times.getInterid());
        return ResultUtils.success(true);
    }

    @PostMapping("/out_look")
    public BaseResponse<Boolean> outLook(@RequestBody RequestTimes times) {
        timesService.updateCheckout(times.getStuid(),times.getChapterid(),times.getInterid());
        return ResultUtils.success(true);
    }
}
