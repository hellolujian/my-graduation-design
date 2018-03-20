package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Tag;
import cn.edu.ujs.service.TagService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    //获取所有热门标签
    @GetMapping(value = "/")
    public ResultVO getAllTags() {

        List<Tag> tagList = tagService.findAll();
        ResultVO resultVO = ResultVOUtil.success(tagList);
        return resultVO;
    }

    //新增标签
    @PostMapping(value = "/addTag")
    public ResultVO addTag(@RequestParam(value = "tagName") String tagName) {

        Tag tag = new Tag();
        tag.setTagName(tagName);
        ResultVO resultVO = null;
        if (tagService.addTag(tag) != null)
            resultVO = ResultVOUtil.success(tag);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //删除标签
    // TODO: 2018/3/13 前台传递id数组，批量删除 
    @PostMapping(value = "/removeTag")
    public ResultVO removeTag(@RequestParam(value = "tagId") Integer tagId) {

        Tag tag = tagService.removeTag(tagId);
        ResultVO resultVO = null;
        if (tag != null)
            resultVO = ResultVOUtil.success(tag);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //修改标签
    @PostMapping(value = "/updateTag")
    public ResultVO updateTag(@RequestParam(value = "tagId") Integer tagId,
                              @RequestParam(value = "tagName") String tagName) {

        Tag tag = tagService.updateTag(tagId, tagName);
        ResultVO resultVO = null;
        if (tag != null)
            resultVO = ResultVOUtil.success(tag);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }
}
