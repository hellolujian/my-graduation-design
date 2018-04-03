package cn.edu.ujs.controller;

import cn.edu.ujs.VO.RemarkVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Remark;
import cn.edu.ujs.service.RemarkService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/remarks")
public class RemarkController {

    @Autowired
    private RemarkService remarkService;

    //获取某个资源的评论列表
    @GetMapping(value = "/resourceId/{resourceId}")
    public ResultVO getRemarksByResourceId(@PathVariable(value = "resourceId") Integer resourceId) {

        List<RemarkVO> remarkVOList = remarkService.findRemarkByResourceId(resourceId);
        ResultVO resultVO = ResultVOUtil.success(remarkVOList);
        return resultVO;
    }

    //分页获取评价列表
    @GetMapping(value = "/page/resourceId/{resourceId}")
    public ResultVO getRemarksByPage(@PathVariable("resourceId") Integer resourceId,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam("pageNum") Integer pageNum) {
        List<RemarkVO> remarkVOList = remarkService.findRemarkByPage(resourceId, pageNum, pageSize);
        ResultVO resultVO = ResultVOUtil.success(remarkVOList);
        return resultVO;
    }

    //获取某个资源的评分个数
    @GetMapping(value = "/scoreAmount/resourceId/{resourceId}")
    public ResultVO getScoreAmount(@PathVariable(value = "resourceId") Integer resourceId) {

        Integer scoreAmount = remarkService.getScoreAmount(resourceId);
        ResultVO resultVO = ResultVOUtil.success(scoreAmount);
        return resultVO;
    }

    //获取某个资源的评论个数
    @GetMapping(value = "/contentAmount/resourceId/{resourceId}")
    public ResultVO getContentAmount(@PathVariable(value = "resourceId") Integer resourceId) {

        Integer contentAmount = remarkService.getContentAmount(resourceId);
        ResultVO resultVO = ResultVOUtil.success(contentAmount);
        return resultVO;
    }

    //获取某个资源的综合评分
    @GetMapping(value = "/averageScore/resourceId/{resourceId}")
    public ResultVO getAverageScore(@PathVariable(value = "resourceId") Integer resourceId) {

        Integer averageScore = remarkService.getContentAmount(resourceId);
        ResultVO resultVO = ResultVOUtil.success(averageScore);
        return resultVO;
    }

    //判断某个用户是否评价某资源
    @GetMapping(value = "/user/{userId}/resource/{resourceId}")
    public ResultVO isUserRemark(@PathVariable(value = "userId") Integer userId,
                                 @PathVariable(value = "resourceId") Integer resourceId) {

        boolean flag = remarkService.isUserRemark(userId, resourceId);
        ResultVO resultVO = ResultVOUtil.success(flag);
        return resultVO;
    }

    //增加评论
    @PostMapping(value = "/addRemark")
    public ResultVO addRemark(@RequestParam(value = "userId") Integer userId,
                              @RequestParam(value = "resourceId") Integer resourceId,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "score") Integer score) {

        Remark remark = new Remark();
        remark.setUserId(userId);
        remark.setResourceId(resourceId);
        remark.setContent(content);
        remark.setScore(score);
        ResultVO resultVO = null;
        if (remarkService.addRemark(remark) != null)
            resultVO = ResultVOUtil.success(remark);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }
}
