package cn.edu.ujs.service.impl;

import cn.edu.ujs.ResourceManagementApplication;
import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.entity.ResourceSuffix;
import cn.edu.ujs.mapper.ResourceSuffixMapper;
import cn.edu.ujs.service.ResourceSuffixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Service
public class ResourceSuffixServiceImpl implements ResourceSuffixService {

    @Autowired
    private ResourceSuffixMapper resourceSuffixMapper;

    @Override
    public ResourceSuffix addResourceSuffix(ResourceSuffix resourceSuffix) {

        Integer result = resourceSuffixMapper.addResourceSuffix(resourceSuffix);
        if (result == 1)
            return resourceSuffix;
        return null;
    }

    @Override
    public ResourceSuffix removeResourceSuffix(Integer resourceSuffixId) {

        ResourceSuffix resourceSuffix = resourceSuffixMapper.findByResourceSuffixId(resourceSuffixId);
        Integer result = resourceSuffixMapper.removeResourceSuffix(resourceSuffixId);
        if (result == 1)
            return resourceSuffix;
        return null;
    }

    @Override
    public ResourceSuffix updateResourceSuffix(Integer resourceSuffixId, String resourceSuffixName, String resourceSuffixImage) {

        Integer result = resourceSuffixMapper.updateResourceSuffix(resourceSuffixId,resourceSuffixName,resourceSuffixImage);
        ResourceSuffix resourceSuffix = resourceSuffixMapper.findByResourceSuffixId(resourceSuffixId);
        if (result == 1)
            return resourceSuffix;
        return null;
    }

    @Override
    public ResourceSuffix updateSuffixIcon(Integer resourceSuffixId, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public List<ResourceSuffix> findAll() {

        List<ResourceSuffix> resourceSuffixList = resourceSuffixMapper.findAll();
        return resourceSuffixList;
    }
}
