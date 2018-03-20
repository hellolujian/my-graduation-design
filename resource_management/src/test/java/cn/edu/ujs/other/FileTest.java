package cn.edu.ujs.other;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.entity.One;
import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.mapper.OneMapper;
import cn.edu.ujs.mapper.ResourceMapper;
import cn.edu.ujs.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileTest {

    @Autowired
    private OneMapper oneMapper;

    public Integer a(Integer integer) {
        integer = integer + 1;
        return integer;
    }

    public Resource hh(Resource resource) {
        resource.setTitle("dsfds");
        return resource;
    }

    @Test
    public void testFile() throws Exception {

        Integer integerA = 1;
        Integer integerB = a(integerA);
        System.out.println("integerA:"+integerA+",integerB:"+integerB);
    }

    @Test
    public void testResource() throws Exception {
        Resource resource = new Resource();
        resource.setSize("0kb");
        resource.setPoints(10);
        hh(resource);
    }

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    public void testGetResourceVO() throws Exception {
        List<ResourceVO> resourceVOList = resourceMapper.findAllByMultiCondition(null,null,null,null,null,"a");
        Assert.assertNotNull(resourceVOList);
    }

    @Test
    public void testSendMessage() throws Exception {

        Map<String,Integer> map = new HashMap<>();
        map = HttpClientUtil.sendMessage("18852852189");
        Assert.assertNotNull(map);
    }

    @Test
    public void testJsonToList() throws Exception {

        String jsonStr = "['mysql','sdfd']";
        List<Object> objects = JSONArray.parseArray(jsonStr,Object.class);
        String str = JSON.toJSONString(objects);
        Assert.assertEquals(2,objects.size());
    }

    @Test
    public void testTypeHandler() throws Exception {

        List<One> oneList = oneMapper.findAll();
        Assert.assertNotNull(oneList);
    }

    @Test
    public void testInsert() throws Exception {

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        One one = new One();
        one.setList(list);
        Integer result = oneMapper.addOne(one);
        Assert.assertEquals(new Integer(1),result);
    }
}
