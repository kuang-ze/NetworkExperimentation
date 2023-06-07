package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.model.domain.Answer;
import com.edu.networkexperimentation.service.AnswerService;
import com.edu.networkexperimentation.mapper.AnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author 29764
* @description 针对表【answer】的数据库操作Service实现
* @createDate 2023-06-05 20:47:20
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

}




