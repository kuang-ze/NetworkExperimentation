package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.contant.FileConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Assignment;
import com.edu.networkexperimentation.model.response.ResponseAssignment;
import com.edu.networkexperimentation.service.AssignmentService;
import com.edu.networkexperimentation.mapper.AssignmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
* @author 29764
* @description 针对表【assignment】的数据库操作Service实现
* @createDate 2023-06-04 23:04:45
*/
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment>
    implements AssignmentService{

    @Override
    public List<ResponseAssignment> getAssignmentsByGradeID(Long id) {
        QueryWrapper<Assignment> wrapper = new QueryWrapper<>();
        wrapper.eq("gradeID", id);
        List<Assignment> assignments = this.list(wrapper);
        List<ResponseAssignment> responseAssignments = new ArrayList<>();
        assignments.forEach(item-> {
            responseAssignments.add(new ResponseAssignment(item));
        });
        return responseAssignments;
    }

    @Override
    public Long addAssignment(MultipartFile file, String title, Date deadline, Long gradeID) {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "上传文件为空");
        }

        String fileName = file.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
        String dateStr = (new SimpleDateFormat("yyyy_MM_dd")).format(new Date());
        String fileDirPath = FileConstant.FILE_UPLOAD_PATH + FileConstant.ASSIGNMENT_UPLOAD + File.separator + dateStr;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            boolean flag = fileDir.mkdirs();
            if (!flag) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建目录失败");
            }
        }
        try {
            file.transferTo(new File(fileDir, newFileName));
        } catch (IOException ioException) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建文件失败");
        }
        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setContent(fileDirPath + File.separator + newFileName);
        assignment.setGradeID(gradeID);
        assignment.setDeadline(deadline);
        boolean flag = this.save(assignment);
        if (flag){
            return assignment.getId();
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");
    }
}




