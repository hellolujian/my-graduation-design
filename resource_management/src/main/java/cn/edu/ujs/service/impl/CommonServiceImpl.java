package cn.edu.ujs.service.impl;

import cn.edu.ujs.VO.ResourceVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Download;
import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.enums.DownloadEnum;
import cn.edu.ujs.enums.UploadEnum;
import cn.edu.ujs.service.*;
import cn.edu.ujs.util.FileUtil;
import cn.edu.ujs.util.ResultVOUtil;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/3/4.
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private DownloadService downloadService;

    @Override
    public ResultVO downloadResource(Integer userId,
                                     Integer resourceId,
                                     HttpServletResponse httpServletResponse) {

        // TODO: 2018/3/4 自己下载自己的是不会显示的，最好也不要写进数据库里

        ResultVO resultVO = null;
        //获取该资源信息
        Resource resource = resourceService.findByResourceId(resourceId);
        //获取该资源上传者
        Integer srcUserId = resource.getUserId();
        //获取资源全称
        String fileName = resourceService.getResourceNameAndSuffixName(resourceId);
        //获取资源所需积分
        Integer points = resource.getPoints();
        //如果该资源是该用户自己上传的,直接就可以下载
        if (resourceService.isUserUpload(userId,resourceId)) {
            FileUtil.downloadResource(httpServletResponse,fileName);
            resultVO = ResultVOUtil.success(resource);
        } else {
            //判断该用户的积分是否足够
            if (userService.isEnabledToDownload(userId,points)) {
                //判断资源是否下载成功
                boolean isDownload = FileUtil.downloadResource(httpServletResponse,fileName);
                if (isDownload) {
                    //扣除相应积分
                    userService.subUserPoints(userId,points);
                    //增加相应积分
                    userService.addUserPoints(srcUserId,points);
                    //将下载记录写进下载表
                    Download download = new Download();
                    download.setResourceId(resourceId);
                    download.setUserId(userId);
                    downloadService.addDownload(download);
                    resultVO = ResultVOUtil.success(download);
                } else {
                    resultVO = ResultVOUtil.error(DownloadEnum.DOWNLOAD_ERROR.getCode(),
                            DownloadEnum.DOWNLOAD_ERROR.getMessage());
                }
            }
            else {
                resultVO = ResultVOUtil.error(DownloadEnum.DOWNLOAD_NOT_ENOUGHT_POINTS.getCode(),
                        DownloadEnum.DOWNLOAD_NOT_ENOUGHT_POINTS.getMessage());
            }
        }
        return resultVO;
    }

    @Override
    public ResultVO uploadResource(String filePath, MultipartFile file, Resource resource) {

        ResultVO resultVO = null;
        //判断文件是否为空
        if (file.isEmpty()) {
            resultVO = ResultVOUtil.error(UploadEnum.UPLOAD_RESOURCE_IS_NULL.getCode(),
                    UploadEnum.UPLOAD_RESOURCE_IS_NULL.getMessage());
        }
        else {
            //获取文件全名
            String originalFilename = file.getOriginalFilename();
            // 获取文件名
            String fileName = file.getOriginalFilename().substring((file.getOriginalFilename().lastIndexOf("\\"))+1);
            logger.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            logger.info("上传的后缀名为：" + suffixName);
            //获取文件标题
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            logger.info("上传的文件标题：" + title);
            //获取文件大小
            String resultSize = FileUtil.setSize(file.getSize());
            logger.info("上传的文件大小：" + resultSize);
            //判断该文件是否已存在
            // TODO: 2018/3/17 判断文件是否存在 
            if (resourceService.isResourceExist(title,suffixName)) {
                resultVO = ResultVOUtil.error(UploadEnum.UPLOAD_RESOURCE_IS_EXIST.getCode(),
                        UploadEnum.UPLOAD_RESOURCE_IS_EXIST.getMessage());
            }
            else {
                //如果上传成功
                if (FileUtil.uploadResource(file,filePath,fileName)) {
                    //设置资源相关信息
                    resource.setTitle(title);
                    resource.setSize(resultSize);
                    resource.setSuffixName(suffixName);
                    resource.setPosition(filePath);
                    resultVO = ResultVOUtil.success(resource,
                            UploadEnum.UPLOAD_SUCCESS.getCode(),
                            UploadEnum.UPLOAD_SUCCESS.getMessage());
                }
            }

        }
        return resultVO;
    }

    @Override
    public ResultVO userCollection(Integer userId) {

        List<Integer> resourceIdList = collectionService.findCollectionByUserId(userId);
        List<ResourceVO> resourceVOList = getResourceByIdList(resourceIdList);
        ResultVO resultVO = ResultVOUtil.success(resourceVOList);
        return resultVO;
    }

    @Override
    public ResultVO userDownloads(Integer userId) {

        List<Integer> resourceIdList = downloadService.findDownloadByUserId(userId);
        List<ResourceVO> resourceVOList = getResourceByIdList(resourceIdList);
        ResultVO resultVO = ResultVOUtil.success(resourceIdList);
        return resultVO;
    }

    private List<ResourceVO> getResourceByIdList(List<Integer> resourceIdList) {

        List<ResourceVO> resourceVOList = new ArrayList<>();
        for (Integer resourceId : resourceIdList) {
            resourceVOList.add(resourceService.getOneResourceVO(resourceId));
        }
        return resourceVOList;
    }
}
