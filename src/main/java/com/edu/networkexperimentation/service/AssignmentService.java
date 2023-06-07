package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Assignment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.response.ResponseAssignment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
* @author 29764
* @description 针对表【assignment】的数据库操作Service
* @createDate 2023-06-04 23:04:45
*/
public interface AssignmentService extends IService<Assignment> {
    List<ResponseAssignment> getAssignmentsByGradeID(Long id);

    Long addAssignment(MultipartFile file, String title, Date deadline, Long gradeID);
}
