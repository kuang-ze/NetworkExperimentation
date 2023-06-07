package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.model.domain.Chapter;
import com.edu.networkexperimentation.model.response.ResponseChapter;
import com.edu.networkexperimentation.service.ChapterService;
import com.edu.networkexperimentation.service.SectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @Resource
    private SectionService sectionService;

    @GetMapping("/all")
    public BaseResponse<ArrayList<ResponseChapter>> searchAllChapter() {
        return ResultUtils.success(chapterService.getAllChapter());
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addChapter(@RequestParam("title") String title) {
        Chapter chapter = new Chapter();
        chapter.setTitle(title);
        chapterService.save(chapter);
        return ResultUtils.success(true);
    }

}
