package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.enums.ResourceEnum;
import cn.edu.ujs.service.CommonService;
import cn.edu.ujs.service.ResourceService;
import cn.edu.ujs.util.ResultVOUtil;
import com.github.pagehelper.PageHelper;
import com.sun.xml.internal.ws.api.config.management.Reconfigurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/3.
 */
@RestController
@RequestMapping(value = "/resources")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    // TODO: 2018/3/19 增加用户日志，记录都有哪些操作 
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CommonService commonService;

    //获取所有资源
    @GetMapping(value = "/")
    public ResultVO getAllResources(@RequestParam(value = "parentCategoryId", required = false) Integer parentCategoryId,
                                    @RequestParam(value = "childCategoryId", required = false) Integer childCategoryId,
                                    @RequestParam(value = "resourceTypeId", required = false) Integer resourceTypeId,
                                    @RequestParam(value = "checkStatus", required = false) Integer checkStatus,
                                    @RequestParam(value = "sortType", required = false) Integer sortType,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        logger.info("parentCategoryId:{},childCategoryId:{},resourceTypeId:{},checkStatus:{},sortType:{},keyword:{}",parentCategoryId,childCategoryId,resourceTypeId,checkStatus,sortType,keyword);
        List<ResourceVO> resourceVOList;
        if (pageNum != null && pageSize != null) {
            //logger.info("pageNum:{},pageSize:{}",pageNum,pageSize);
            resourceVOList = resourceService.findByPage(parentCategoryId,
                    childCategoryId, resourceTypeId, checkStatus, sortType, keyword, pageNum, pageSize);

        } else {
            //logger.info("pageNum&pageSize:null");
            resourceVOList = resourceService.findAll(parentCategoryId,
                    childCategoryId, resourceTypeId, checkStatus,sortType,keyword);
        }
        ResultVO resultVO = ResultVOUtil.success(resourceVOList);
        return resultVO;
    }

    @GetMapping(value = "/2")
    public ResultVO getAllResources2(@RequestParam(value = "parentCategoryId", required = false) Integer parentCategoryId,
                                    @RequestParam(value = "childCategoryId", required = false) Integer childCategoryId,
                                    @RequestParam(value = "resourceTypeId", required = false) Integer resourceTypeId,
                                    @RequestParam(value = "checkStatus", required = false) Integer checkStatus,
                                    @RequestParam(value = "sortType", required = false) Integer sortType,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        logger.info("parentCategoryId:{},childCategoryId:{},resourceTypeId:{},checkStatus:{},sortType:{},keyword:{}",parentCategoryId,childCategoryId,resourceTypeId,checkStatus,sortType,keyword);
        Map<String,Object> map = new HashMap<>();
        map = resourceService.findByPage2(parentCategoryId,
                childCategoryId, resourceTypeId, checkStatus, sortType, keyword, pageNum, pageSize);
        ResultVO resultVO = ResultVOUtil.success(map);
        logger.info("result:{}",map);
        return resultVO;
    }


    //前台获取资源列表（审核通过的）
    @GetMapping(value = "/check")
    public ResultVO getResourcesByCheckStatus(@RequestParam(value = "checkStatus") Integer checkStatus) {

        List<ResourceVO> resourceVOList = resourceService.findAll(checkStatus);
        ResultVO resultVO = ResultVOUtil.success(resourceVOList);
        return resultVO;

    }


    //获取某个资源
    @GetMapping(value = "/{resourceId}")
    public ResultVO getResourceById(@PathVariable(value = "resourceId") Integer resourceId) {

        Resource resource = resourceService.findByResourceId(resourceId);
        ResultVO resultVO = ResultVOUtil.success(resource);
        return resultVO;
    }

    //获取某个资源的详细信息
    @GetMapping(value = "/detail/{resourceId}")
    public ResultVO getResourceDetail(@PathVariable(value = "resourceId") Integer resourceId) {

        Map<String,Object> map = commonService.getOneResource(resourceId);
        ResultVO resourceVO = ResultVOUtil.success(map);
        return resourceVO;
    }

    //上传资源
    @PostMapping(value = "/upload")
    public ResultVO uploadResource(@RequestParam("userId") Integer userId,
                                   @RequestParam("typeId") Integer typeId,
                                   @RequestParam("categoryId") Integer categoryId,
                                   @RequestParam("tagList") List<String> tagList,
                                   @RequestParam("points") Integer points,
                                   @RequestParam("description") String description,
                                   @RequestParam("file") MultipartFile file) {

        // TODO: 2018/3/18 判断是否为管理员上传，如果是则不需要再审核
        Resource resource = new Resource();
        resource.setUserId(userId);
        resource.setTypeId(typeId);
        resource.setCategoryId(categoryId);
        resource.setTagList(tagList);
        resource.setPoints(points);
        resource.setDescription(description);
        // TODO: 2018/3/17 修改路径 
        String filePath = "E://test//";
        ResultVO resultVO = commonService.uploadResource(filePath,file,resource);
        //将上传记录写入上传表
        resourceService.uploadResource(resource);
        return resultVO;
    }

    //下载资源
    @RequestMapping(value = "/download/{resourceId}/users/{userId}")
    public ResultVO downloadResource(@PathVariable("resourceId") Integer resourceId,
                                     @PathVariable("userId") Integer userId,
                                     HttpServletResponse httpServletResponse) {

        ResultVO resultVO = commonService.downloadResource(userId,resourceId,httpServletResponse);
        return resultVO;
    }

    //删除资源
    @RequestMapping(value = "/remove/{resourceId}")
    public ResultVO removeResource(@PathVariable("resourceId") Integer resourceId) {

        ResultVO resultVO = null;
        Resource resource = resourceService.removeResource(resourceId);
        if (resource == null)
            resultVO = ResultVOUtil.error(ResourceEnum.REMOVE_ERROR.getCode(),
                    ResourceEnum.REMOVE_ERROR.getMessage());
        else
            resultVO = ResultVOUtil.success(resource,
                    ResourceEnum.REMOVE_SUCCESS.getCode(),
                    ResourceEnum.REMOVE_SUCCESS.getMessage());
        return resultVO;
    }

    //编辑资源
    @RequestMapping(value = "/update/{resourceId}")
    public ResultVO updateResource(@RequestParam(value = "typeId", required = false) Integer typeId,
                                   @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                   @RequestParam(value = "tagList", required = false) List<String> tagList,
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "checkStatus", required = false) Integer checkStatus,
                                   @PathVariable(value = "resourceId") Integer resourceId) {

        ResultVO resultVO = null;
        Resource resource = resourceService.findByResourceId(resourceId);
        resource.setTypeId(typeId);
        resource.setCategoryId(categoryId);
        resource.setTagList(tagList);
        resource.setDescription(description);
        resource.setCheckStatus(checkStatus);
        if (resourceService.updateResource(resource) != null)
            resultVO = ResultVOUtil.success(resource);
        else
            resultVO = ResultVOUtil.error(ResourceEnum.UPDATE_ERROR.getCode(),
                    ResourceEnum.UPDATE_ERROR.getMessage());
        return resultVO;
    }

    //获取某个用户的资源列表
    @GetMapping(value = "/userId/{userId}")
    public ResultVO getResourcesByUserId(@PathVariable(value = "userId") Integer userId,
                                         @RequestParam(value = "checkStatus", required = false) Integer checkStatus) {

        List<ResourceVO> resourceVOList = resourceService.findResourcesByUserId(userId,checkStatus);
        ResultVO resultVO = ResultVOUtil.success(resourceVOList);
        return resultVO;
    }

    //获取某个用户上传的资源数量（审核通过）
    @GetMapping(value = "/count/userId/{userId}")
    public ResultVO getUserResourceCount(@PathVariable(value = "userId") Integer userId) {

        Integer result = resourceService.getResourceCountByUserId(userId);
        ResultVO resultVO = ResultVOUtil.success(result);
        return resultVO;
    }

// TODO: 2018/3/19 应该将为未审核的放进视图里，方便前台一次性获取，而不用查询整个表 
}
