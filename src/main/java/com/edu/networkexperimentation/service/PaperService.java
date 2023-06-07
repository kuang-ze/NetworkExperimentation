package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Paper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.request.RequestAnswer;
import com.edu.networkexperimentation.model.request.RequestPaper;
import com.edu.networkexperimentation.model.response.ResponseHistoryPaper;
import com.edu.networkexperimentation.model.response.ResponsePaper;

import java.util.List;

/**
* @author 29764
* @description 针对表【paper】的数据库操作Service
* @createDate 2023-06-05 20:47:20
*/
public interface PaperService extends IService<Paper> {

    /**
     * 制作试卷
     * @param requestPaper 制作试卷所需的数据
     * @return 制作好的试卷
     */
    ResponsePaper preparePaper(RequestPaper requestPaper);

    /**
     * 提交试卷
     * @param requestAnswers 试卷的问题答案数据
     */
    void submitPaper(List<RequestAnswer> requestAnswers);

    /**
     * 获得试卷的答题情况
     * @param id 试卷ID
     * @return 试卷
     */
    ResponseHistoryPaper getPaperByID(Long id);

    /**
     * 获得用户的试卷历史记录
     * @param id 用户ID
     * @return 用户的所有试卷
     */
    List<ResponseHistoryPaper> getAllPaperByUserID(Long id);

}
