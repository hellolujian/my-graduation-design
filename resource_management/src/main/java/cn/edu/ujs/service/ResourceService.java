package cn.edu.ujs.service;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/1.
 */
public interface ResourceService {

    /**上传资源*/
    public Resource uploadResource(Resource resource);

    /**删除资源*/
    public Resource removeResource(Integer resourceId);

    /**修改资源*/
    public Resource updateResource(Resource resource);

    /**查找某个资源*/
    public Resource findByResourceId(Integer resourceId);

    /**根据id查找，返回resourceVOid*/
    public ResourceVO getOneResourceVO(Integer resourceId);

    /**返回某个资源的详细信息*/
    public ResourceVO getOneResourceDetail(Integer resourceId);

    /**资源列表*/
    public List<ResourceVO> findAll();

    /**分页查询*/
    public List<ResourceVO> findByPage(Integer parentCategoryId,
                                     Integer childCategoryId,
                                     Integer resourceTypeId,
                                     Integer checkStatus,
                                     Integer sortType,
                                     String keyword,
                                     Integer pageNum,
                                     Integer pageSize);

    /**分页查询*/
    public Map<String,Object> findByPage2(Integer parentCategoryId,
                           Integer childCategoryId,
                           Integer resourceTypeId,
                           Integer checkStatus,
                           Integer sortType,
                           String keyword,
                           Integer pageNum,
                           Integer pageSize);
    /**审核状态的资源列表*/
    public List<ResourceVO> findAll(Integer checkStatus);

    /**多条件查询的资源列表*/
    public List<ResourceVO> findAll(Integer parentCategoryId,
                                    Integer childCategoryId,
                                    Integer resourceTypeId,
                                    Integer checkStatus,
                                    Integer sortType,
                                    String keyword);

    /**查询某个用户上传资源列表*/
    public List<ResourceVO> findResourcesByUserId(Integer userId, Integer checkStatus);

    /**查询某个用户审核通过的资源数量*/
    public Integer getResourceCountByUserId(Integer userId);

    /**判断该资源是否为某用户上传*/
    public boolean isUserUpload(Integer userId, Integer resourceId);

    /**拼接资源名称与后缀名*/
    public String getResourceNameAndSuffixName(Integer resourceId);

    /**判断该资源是否已存在*/
    public boolean isResourceExist(String title, String suffixNmme);

}
