package com.edu.networkexperimentation.controller;


import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.mapper.TimesMapper;
import com.edu.networkexperimentation.model.domain.Question;
import com.edu.networkexperimentation.model.request.RequestAnswer;
import com.edu.networkexperimentation.model.request.RequestPaper;
import com.edu.networkexperimentation.model.request.RequestPaperGenetic;
import com.edu.networkexperimentation.model.response.ResponseHistoryPaper;
import com.edu.networkexperimentation.model.response.ResponsePaper;
import com.edu.networkexperimentation.service.GeneticService;
import com.edu.networkexperimentation.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("paper")
@Slf4j
public class PaperController {

    @Resource
    private PaperService paperService;

    @Resource
    private TimesMapper timesMapper;

    @Resource
    private GeneticService geneticService;

    @PostMapping("/prepare")
    public BaseResponse<ResponsePaper> preparePaper(@RequestBody RequestPaper paper) {
        log.info(paper.getTitle() + " --- " + paper.getXzSum() + " --- " +
                paper.getPdSum() + " --- " + paper.getTkSum());
        return ResultUtils.success(paperService.preparePaper(paper));
    }

    @PostMapping("/submit")
    public BaseResponse<Boolean> submitPaper(@RequestBody List<RequestAnswer> answers) {
        log.info("答案的个数: " + answers.size());
        paperService.submitPaper(answers);
        return ResultUtils.success(true);
    }

    @GetMapping("/user/{id}")
    public BaseResponse<List<ResponseHistoryPaper>> getAllHistoryPaperByUserID(@PathVariable("id") Long id) {
        return ResultUtils.success(paperService.getAllPaperByUserID(id));
    }

    @GetMapping("/info/{id}")
    public BaseResponse<ResponseHistoryPaper> getHistoryPaperByID(@PathVariable("id") Long id) {
        log.info("此接口被访问:\t" + id);
        return ResultUtils.success(paperService.getPaperByID(id));
    }

    @PostMapping("/getPaper")
    public BaseResponse<ResponsePaper> getAllPaper(@RequestBody RequestPaperGenetic requestPaperGenetic) {
//        return ResultUtils.success(timesMapper.selectList(null));
        return ResultUtils.success(paperService.preparePaperByGenetic(requestPaperGenetic));
    }
}
