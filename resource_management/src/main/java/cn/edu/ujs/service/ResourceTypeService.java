package cn.edu.ujs.service;

import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.entity.ResourceType;

import java.util.Iterator;
import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
public interface ResourceTypeService {

    /**添加资源类型*/
    public ResourceType addResourceType(ResourceType resourceType);

    /**删除资源类型*/
    public ResourceType removeResourceType(Integer resourceTypeId);

    /**修改资源类型名称*/
    public ResourceType updateResourceType(Integer resourceTypeId, String resourceTypeName);

    /**资源类型列表*/
    public List<ResourceType> findAll();

}
