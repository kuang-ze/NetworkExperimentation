package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.model.domain.Assignment;
import com.edu.networkexperimentation.model.domain.Material;
import com.edu.networkexperimentation.model.response.ResponseAssignment;
import com.edu.networkexperimentation.service.AssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("assignment")
public class AssignmentController {

    @Resource
    private AssignmentService assignmentService;

    @GetMapping("/grade/{id}")
    public BaseResponse<List<ResponseAssignment>> getALlAssignmentByGradeID(@PathVariable("id") Long id) {
        return ResultUtils.success(assignmentService.getAssignmentsByGradeID(id));
    }

    @PostMapping("/add")
    public BaseResponse<Long> addAssignment(@RequestParam("content") MultipartFile file,
                                            @RequestParam("title") String title,
                                            @RequestParam("date")Date deadline,
                                            @RequestParam("grade") Long gradeID) {
        return ResultUtils.success(assignmentService.addAssignment(file, title, deadline, gradeID));
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Assignment file = assignmentService.getById(id);
        String realFilePath = file.getContent();
        // 获取文件输入流（把磁盘上的文件通过IO加载到程序（内存）中
        FileInputStream fis = new FileInputStream(new File(realFilePath));
        // 附件下载
        // 注意：为防止中文乱码，需要使用UTF-8
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(file.getTitle(), "UTF-8"));
        // 获取响应流（找到后需要通过Response发送回给用户
        ServletOutputStream os = response.getOutputStream();
        // 文件拷贝
        IOUtils.copy(fis, os);
        // 关闭流
        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(os);
    }

}
