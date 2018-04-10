package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Collection;
import cn.edu.ujs.service.CollectionService;
import cn.edu.ujs.service.CommonService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CommonService commonService;

    //获取某个用户的收藏列表
    @GetMapping(value = "/users/{userId}")
    public ResultVO getCollectionsByUserId(@PathVariable(value = "userId") Integer userId) {

        ResultVO resultVO = commonService.userCollection(userId);
        return resultVO;
    }

    //增加收藏记录
    @PostMapping(value = "/addCollection")
    public ResultVO addCollection(@RequestParam(value = "userId") Integer userId,
                                  @RequestParam(value = "resourceId") Integer resourceId) {

        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setResourceId(resourceId);
        ResultVO resultVO = null;
        if (collectionService.addCollection(collection) != null)
            resultVO = ResultVOUtil.success(collection);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //移除收藏记录
    @PostMapping(value = "/removeCollection")
    public ResultVO removeCollection(@RequestParam(value = "collectionId") Integer collectionId) {

        Collection collection = collectionService.removeCollection(collectionId);
        ResultVO resultVO = null;
        if (collection != null)
            resultVO = ResultVOUtil.success(collection);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //用户取消收藏
    @PostMapping(value = "/cancelCollection")
    public ResultVO cancelCollection(@RequestParam(value = "userId") Integer userId,
                                     @RequestParam(value = "resourceId") Integer resourceId) {

        Integer result = collectionService.removeCollectionByUserIdAndResourceId(userId, resourceId);
        ResultVO resultVO = null;
        if (result != null)
            resultVO = ResultVOUtil.success(result);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //判断某个用户是否收藏了某个资源
    @GetMapping(value = "user/{userId}/resource/{resourceId}")
    public ResultVO isUserCollect(@PathVariable("resourceId") Integer resourceId,
                                  @PathVariable("userId") Integer userId) {

        boolean result = collectionService.isUserCollect(userId,resourceId);
        ResultVO resultVO = ResultVOUtil.success(result);
        return resultVO;
    }
}
