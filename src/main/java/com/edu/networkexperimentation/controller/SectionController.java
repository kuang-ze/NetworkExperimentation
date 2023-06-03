package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.model.domain.Chapter;
import com.edu.networkexperimentation.model.domain.Section;
import com.edu.networkexperimentation.model.request.ResponseMaterial;
import com.edu.networkexperimentation.model.request.ResponseVideo;
import com.edu.networkexperimentation.service.SectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("section")
@CrossOrigin
public class SectionController {

    @Resource
    private SectionService sectionService;

    @GetMapping("/{id}/material")
    public BaseResponse<List<ResponseMaterial>> getAllMaterialBySectionID(@PathVariable("id") Long id){
        List<ResponseMaterial> materials = sectionService.getAllMaterialBySectionID(id);
        return ResultUtils.success(materials);
    }

    @GetMapping("/{id}/video")
    public BaseResponse<List<ResponseVideo>> getAllVideoBySectionID(@PathVariable("id") Long id){
        List<ResponseVideo> materials = sectionService.getAllVideoBySectionID(id);
        return ResultUtils.success(materials);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addChapter(@RequestParam("title") String title, @RequestParam("chapter") Long id) {
        Section section = new Section();
        section.setTitle(title);
        section.setBelongChapterID(id);
        sectionService.save(section);
        return ResultUtils.success(true);
    }
}
