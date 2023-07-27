package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Times;
import com.edu.networkexperimentation.service.TimesService;
import com.edu.networkexperimentation.mapper.TimesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 29764
 * @description 针对表【times】的数据库操作Service实现
 * @createDate 2023-07-25 21:05:44
 */
@Service
@Slf4j
public class TimesServiceImpl extends ServiceImpl<TimesMapper, Times>
        implements TimesService {
    @Resource
    private TimesMapper timesMapper;

    @Override
    public boolean save(int stuid, int chapterid, int interid) {
        try {


            List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                    .eq("stuid", stuid)
                    .eq("chapterid", chapterid)
                    .eq("interid", interid));
            Date date = new Date();
            Times times = new Times();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            times.setStuid(stuid);
            times.setChapterid(chapterid);
            times.setInterid(interid);
//            log.info("date:\t" + date + "\nformatter:\t" + formatter.format(date));
            times.setCheckin(formatter.parse(formatter.format(date)));
            if (timesList.size() == 0) {
                timesMapper.insert(times);
            } else {
                timesMapper.updateByMultiId(times);
            }
            return true;
        } catch (ParseException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    @Override
    public boolean updateScan(int stuid, int chapterid, int interid) {
        List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                .eq("stuid", stuid)
                .eq("chapterid", chapterid)
                .eq("interid", interid));
        if (timesList.size() != 0) {
            Times times = timesList.get(0);
            times.setScan(times.getScan() + 1);
            timesMapper.updateByMultiId(times);
        }
        return true;
    }

    @Override
    public boolean updateCheckout(int stuid, int chapterid, int interid) {
        try {

            List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                    .eq("stuid", stuid)
                    .eq("chapterid", chapterid)
                    .eq("interid", interid));
            if (timesList.size() != 0) {
                Times times = timesList.get(0);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                times.setCheckout(formatter.parse(formatter.format(date)));
                //退出时记录累计时长
                double hours = 0;
                Date date1 = times.getCheckin(), date2 = times.getCheckout();
                double millisecond = date2.getTime() - date1.getTime();
                hours = millisecond / (60 * 60 * 1000);
                times.setTime_length(times.getTime_length() + hours);
                timesMapper.updateByMultiId(times);
            }
            return true;
        } catch (ParseException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }


    public boolean deleteByStuid(int stuid) {
        List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                .eq("stuid", stuid));
        Iterator iter = timesList.iterator();
        while (iter.hasNext()) {
            timesMapper.deleteByMultiId((Times) iter.next());
        }
        return true;
    }


    public List<Times> getByStuid(int stuid) {
        List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                .eq("stuid", stuid));
        return timesList;
    }


    public List<Times> getByStuidAndChapterid(int stuid, int chapterid) {
        List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                .eq("stuid", stuid)
                .eq("chapterid", chapterid));
        return timesList;
    }

    @Override
    public List<Times> getByid(int stuid, int chapterid, int interid) {
        List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                .eq("stuid", stuid)
                .eq("chapterid", chapterid)
                .eq("interid", interid));
        return timesList;
    }
}




