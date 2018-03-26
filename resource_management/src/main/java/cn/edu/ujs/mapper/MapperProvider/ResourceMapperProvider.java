package cn.edu.ujs.mapper.MapperProvider;

import cn.edu.ujs.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by DELL on 2018/3/5.
 */
public class ResourceMapperProvider {

    /*
    select r.id,r.resource_title,r.description,ifnull(d.downloads,0)as downloads,r.resource_size,
    r.resource_suffix_name,s.suffix_image,r.resource_position,r.points,r.check_status,u.username,
    r.tag_list,r.create_time,r.update_time from resource r left join
    (select resource_id,count(user_id)as downloads from download group by resource_id) d
    on r.id=d.resource_id
    left join user u on r.user_id=u.id
    left join resource_suffix s on r.resource_suffix_name=s.suffix_name
    left join child_category c on r.resource_category_id=c.id where 1=1;
     */
    private static String prefixSql = "select r.id,r.resource_title,concat(r.resource_title,r.resource_suffix_name) as name,r.description,ifnull(d.downloads,0)as downloads," +
            "r.resource_size,r.resource_suffix_name,s.suffix_image,r.resource_position,r.points,t.type_name as type,r.update_time," +
            "r.check_status,u.username,r.tag_list,r.create_time,concat_ws('/',p.parent_category_name,c.child_category_name) as category " +
            "from resource r left join " +
            "(select resource_id,count(user_id)as downloads from download group by resource_id) d " +
            "on r.id=d.resource_id " +
            "left join user u on r.user_id=u.id " +
            "left join resource_suffix s on r.resource_suffix_name=s.suffix_name " +
            "left join child_category c on r.resource_category_id=c.id " +
            "left join parent_category p on c.parent_category_id = p.id " +
            "left join resource_type t on r.resource_type_id=t.id where 1=1";

    //多条件查询的SQL语句
    public String multiConditionalQuery(@Param("parentCategoryId") Integer parentCategoryId,
                                        @Param("childCategoryId") Integer childCategoryId,
                                        @Param("resourceTypeId") Integer resourceTypeId,
                                        @Param("checkStatus") Integer checkStatus,
                                        @Param("sortType") Integer sortType,
                                        @Param("keyword") String keyword) {

        StringBuffer sql = new StringBuffer(prefixSql);
        if (parentCategoryId != null)
            sql.append(" and c.parent_category_id=#{parentCategoryId}");
        if (childCategoryId != null)
            sql.append(" and r.resource_category_id=#{childCategoryId}");
        if (resourceTypeId != null)
            sql.append(" and r.resource_type_id=#{resourceTypeId}");
        if (checkStatus != null)
            sql.append(" and check_status=#{checkStatus}");
        if (keyword != null)
            sql.append(" and r.resource_title like CONCAT('%',#{keyword},'%') or r.description like CONCAT('%',#{keyword},'%')");
        //排序方式，0默认排序，1最新上传，2最多下载
        if (sortType != null) {
            if (sortType == 1)
                sql.append(" order by update_time desc");
            else if (sortType == 2)
                sql.append(" order by downloads desc");
        }

        return sql.toString();
    }

    private static String updatePrefix = "update resource set ";
    //编辑资源
    public String updateResourceMultiFieldSql(Resource resource) {

        Integer typeId = resource.getTypeId();
        Integer categoryId = resource.getCategoryId();
        List<String> tagList = resource.getTagList();
        String description = resource.getDescription();
        Integer checkStatus = resource.getCheckStatus();
        Integer resourceId = resource.getId();
        StringBuffer sql = new StringBuffer(updatePrefix);
        if (typeId != null) {
            sql.append("resource_type_id=#{typeId},");
        }
        if (categoryId != null) {
            sql.append("resource_category_id=#{categoryId},");
        }
        if (tagList != null) {
            sql.append("tag_list=#{tagList},");
        }
        if (description != null) {
            sql.append("description=#{description},");
        }
        if (checkStatus != null) {
            sql.append("check_status=#{checkStatus},");
        }
        //删除最后一个逗号
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where id=#{id}");
        return sql.toString();
    }

    //编辑资源
    public String updateResourceMultiField(@Param("typeId") Integer typeId,
                                              @Param("categoryId") Integer categoryId,
                                              @Param("tagList") String tagList,
                                              @Param("description") String description,
                                              @Param("checkStatus") Integer checkStatus,
                                              @Param("resourceId") Integer resourceId) {

        StringBuffer sql = new StringBuffer(updatePrefix);
        if (typeId != null) {
            sql.append("resource_type_id=#{typeId},");
        }
        if (categoryId != null) {
            sql.append("resource_category_id=#{categoryId},");
        }
        if (tagList != null) {
            sql.append("tag_list=#{tagList},");
        }
        if (description != null) {
            sql.append("description=#{description},");
        }
        if (checkStatus != null) {
            sql.append("check_status=#{checkStatus},");
        }
        //删除最后一个逗号
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where id=#{resourceId}");
        return sql.toString();
    }

    public String findByUserId(Integer userId) {

        StringBuffer sql = new StringBuffer(prefixSql);
        sql.append(" and r.user_id=#{userId}");
        return sql.toString();
    }
}
