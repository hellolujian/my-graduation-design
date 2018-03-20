package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.ResourceType;
import cn.edu.ujs.mapper.ResourceTypeMapper;
import cn.edu.ujs.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {

    @Autowired
    private ResourceTypeMapper resourceTypeMapper;

    @Override
    public ResourceType addResourceType(ResourceType resourceType) {

        Integer result = resourceTypeMapper.addResourceType(resourceType);
        if (result == 1)
            return resourceType;
        return null;
    }

    @Override
    public ResourceType removeResourceType(Integer resourceTypeId) {

        ResourceType resourceType = resourceTypeMapper.findByResourceTypeId(resourceTypeId);
        Integer result = resourceTypeMapper.removeResourceType(resourceTypeId);
        if (result == 1)
            return resourceType;
        return null;
    }

    @Override
    public ResourceType updateResourceType(Integer resourceTypeId, String resourceTypeName) {

        Integer result = resourceTypeMapper.updateResourceType(resourceTypeId, resourceTypeName);
        ResourceType resourceType = resourceTypeMapper.findByResourceTypeId(resourceTypeId);
        if (result == 1)
            return resourceType;
        return null;
    }

    @Override
    public List<ResourceType> findAll() {

        List<ResourceType> resourceTypeList = resourceTypeMapper.findAll();
        return resourceTypeList;
    }
}
