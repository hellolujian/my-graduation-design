package cn.edu.ujs.service;

import cn.edu.ujs.entity.ChildCategory;
import cn.edu.ujs.mapper.ChildCategoryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/3/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChildCategoryServiceTest {

    @Autowired
    private ChildCategoryService childCategoryService;

    @Test
    public void testAddChildCategory() throws Exception {

        ChildCategory childCategory = new ChildCategory();
        childCategory.setChildCategoryName("new child");
        childCategory.setParentCategoryId(10);
        ChildCategory newChild = childCategoryService.addChildCategory(childCategory);
        Assert.assertNotNull(newChild);
    }

    @Test
    public void testRemoveChildCategory() throws Exception {

        ChildCategory childCategory = childCategoryService.removeChildCategory(162);
        Assert.assertNotNull(childCategory);
    }

    @Test
    public void testUpdateChildCategory() throws Exception {

        ChildCategory childCategory = childCategoryService.updateChildCategory(162,"sdfsdf");
        Assert.assertNotNull(childCategory);
    }

    @Test
    public void testFindByParentCategoryId() throws Exception {

        List<ChildCategory> childCategoryList = childCategoryService.findByParentCategoryId(10);
        Assert.assertNotEquals(0,childCategoryList.size());
    }
}