package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.mapper.SectionMapper;
import com.edu.networkexperimentation.model.domain.Chapter;
import com.edu.networkexperimentation.model.domain.Section;
import com.edu.networkexperimentation.model.response.ResponseChapter;
import com.edu.networkexperimentation.service.ChapterService;
import com.edu.networkexperimentation.mapper.ChapterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 29764
 * @description 针对表【chapter】的数据库操作Service实现
 * @createDate 2023-05-29 21:00:59
 */
@Service
@Slf4j
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
        implements ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private SectionMapper sectionMapper;

    @Override
    public ArrayList<ResponseChapter> getAllChapter() {
        List<Chapter> chapters = chapterMapper.selectList(null);
//        log.info("number: " + chapters.size());
        ArrayList<ResponseChapter> responseChapters = new ArrayList<>();
        for (Chapter chapter : chapters) {
            ResponseChapter item = new ResponseChapter(chapter);
            QueryWrapper<Section> wrapper = new QueryWrapper<>();
            wrapper.eq("belongChapterID", chapter.getId());
            List<Section> sections = sectionMapper.selectList(wrapper);
            sections.forEach(item::addSection);
            responseChapters.add(item);
        }

        return responseChapters;
    }
}




