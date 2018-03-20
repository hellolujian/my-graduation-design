package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.Download;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
public class DownloadMapperTest {

    @Autowired
    private DownloadMapper downloadMapper;

    @Test
    public void testAddDownload() throws Exception {

        Download download = new Download();
        download.setUserId(1);
        download.setResourceId(4);
        Integer result = downloadMapper.addDownload(download);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testFindDownLoadByUserId() throws Exception {

        List<Integer> downloadList = downloadMapper.findDownLoadByUserId(1);
        Assert.assertNotEquals(0,downloadList.size());
    }

    @Test
    public void testGetDownloads() throws Exception {

        Integer downloads = downloadMapper.getDownloads(4);
        Assert.assertNotEquals(new Integer(0),downloads);
    }

    @Test
    public void testFindDownByuserIdAndResourceId() throws Exception {

        Integer result = downloadMapper.findDownByuserIdAndResourceId(1,4);
        Assert.assertEquals(new Integer(1),result);
    }
}