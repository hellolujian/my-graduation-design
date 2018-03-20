package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.ParentCategory;
import cn.edu.ujs.mapper.ParentCategoryMapper;
import cn.edu.ujs.service.ParentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/1.
 */
@Service
public class ParentCategoryServiceImpl implements ParentCategoryService {

    @Autowired
    private ParentCategoryMapper parentCategoryMapper;

    @Override
    public ParentCategory addParentCategory(ParentCategory parentCategory) {

        Integer result = parentCategoryMapper.addParentCategory(parentCategory);
        if (result == 1)
            return parentCategory;
        return null;
    }

    @Override
    public ParentCategory removeParentCategory(Integer parentCategoryId) {

        ParentCategory parentCategory = parentCategoryMapper.findByParentCategoryId(parentCategoryId);
        Integer result = parentCategoryMapper.removeParentCategory(parentCategoryId);
        if (result == 1)
            return parentCategory;
        return null;
    }

    @Override
    public ParentCategory updateParentCategory(Integer parentCategoryId, String parentCategoryName) {

        Integer result = parentCategoryMapper.updateParentCategory(parentCategoryId,parentCategoryName);
        ParentCategory parentCategory = parentCategoryMapper.findByParentCategoryId(parentCategoryId);
        if (result == 1)
            return parentCategory;
        return null;
    }

    @Override
    public List<ParentCategory> findAll() {

        List<ParentCategory> parentCategoryList = parentCategoryMapper.findAll();
        return parentCategoryList;
    }
}
