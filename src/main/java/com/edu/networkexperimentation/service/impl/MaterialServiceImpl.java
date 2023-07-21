package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.contant.FileConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Material;
import com.edu.networkexperimentation.model.response.ResponseMaterial;
import com.edu.networkexperimentation.service.MaterialService;
import com.edu.networkexperimentation.mapper.MaterialMapper;
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
* @description 针对表【material】的数据库操作Service实现
* @createDate 2023-05-27 14:16:07
*/
@Service
@Slf4j
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material>
    implements MaterialService{

    @Override
    public List<ResponseMaterial> getAllMaterial() {
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        List<Material> materials = this.list(wrapper.orderByDesc("updateTime"));
        List<ResponseMaterial> responseMaterials = new ArrayList<>();
        materials.forEach(item->{
            responseMaterials.add(new ResponseMaterial(item));
        });
        return responseMaterials;
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
        String fileDirPath = FileConstant.FILE_UPLOAD_PATH + FileConstant.MATERIAL_UPLOAD + File.separator + dateStr;
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
        Material material = new Material();
        material.setTitle(title);
        material.setFile(fileDirPath + File.separator + newFileName);
        material.setUploadUser(userID);
        material.setSectionID(sectionID);
        boolean flag =  this.save(material);
        if (flag){
            log.info("已上传文件: " + newFileName);
            return material.getId();
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");
    }

    @Override
    public Boolean deleteMaterial(Long id) {
        Material material = this.getById(id);
        try {
            FileSystemUtils.deleteRecursively(new File(material.getFile()));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除文件失败");
        }
        this.removeById(material.getId());
        return true;
    }
}




