package cn.edu.ujs.service;

import cn.edu.ujs.entity.Tag;

import java.util.List;

/**
 * Created by DELL on 2018/2/14.
 */
public interface TagService {

    /**添加标签*/
    public Tag addTag(Tag tag);

    /**删除标签*/
    public Tag removeTag(Integer tagId);

    /**修改标签名称*/
    public Tag updateTag(Integer tagId, String tagName);

    /**标签列表*/
    public List<Tag> findAll();
}
