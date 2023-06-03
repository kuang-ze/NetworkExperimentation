package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.model.domain.Grade;
import com.edu.networkexperimentation.service.GradeService;
import com.edu.networkexperimentation.mapper.GradeMapper;
import org.springframework.stereotype.Service;

/**
* @author 29764
* @description 针对表【grade】的数据库操作Service实现
* @createDate 2023-05-29 21:00:59
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

}




