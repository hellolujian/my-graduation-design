package cn.edu.ujs.mapper.MapperProvider;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/3/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceMapperProviderTest {

    @Test
    public void testMultiConditionalQuery() throws Exception {

    }

    @Test
    public void testUpdateResourceMultiFieldSql() throws Exception {

        ResourceMapperProvider resourceMapperProvider = new ResourceMapperProvider();
        Integer typeId = null;
        Integer categoryId = null;
        String tagList = null;
        String description = null;
        Integer checkStatus = 0;
        Integer resourceId = null;
       // String sql = resourceMapperProvider.updateResourceMultiFieldSql(typeId,categoryId,tagList,description,checkStatus,resourceId);
       // Assert.assertNotNull(sql);
    }

    @Test
    public void testFindByUserId() throws Exception {

    }
}