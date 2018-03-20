package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Collection;
import cn.edu.ujs.mapper.CollectionMapper;
import cn.edu.ujs.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.InetAddressEditor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/6.
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public Collection addCollection(Collection collection) {

        Integer result = collectionMapper.addCollection(collection);
        if (result == 1)
            return collection;
        return null;
    }

    @Override
    public Collection removeCollection(Integer collectionId) {

        Collection collection = collectionMapper.findByCollectionId(collectionId);
        Integer result = collectionMapper.removeCollection(collectionId);
        if (result == 1)
            return collection;
        return null;
    }

    @Override
    public List<Integer> findCollectionByUserId(Integer userId) {

        List<Integer> resourceIdList = collectionMapper.findCollectionByUserId(userId);
        return resourceIdList;
    }

    @Override
    public boolean isUserCollect(Integer userId, Integer resourceId) {

        Integer result = collectionMapper.findByUserIdAndResourceId(userId,resourceId);
        if (result == 1)
            return true;
        return false;
    }

    @Override
    public Integer getCollectionAmount(Integer resourceId) {

        Integer collectionAmount = collectionMapper.findByResourceId(resourceId);
        return collectionAmount;
    }
}
