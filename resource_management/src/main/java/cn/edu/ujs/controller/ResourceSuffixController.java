package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.ResourceSuffix;
import cn.edu.ujs.service.ResourceSuffixService;
import cn.edu.ujs.util.FileUtil;
import cn.edu.ujs.util.ResultVOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/suffixs")
public class ResourceSuffixController {

    private static  final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    // TODO: 2018/3/13 后缀图标应该存相对地址 
    @Autowired
    private ResourceSuffixService resourceSuffixService;

    //获取后缀名列表
    @GetMapping(value = "/")
    public ResultVO getAllSuffixs() {

        List<ResourceSuffix> resourceSuffixList = resourceSuffixService.findAll();
        ResultVO resultVO = ResultVOUtil.success(resourceSuffixList);
        return resultVO;
    }

    //新增后缀
    @PostMapping(value = "/addSuffix")
    public ResultVO addSuffix(@RequestParam(value = "suffixName") String suffixName,
                              @RequestParam(value = "file", required = false) MultipartFile file) {

        ResultVO resultVO = null;
        ResourceSuffix resourceSuffix = new ResourceSuffix();
        resourceSuffix.setSuffixName(suffixName);
        // TODO: 2018/3/16 路径配置
        String suffixImage = "http://127.0.0.1:8082/image/suffix_icon/";
        if (file == null) {
            suffixImage += "unknow.svg";
        }
        else {
            suffixImage += file.getOriginalFilename();
            FileUtil.uploadResource(file);
        }
        resourceSuffix.setSuffixImage(suffixImage);
        // TODO: 2018/3/16 需要捕获数据库异常，不能重名
        if (resourceSuffixService.addResourceSuffix(resourceSuffix) != null)
            resultVO = ResultVOUtil.success(resourceSuffix);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //删除后缀
    @PostMapping(value = "/removeSuffix")
    public ResultVO removeSuffix(@RequestParam(value = "suffixId") Integer suffixId) {

        ResultVO resultVO = null;
        ResourceSuffix resourceSuffix = resourceSuffixService.removeResourceSuffix(suffixId);
        if (resourceSuffix != null)
            resultVO = ResultVOUtil.success(resourceSuffix);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //修改后缀
    @PostMapping(value = "/updateSuffix")
    public ResultVO updateSuffix(@RequestParam(value = "suffixId") Integer suffixId,
                                 @RequestParam(value = "suffixName") String suffixName,
                                 @RequestParam(value = "file") MultipartFile file) {

        // TODO: 2018/3/16 还要删除原图标
        ResultVO resultVO = null;
        logger.info(suffixName + suffixId);
        String suffixImage = "http://127.0.0.1:8082/image/suffix_icon/";
        if (FileUtil.uploadResource(file)) {
            suffixImage += file.getOriginalFilename();
            ResourceSuffix resourceSuffix = resourceSuffixService.updateResourceSuffix(suffixId,suffixName,suffixImage);
            if (resourceSuffix != null)
                resultVO = ResultVOUtil.success(resourceSuffix);
            else
                resultVO = ResultVOUtil.error();
        }
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    @RequestMapping(value = "/icon")
    public String icon(@RequestParam("file") MultipartFile file,
                       HttpServletRequest httpServletRequest) {

        // TODO: 2018/3/15 研究一下base64 
        FileUtil.uploadResource(file);
        return "icon";
    }
}
