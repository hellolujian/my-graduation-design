package cn.edu.ujs.service;

import cn.edu.ujs.entity.ChildCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
public interface ChildCategoryService {

    /**添加子级分类*/
    ChildCategory addChildCategory(ChildCategory childCategory);

    /**删除子级分类*/
    ChildCategory removeChildCategory(Integer childCategoryId);

    /**根据父级分类id删除子级分类*/
    boolean removeChildCategoryByParentId(Integer parentCategoryId);

     /**修改子级分类*/
    ChildCategory updateChildCategory(Integer childCategoryId, String childCategoryName);

    /**根据父级分类id查询*/
    List<ChildCategory> findByParentCategoryId(Integer parentCategoryId);
}
