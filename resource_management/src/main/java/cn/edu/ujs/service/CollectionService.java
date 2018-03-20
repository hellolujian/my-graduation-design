package cn.edu.ujs.service;

import cn.edu.ujs.entity.Collection;

import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
public interface CollectionService {

    /**添加收藏记录*/
    public Collection addCollection(Collection collection);

    /**移除收藏记录*/
    public Collection removeCollection(Integer collectionId);

    /**收藏记录列表,返回resource_id*/
    public List<Integer> findCollectionByUserId(Integer userId);

    /**判断某个用户是否收藏了某资源*/
    public boolean isUserCollect(Integer userId, Integer resourceId);

    /**获取某个资源的收藏个数*/
    public Integer getCollectionAmount(Integer resourceId);

}
