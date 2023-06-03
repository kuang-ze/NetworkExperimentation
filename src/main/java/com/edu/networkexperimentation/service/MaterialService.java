package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 29764
* @description 针对表【material】的数据库操作Service
* @createDate 2023-05-27 14:16:07
*/
public interface MaterialService extends IService<Material> {
    Long upload(MultipartFile file, String title, Long userID, Long sectionID);

    Boolean deleteMaterial(Long id);


}
