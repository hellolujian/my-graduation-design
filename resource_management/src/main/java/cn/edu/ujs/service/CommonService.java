package cn.edu.ujs.service;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by DELL on 2018/3/3.
 */
public interface CommonService {

    /**用户下载资源*/
    public ResultVO downloadResource(Integer userId,
                                     Integer resourceId,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse);

    /**用户上传资源*/
    public ResultVO uploadResource(String filePath,
                                   MultipartFile file,
                                   Resource resource);

    /**获取某个用户的收藏列表*/
    public ResultVO userCollection(Integer userId);

    /**获取某个用户的下载列表*/
    public ResultVO userDownloads(Integer userId);

    /**获取单个资源的详细信息*/
    public Map<String,Object> getOneResource(Integer resourceId);
}
