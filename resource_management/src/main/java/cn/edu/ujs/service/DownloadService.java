package cn.edu.ujs.service;

import cn.edu.ujs.entity.Download;

import java.util.List;

/**
 * Created by DELL on 2018/3/3.
 */
public interface DownloadService {

    /**下载资源*/
    public Download addDownload(Download download);

    /**某用户下载列表,返回resource_id数组*/
    public List<Integer> findDownloadByUserId(Integer userId);

    /**某资源下载个数*/
    public Integer getDownloads(Integer resourceId);

    /**判断某用户是否下载某资源*/
    public boolean isUserDownload(Integer userId, Integer resourceId);

}
