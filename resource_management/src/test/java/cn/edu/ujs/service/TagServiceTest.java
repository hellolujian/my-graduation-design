package cn.edu.ujs.service;

import cn.edu.ujs.entity.Tag;
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
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void testAddTag() throws Exception {

        Tag tag = new Tag();
        tag.setTagName("新标签");
        Tag newTag = tagService.addTag(tag);
        Assert.assertNotNull(newTag);
    }

    @Test
    public void testRemoveTag() throws Exception {

        Tag tag = tagService.removeTag(14);
        Assert.assertNotNull(tag);
    }

    @Test
    public void testUpdateTag() throws Exception {

        Tag tag = tagService.updateTag(14,"newTag");
        Assert.assertNotNull(tag);
    }

    @Test
    public void testFindAll() throws Exception {

        List<Tag> tagList = tagService.findAll();
        Assert.assertNotNull(tagList);
    }
}