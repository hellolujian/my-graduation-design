package cn.edu.ujs.controller;

import cn.edu.ujs.VO.CategoryVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.ChildCategory;
import cn.edu.ujs.entity.ParentCategory;
import cn.edu.ujs.service.ChildCategoryService;
import cn.edu.ujs.service.ParentCategoryService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    // TODO: 2018/3/13 可以添加一个description字段
    @Autowired
    private ParentCategoryService parentCategoryService;

    @Autowired
    private ChildCategoryService childCategoryService;

    //返回分类视图对象
    @GetMapping(value = "/")
    public ResultVO getCategory() {

        //查询所有的父级分类
        List<ParentCategory> parentCategoryList = parentCategoryService.findAll();
        List<CategoryVO> categoryVOList = new ArrayList<>();
        //遍历父级分类
        for (ParentCategory parentCategory : parentCategoryList) {
            //查询该父级分类下的所有子级分类
            List<ChildCategory> childCategoryList = childCategoryService.findByParentCategoryId(parentCategory.getId());
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(parentCategory,categoryVO);
            categoryVO.setChildCategoryList(childCategoryList);
            categoryVOList.add(categoryVO);
        }
        ResultVO resultVO = ResultVOUtil.success(categoryVOList);
        return resultVO;
    }

    //父级分类列表
    @GetMapping(value = "/parent")
    public ResultVO getParentCategory() {

        List<ParentCategory> parentCategoryList = parentCategoryService.findAll();
        ResultVO resultVO = ResultVOUtil.success(parentCategoryList);
        return resultVO;
    }

    //查询父级分类下的所有子级分类
    @GetMapping(value = "/parent/{parentCategoryId}/childCategory")
    public ResultVO getChildCategory(@PathVariable(value = "parentCategoryId") Integer parentCategoryId) {
        List<ChildCategory> childCategoryList = childCategoryService.findByParentCategoryId(parentCategoryId);
        ResultVO resultVO = ResultVOUtil.success(childCategoryList);
        return resultVO;
    }

    //添加父级分类
    @PostMapping(value = "/addParentCategory")
    public ResultVO addParentCategory(@RequestParam(value = "parentCategoryName") String parentCategoryName) {

        ResultVO resultVO = null;
        ParentCategory parentCategory =new ParentCategory();
        parentCategory.setParentCategoryName(parentCategoryName);
        if (parentCategoryService.addParentCategory(parentCategory) != null)
            resultVO = ResultVOUtil.success(parentCategory);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //添加子级分类
    @PostMapping(value = "/addChildCategory")
    public ResultVO addChildCategory(@RequestParam(value = "parentCategoryId") Integer parentCategoryId,
                                     @RequestParam(value = "childCategoryName") String childCategoryName) {

        ResultVO resultVO = null;
        ChildCategory childCategory =new ChildCategory();
        childCategory.setParentCategoryId(parentCategoryId);
        childCategory.setChildCategoryName(childCategoryName);
        if (childCategoryService.addChildCategory(childCategory) != null)
            resultVO = ResultVOUtil.success(childCategory);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //删除父级分类
    @PostMapping(value = "/removeParentCategory")
    public ResultVO removeParentCategory(@RequestParam(value = "parentCategoryId") Integer parentCategoryId) {

        ResultVO resultVO = null;
        //先删除它的子级分类
        childCategoryService.removeChildCategoryByParentId(parentCategoryId);
        //删除父级分类
        ParentCategory parentCategory = parentCategoryService.removeParentCategory(parentCategoryId);
        if (parentCategory != null)
            resultVO = ResultVOUtil.success(parentCategory);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //删除子级分类
    @PostMapping(value = "/removeChildCategory")
    public ResultVO removeChildCategory(@RequestParam(value = "childCategoryId") Integer childCategoryId) {

        ResultVO resultVO = null;
        ChildCategory childCategory = childCategoryService.removeChildCategory(childCategoryId);
        if (childCategory != null)
            resultVO = ResultVOUtil.success(childCategory);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //修改父级分类
    @PostMapping(value = "/updateParentCategory")
    public ResultVO updateParentCategory(@RequestParam(value = "parentCategoryId") Integer parentCategoryId,
                                         @RequestParam(value = "parentCategoryName") String parentCategoryName) {

        ResultVO resultVO = null;
        ParentCategory parentCategory = parentCategoryService.updateParentCategory(parentCategoryId, parentCategoryName);
        if (parentCategory != null)
            resultVO = ResultVOUtil.success(parentCategory);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }

    //修改子级分类
    @PostMapping(value = "/updateChildCategory")
    public ResultVO updateChildCategory(@RequestParam(value = "childCategoryId") Integer childCategoryId,
                                        @RequestParam(value = "childCategoryName") String childCategoryName) {

        ResultVO resultVO = null;
        ChildCategory childCategory = childCategoryService.updateChildCategory(childCategoryId, childCategoryName);
        if (childCategory != null)
            resultVO = ResultVOUtil.success(childCategory);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }
}
