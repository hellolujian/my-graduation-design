package cn.edu.ujs.service;

import cn.edu.ujs.entity.ResourceType;
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
public class ResourceTypeServiceTest {

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Test
    public void testAddResourceType() throws Exception {

        ResourceType resourceType = new ResourceType();
        resourceType.setTypeName("新增");
        ResourceType newType = resourceTypeService.addResourceType(resourceType);
        Assert.assertNotNull(newType);
    }

    @Test
    public void testRemoveResourceType() throws Exception {

        ResourceType resourceType = resourceTypeService.removeResourceType(5);
        Assert.assertNotNull(resourceType);
    }

    @Test
    public void testUpdateResourceType() throws Exception {

        ResourceType resourceType = resourceTypeService.updateResourceType(5,"新增的");
        Assert.assertNotNull(resourceType);
    }

    @Test
    public void testFindAll() throws Exception {

        List<ResourceType> resourceTypeList = resourceTypeService.findAll();
        Assert.assertNotNull(resourceTypeList);
    }
}