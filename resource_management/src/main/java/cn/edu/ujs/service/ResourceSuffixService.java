package cn.edu.ujs.service;

import cn.edu.ujs.entity.ResourceSuffix;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
public interface ResourceSuffixService {

    /**添加资源后缀*/
    public ResourceSuffix addResourceSuffix(ResourceSuffix resourceSuffix);

    /**删除资源后缀*/
    public ResourceSuffix removeResourceSuffix(Integer resourceSuffixId);

    /**修改资源后缀名称与图片*/
    public ResourceSuffix updateResourceSuffix(Integer resourceSuffixId, String resourceSuffixName, String resourceSuffixImage);

    /**修改后缀图标*/
    public ResourceSuffix updateSuffixIcon(Integer resourceSuffixId, MultipartFile multipartFile);

    /**资源后缀列表*/
    public List<ResourceSuffix> findAll();
}
