package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.Section;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.request.ResponseMaterial;
import com.edu.networkexperimentation.model.request.ResponseVideo;

import java.util.List;

/**
* @author 29764
* @description 针对表【section】的数据库操作Service
* @createDate 2023-05-29 21:21:01
*/
public interface SectionService extends IService<Section> {

    List<ResponseMaterial> getAllMaterialBySectionID(Long id);

    List<ResponseVideo> getAllVideoBySectionID(Long id);

}
