package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.RemarkVO;
import cn.edu.ujs.entity.Remark;
import org.hibernate.cfg.beanvalidation.IntegrationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/3/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemarkMapperTest {

    @Autowired
    private RemarkMapper remarkMapper;

    @Test
    public void testAddRemark() throws Exception {

        Remark remark = new Remark();
        remark.setUserId(1);
        remark.setResourceId(4);
        remark.setScore(3);
        Integer result = remarkMapper.addRemark(remark);
        Assert.assertNotEquals(new Integer(0),result);
    }

    @Test
    public void testFindRemarkByResourceId() throws Exception {

        List<RemarkVO> remarkVOList = remarkMapper.findRemarkByResourceId(4);
        Assert.assertNotEquals(0,remarkVOList.size());
    }

    @Test
    public void testGetScoreAmount() throws Exception {

        Integer scoreAmount = remarkMapper.getScoreAmount(4);
        Assert.assertNotEquals(new Integer(0),scoreAmount);
    }

    @Test
    public void testGetContentAmount() throws Exception {

        Integer contentAmount = remarkMapper.getContentAmount(4);
        Assert.assertNotEquals(new Integer(0),contentAmount);
    }

    @Test
    public void testGetAverageScore() throws Exception {

        Double averageScore = remarkMapper.getAverageScore(4);
        Assert.assertNotNull(averageScore);
    }

    @Test
    public void testFindRemarkByuserIdAndResourceId() throws Exception {

        Integer result = remarkMapper.findRemarkByuserIdAndResourceId(1,4);
        Assert.assertEquals(new Integer(1),result);
    }
}