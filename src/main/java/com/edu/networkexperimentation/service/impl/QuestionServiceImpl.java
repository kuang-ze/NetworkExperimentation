package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.model.domain.Question;
import com.edu.networkexperimentation.service.QuestionService;
import com.edu.networkexperimentation.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 29764
* @description 针对表【question】的数据库操作Service实现
* @createDate 2023-06-05 20:47:20
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




