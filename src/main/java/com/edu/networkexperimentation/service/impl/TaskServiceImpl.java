package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.model.domain.Task;
import com.edu.networkexperimentation.service.TaskService;
import com.edu.networkexperimentation.mapper.TaskMapper;
import org.springframework.stereotype.Service;

/**
* @author 29764
* @description 针对表【task】的数据库操作Service实现
* @createDate 2023-05-29 21:00:59
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

}




