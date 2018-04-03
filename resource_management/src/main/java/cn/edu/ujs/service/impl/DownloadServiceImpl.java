package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Download;
import cn.edu.ujs.mapper.DownloadMapper;
import cn.edu.ujs.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/3/4.
 */
@Service
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private DownloadMapper downloadMapper;

    @Override
    public Download addDownload(Download download) {

        Integer result = downloadMapper.addDownload(download);
        if (result == 1)
            return download;
        return null;
    }

    @Override
    public List<Integer> findDownloadByUserId(Integer userId) {

        List<Integer> downloadList = downloadMapper.findDownLoadByUserId(userId);
        return downloadList;
    }

    @Override
    public Integer getDownloads(Integer resourceId) {

        Integer downloads = downloadMapper.getDownloads(resourceId);
        return downloads;
    }

    @Override
    public boolean isUserDownload(Integer userId, Integer resourceId) {

        Integer result = downloadMapper.findDownByuserIdAndResourceId(userId, resourceId);
        //是可以重复下载的，所以记录可能大于一
        if (result >= 1)
            return true;
        return false;
    }
}
