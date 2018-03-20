package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.Collection;
import org.hibernate.hql.internal.ast.tree.AssignmentSpecification;
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
public class CollectionMapperTest {

    @Autowired
    private CollectionMapper collectionMapper;

    @Test
    public void testAddCollection() throws Exception {

        Collection collection = new Collection();
        collection.setUserId(1);
        collection.setResourceId(1);
        Integer result = collectionMapper.addCollection(collection);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testRemoveCollection() throws Exception {

        Integer result = collectionMapper.removeCollection(21);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testFindCollectionByUserId() throws Exception {

        List<Integer> collectionList = collectionMapper.findCollectionByUserId(1);
        Assert.assertNotEquals(0,collectionList.size());
    }

    @Test
    public void testFindByUserIdAndResourceId() throws Exception {

        Integer result = collectionMapper.findByUserIdAndResourceId(1,2);
        Assert.assertEquals(new Integer(1),result);
    }

    @Test
    public void testFindByResourceId() throws Exception {

        Integer result = collectionMapper.findByResourceId(1);
        Assert.assertNotEquals(new Integer(0),result);
    }
}