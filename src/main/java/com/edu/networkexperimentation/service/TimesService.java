package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Times;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 29764
* @description 针对表【times】的数据库操作Service
* @createDate 2023-07-25 21:05:44
*/
public interface TimesService extends IService<Times> {
    //存入数据
    boolean save(int stuid,int chapterid,int interid);
    //更新Scan
    boolean updateScan(int stuid,int chapterid,int interid);
    //更新checkout
    boolean updateCheckout(int stuid,int chapterid,int interid);
    boolean deleteByStuid(int stuid);
    List<Times> getByStuid(int stuid);
    List<Times> getByStuidAndChapterid(int stuid, int chapterid);
    List<Times> getByid(int stuid,int chapterid,int interid);
}
