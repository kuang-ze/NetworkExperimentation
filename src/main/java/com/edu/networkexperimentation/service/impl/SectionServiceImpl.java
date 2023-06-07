package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.mapper.MaterialMapper;
import com.edu.networkexperimentation.mapper.VideoMapper;
import com.edu.networkexperimentation.model.domain.Material;
import com.edu.networkexperimentation.model.domain.Section;
import com.edu.networkexperimentation.model.domain.Video;
import com.edu.networkexperimentation.model.response.ResponseMaterial;
import com.edu.networkexperimentation.model.response.ResponseVideo;
import com.edu.networkexperimentation.service.SectionService;
import com.edu.networkexperimentation.mapper.SectionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 29764
* @description 针对表【section】的数据库操作Service实现
* @createDate 2023-05-29 21:21:01
*/
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section>
    implements SectionService{

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<ResponseMaterial> getAllMaterialBySectionID(Long id) {
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        wrapper.eq("sectionID", id);
        List<Material> materials = materialMapper.selectList(wrapper);
        List<ResponseMaterial> responseMaterials = new ArrayList<>();
        materials.forEach(item->{
            responseMaterials.add(new ResponseMaterial(item));
        });
        return responseMaterials;
    }

    @Override
    public List<ResponseVideo> getAllVideoBySectionID(Long id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("sectionID", id);
        List<Video> materials = videoMapper.selectList(wrapper);
        List<ResponseVideo> responseVideos = new ArrayList<>();
        materials.forEach(item->{
            responseVideos.add(new ResponseVideo(item));
        });
        return responseVideos;
    }
}




