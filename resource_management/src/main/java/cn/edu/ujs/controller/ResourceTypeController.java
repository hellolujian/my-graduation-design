package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.ResourceType;
import cn.edu.ujs.service.ResourceTypeService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/resourceTypes")
public class ResourceTypeController {

    @Autowired
    private ResourceTypeService resourceTypeService;

    //获取所有类型
    @GetMapping(value = "/")
    public ResultVO getAllTypes() {

        List<ResourceType> resourceTypeList = resourceTypeService.findAll();
        ResultVO resultVO = ResultVOUtil.success(resourceTypeList);
        return resultVO;
    }

    //新增类型
    @PostMapping(value = "/addType")
    public ResultVO addType(@RequestParam(value = "typeName") String typeName) {

        ResourceType resourceType = new ResourceType();
        resourceType.setTypeName(typeName);
        ResultVO resultVO = null;
        if (resourceTypeService.addResourceType(resourceType) != null)
            resultVO = ResultVOUtil.success(resourceType);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //删除类型
    @PostMapping(value = "/removeType")
    public ResultVO removeType(@RequestParam(value = "typeId") Integer typeId) {

        ResourceType resourceType = resourceTypeService.removeResourceType(typeId);
        ResultVO resultVO = null;
        if (resourceType != null)
            resultVO = ResultVOUtil.success(resourceType);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //修改类型
    @PostMapping(value = "/updateType")
    public ResultVO updateType(@RequestParam(value = "typeId") Integer typeId,
                               @RequestParam(value = "typeName") String typeName) {

        ResourceType resourceType = resourceTypeService.updateResourceType(typeId,typeName);
        ResultVO resultVO = null;
        if (resourceType != null)
            resultVO = ResultVOUtil.success(resourceType);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }
}
