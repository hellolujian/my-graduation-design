package cn.edu.ujs.service;

import cn.edu.ujs.entity.ParentCategory;
import org.aspectj.weaver.IClassWeaver;
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
public class ParentCategoryServiceTest {

    @Autowired
    private ParentCategoryService parentCategoryService;

    @Test
    public void testAddParentCategory() throws Exception {

        ParentCategory parentCategory = new ParentCategory();
        parentCategory.setParentCategoryName("new P");
        ParentCategory newP = parentCategoryService.addParentCategory(parentCategory);
        Assert.assertNotNull(newP);
    }

    @Test
    public void testRemoveParentCategory() throws Exception {

        ParentCategory parentCategory = parentCategoryService.removeParentCategory(18);
        Assert.assertNotNull(parentCategory);
    }

    @Test
    public void testUpdateParentCategory() throws Exception {

        ParentCategory parentCategory = parentCategoryService.updateParentCategory(18,"sdfdsf");
        Assert.assertNotNull(parentCategory);
    }

    @Test
    public void testFindAll() throws Exception {

        List<ParentCategory> parentCategoryList = parentCategoryService.findAll();
        Assert.assertNotEquals(0,parentCategoryList.size());
    }
}