package cn.edu.ujs.service.impl;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.mapper.ResourceMapper;
import cn.edu.ujs.service.ResourceService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/3.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Resource uploadResource(Resource resource) {

        Integer result = resourceMapper.addResource(resource);
        if (result == 1) {

            // TODO: 2018/3/3 利用websocket发送消息，提示后台管理审核，审核通过后增加相应积分
            return resource;
        }
        return null;
    }

    @Override
    public Resource removeResource(Integer resourceId) {

        Resource resource = resourceMapper.findByResourceId(resourceId);
        // TODO: 2018/3/18 还要删除资源 
        Integer result = resourceMapper.removeResource(resourceId);
        if (result == 1)
            return resource;
        return null;
    }

    @Override
    public Resource updateResource(Resource resource) {

        BeanMap beanMap = BeanMap.create(resource);
        Map map = new HashMap<>();
        for (Object key : beanMap.keySet()) {
            map.put(key,beanMap.get(key));
        }
        Integer result = resourceMapper.updateResourceMultiField(resource);
        if (result == 1)
            return resource;
        return null;
    }

    @Override
    public Resource findByResourceId(Integer resourceId) {

        Resource resource = resourceMapper.findByResourceId(resourceId);
        return resource;
    }

    @Override
    public ResourceVO getOneResourceVO(Integer resourceId) {

        ResourceVO resourceVO = resourceMapper.getOneResourceVO(resourceId);
        return resourceVO;
    }

    @Override
    public List<ResourceVO> findAll() {

        List<ResourceVO> resourceVOList = resourceMapper.findAllOrderByUpdateTime();
        return resourceVOList;
    }

    @Override
    public List<ResourceVO> findByPage(Integer parentCategoryId,
                                     Integer childCategoryId,
                                     Integer resourceTypeId,
                                     Integer checkStatus,
                                     Integer sortType,
                                     String keyword,
                                     Integer pageNum,
                                     Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ResourceVO> resourceVOList = resourceMapper.findAllByMultiCondition(parentCategoryId,
                childCategoryId, resourceTypeId, checkStatus, sortType, keyword);
        return resourceVOList;
    }

    @Override
    public List<ResourceVO> findAll(Integer checkStatus) {

        List<ResourceVO> resourceVOList = resourceMapper.findAllOrderByCheckStatus(checkStatus);
        return resourceVOList;
    }

    @Override
    public List<ResourceVO> findAll(Integer parentCategoryId,
                                    Integer childCategoryId,
                                    Integer resourceTypeId,
                                    Integer checkStatus,
                                    Integer sortType,
                                    String keyword) {

        List<ResourceVO> resourceVOList = resourceMapper.findAllByMultiCondition(parentCategoryId,
                childCategoryId, resourceTypeId, checkStatus, sortType, keyword);
        return resourceVOList;
    }

    @Override
    public List<ResourceVO> findResourcesByUserId(Integer userId) {

        List<ResourceVO> resourceVOList = resourceMapper.findResourcesByUserId(userId);
        return resourceVOList;
    }

    @Override
    public boolean isUserUpload(Integer userId, Integer resourceId) {

        Integer result = resourceMapper.findByUserIdAndResourceId(userId, resourceId);
        if (result == 1)
            return true;
        return false;
    }

    @Override
    public String getResourceNameAndSuffixName(Integer resourceId) {

        Resource resource = resourceMapper.findByResourceId(resourceId);
        String fileName = resource.getTitle()+resource.getSuffixName();
        return fileName;
    }

    @Override
    public boolean isResourceExist(String title, String suffixNmme) {

        // TODO: 2018/3/5 判断文件重复不能简单的通过文件名，赢根据file的内置函数， 
        boolean isResourceExist = false;
        Integer result = resourceMapper.findByTitleAndSuffixName(title,suffixNmme);
        if (result == 1)
            isResourceExist = true;
        return isResourceExist;
    }
}
