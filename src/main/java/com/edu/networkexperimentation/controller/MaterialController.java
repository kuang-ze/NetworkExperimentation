package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.contant.FileConstant;
import com.edu.networkexperimentation.contant.UserConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Material;
import com.edu.networkexperimentation.model.request.ResponseFile;
import com.edu.networkexperimentation.model.request.ResponseUser;
import com.edu.networkexperimentation.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@CrossOrigin
@RequestMapping("material")
@Slf4j
public class MaterialController {

    @Resource
    private MaterialService materialService;

    @PutMapping("/upload")
    public BaseResponse<Long> materialFileUpload(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("section") Long sectionID,
                                                 HttpServletRequest request) {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未上传文件");
        }

        ResponseUser user = (ResponseUser) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
//        log.info("用户是否有权限: " + (user == null ? "未登录" : user.getUserIdentity() == UserConstant.ADMIN_ROLE ? "有权限" : "无权限"));
        if (user == null || user.getUserIdentity() != UserConstant.ADMIN_ROLE) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Long uploadResult = materialService.upload(file, title, user.getId(), sectionID);

        return ResultUtils.success(uploadResult);
    }

    @GetMapping("/info/{id}")
    public BaseResponse<ResponseFile> getFile(@PathVariable Long id) {
        Material file = materialService.getById(id);
        if (file == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "无该文件");
        }
        return ResultUtils.success(new ResponseFile(file));
    }

    /**
     * 文件下载
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Material file = materialService.getById(id);
        String realFilePath = file.getFile();
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

    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteMaterial(@PathVariable Long id, HttpServletRequest request) {
        boolean flag = materialService.deleteMaterial(id);
        return ResultUtils.success(flag);
    }
}
