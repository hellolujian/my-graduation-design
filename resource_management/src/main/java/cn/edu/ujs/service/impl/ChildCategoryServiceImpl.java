package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.ChildCategory;
import cn.edu.ujs.mapper.ChildCategoryMapper;
import cn.edu.ujs.service.ChildCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Service
public class ChildCategoryServiceImpl implements ChildCategoryService {

    @Autowired
    private ChildCategoryMapper childCategoryMapper;

    @Override
    public ChildCategory addChildCategory(ChildCategory childCategory) {

        Integer result = childCategoryMapper.addChildCategory(childCategory);
        if (result == 1)
            return childCategory;
        return null;
    }

    @Override
    public ChildCategory removeChildCategory(Integer childCategoryId) {

        ChildCategory childCategory = childCategoryMapper.findByChildCategoryId(childCategoryId);
        Integer result = childCategoryMapper.removeChildCategory(childCategoryId);
        if (result == 1)
            return childCategory;
        return null;
    }

    @Override
    public boolean removeChildCategoryByParentId(Integer parentCategoryId) {

        Integer result = childCategoryMapper.removeChildCategoryByParentId(parentCategoryId);
        if (result >= 0)
            return true;
        return false;
    }

    @Override
    public ChildCategory updateChildCategory(Integer childCategoryId, String childCategoryName) {

        Integer result = childCategoryMapper.updateChildCategory(childCategoryId,childCategoryName);
        ChildCategory childCategory = childCategoryMapper.findByChildCategoryId(childCategoryId);
        if (result == 1)
            return childCategory;
        return null;
    }

    @Override
    public List<ChildCategory> findByParentCategoryId(Integer parentCategoryId) {

        List<ChildCategory> childCategoryList = childCategoryMapper.findByParentCategoryId(parentCategoryId);
        return childCategoryList;
    }
}
