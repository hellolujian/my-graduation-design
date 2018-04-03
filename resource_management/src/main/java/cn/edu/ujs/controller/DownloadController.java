package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Download;
import cn.edu.ujs.entity.ResourceSuffix;
import cn.edu.ujs.service.CommonService;
import cn.edu.ujs.service.DownloadService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by DELL on 2018/3/6.
 */
@RestController
@RequestMapping(value = "/downloads")
public class DownloadController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private DownloadService downloadService;

    //获取用户的下载列表
    @GetMapping(value = "/userId/{userId}")
    public ResultVO getDownloadsByUserId(@PathVariable(value = "userId") Integer userId) {

        ResultVO resultVO = commonService.userDownloads(userId);
        return resultVO;
    }

    //判断某个用户是否下载某资源
    @GetMapping(value = "user/{userId}/resource/{resourceId}")
    public ResultVO isUserDownload(@PathVariable(value = "userId") Integer userId,
                                   @PathVariable(value = "resourceId") Integer resourceId) {

        boolean result = downloadService.isUserDownload(userId, resourceId);
        ResultVO resultVO = ResultVOUtil.success(result);
        return resultVO;
    }

    //添加下载记录
    @PostMapping(value = "/addDownload")
    public ResultVO addDownload(@RequestParam(value = "userId") Integer userId,
                                @RequestParam(value = "resourceId") Integer resourceId) {

        Download download = new Download();
        download.setUserId(userId);
        download.setResourceId(resourceId);
        ResultVO resultVO = null;
        if (downloadService.addDownload(download) != null)
            resultVO = ResultVOUtil.success(download);
        else
            resultVO = ResultVOUtil.error();
        return resultVO;
    }
}
