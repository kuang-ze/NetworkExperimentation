package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.contant.FileConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Material;
import com.edu.networkexperimentation.model.domain.Video;
import com.edu.networkexperimentation.model.response.ResponseVideo;
import com.edu.networkexperimentation.service.VideoService;
import com.edu.networkexperimentation.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
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
 * @description 针对表【video】的数据库操作Service实现
 * @createDate 2023-05-29 22:49:23
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
        implements VideoService {

    @Override
    public List<ResponseVideo> getAllVideo() {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        List<Video> videos = this.list(wrapper.orderByDesc("updateTime"));
        List<ResponseVideo> responseVideos = new ArrayList<>();
        videos.forEach(item -> {
            responseVideos.add(new ResponseVideo(item));
        });
        return responseVideos;
    }

    @Override
    public Long upload(MultipartFile file, String title, Long userID, Long sectionID) {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "上传文件为空");
        }

        String fileName = file.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
        String dateStr = (new SimpleDateFormat("yyyy_MM_dd")).format(new Date());
        String fileDirPath = FileConstant.FILE_UPLOAD_PATH + FileConstant.VIDEO_UPLOAD + File.separator + dateStr;
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
        Video video = new Video();
        video.setTitle(title);
        video.setFile(fileDirPath + File.separator + newFileName);
        video.setUploadUser(userID);
        video.setSectionID(sectionID);
        boolean flag = this.save(video);
        if (flag) {
            log.info("已上传文件: " + newFileName);
            return video.getId();
        }

        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");
    }

    @Override
    public boolean deleteVideo(Long id) {
        Video video = this.getById(id);
        try {
            FileSystemUtils.deleteRecursively(new File(video.getFile()));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除文件失败");
        }
        this.removeById(video.getId());
        return true;
    }

    @Override
    public ResponseVideo getLatestVideo() {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        Video video = this.getOne(wrapper.orderByDesc("updateTime").last("limit 1"));
        return new ResponseVideo(video);
    }
}




