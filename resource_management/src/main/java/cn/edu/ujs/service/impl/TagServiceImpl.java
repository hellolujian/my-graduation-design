package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Tag;
import cn.edu.ujs.mapper.TagMapper;
import cn.edu.ujs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/2/14.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public Tag addTag(Tag tag) {

        Integer result = tagMapper.addTag(tag);
        if (result == 1)
            return tag;
        return null;
    }

    @Override
    public Tag removeTag(Integer tagId) {

        Tag tag = tagMapper.findByTagId(tagId);
        Integer result = tagMapper.removeTag(tagId);
        if (result == 1)
            return tag;
        return null;
    }

    @Override
    public Tag updateTag(Integer tagId, String tagName) {

        Integer result = tagMapper.updateTag(tagId,tagName);
        Tag tag = tagMapper.findByTagId(tagId);
        if (result == 1)
            return tag;
        return null;
    }

    @Override
    public List<Tag> findAll() {

        List<Tag> tagList = tagMapper.findAll();
        return tagList;
    }
}
