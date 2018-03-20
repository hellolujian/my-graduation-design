package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.entity.Resource;
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
public class ResourceMapperTest {

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    public void testAddResource() throws Exception {

        Resource resource = new Resource();
        resource.setTitle("a");
        resource.setTypeId(1);
        resource.setCategoryId(1);
        resource.setUserId(1);
     //   resource.setTagList("a");
        resource.setCheckStatus(0);
        resource.setPosition("a");
        resource.setSize("0KB");
        resource.setPoints(0);
        resource.setDescription("a");
        Integer result = resourceMapper.addResource(resource);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testRemoveResource() throws Exception {

        Integer result = resourceMapper.removeResource(1);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testUpdateResource() throws Exception {

        Resource resource = resourceMapper.findByResourceId(2);
        resource.setTitle("sdfdsfsd");
        Integer result = resourceMapper.updateResource(resource);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testFindByResourceId() throws Exception {

        Resource resource = resourceMapper.findByResourceId(2);
        Assert.assertNotNull(resource);
    }

    @Test
    public void testFindAllOrderByUpdateTime() throws Exception {

        List<ResourceVO> resourceList = resourceMapper.findAllOrderByUpdateTime();
        Assert.assertNotEquals(0,resourceList.size());
    }

    @Test
    public void testindAllByMultiCondition() throws Exception {

        List<ResourceVO> resourceVOList = resourceMapper.findAllByMultiCondition(null,null,null,null,null,null);
        Assert.assertNotEquals(0,resourceVOList.size());
    }
}