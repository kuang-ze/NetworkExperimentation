package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.response.ResponseChapter;

import java.util.ArrayList;

/**
* @author 29764
* @description 针对表【chapter】的数据库操作Service
* @createDate 2023-05-29 21:00:59
*/
public interface ChapterService extends IService<Chapter> {
    ArrayList<ResponseChapter> getAllChapter();
}
