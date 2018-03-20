package cn.edu.ujs.service;

import cn.edu.ujs.entity.ParentCategory;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
public interface ParentCategoryService {

    /**添加父级分类*/
    ParentCategory addParentCategory(ParentCategory parentCategory);

    /**删除父级分类*/
    ParentCategory removeParentCategory(Integer parentCategoryId);

    /**修改父级分类*/
    ParentCategory updateParentCategory(Integer parentCategoryId, String parentCategoryName);

    /**父级分类列表*/
    List<ParentCategory> findAll();


}
