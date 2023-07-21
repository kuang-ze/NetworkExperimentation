package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.response.ResponseVideo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ResourceBundle;

/**
* @author 29764
* @description 针对表【video】的数据库操作Service
* @createDate 2023-05-29 22:49:23
*/
public interface VideoService extends IService<Video> {

    List<ResponseVideo> getAllVideo();

    Long upload(MultipartFile file, String title, Long userID, Long sectionID);

    boolean deleteVideo(Long id);

    ResponseVideo getLatestVideo();
}
