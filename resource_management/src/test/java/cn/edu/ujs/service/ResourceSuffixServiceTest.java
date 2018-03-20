package cn.edu.ujs.service;

import cn.edu.ujs.entity.ResourceSuffix;
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
public class ResourceSuffixServiceTest {

    @Autowired
    private ResourceSuffixService resourceSuffixService;

    @Test
    public void testAddResourceSuffix() throws Exception {

        ResourceSuffix resourceSuffix = new ResourceSuffix();
        resourceSuffix.setSuffixName("new suffix");
        ResourceSuffix newSuffix = resourceSuffixService.addResourceSuffix(resourceSuffix);
        Assert.assertNotNull(newSuffix);
    }

    @Test
    public void testRemoveResourceSuffix() throws Exception {

        ResourceSuffix resourceSuffix = resourceSuffixService.removeResourceSuffix(9);
        Assert.assertNotNull(resourceSuffix);
    }

    @Test
    public void testUpdateResourceSuffix() throws Exception {

        ResourceSuffix resourceSuffix = resourceSuffixService.updateResourceSuffix(9,"update suffix","image");
        Assert.assertNotNull(resourceSuffix);
    }

    @Test
    public void testFindAll() throws Exception {

        List<ResourceSuffix> resourceSuffixList = resourceSuffixService.findAll();
        Assert.assertNotNull(resourceSuffixList);
    }
}