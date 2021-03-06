package cn.edu.ujs.controller;

import cn.edu.ujs.entity.Resource;
import cn.edu.ujs.jsoup.GetResourceData;
import cn.edu.ujs.mapper.ChildCategoryMapper;
import cn.edu.ujs.service.ChildCategoryService;
import cn.edu.ujs.service.ResourceService;
import cn.edu.ujs.util.FileUtil;
import cn.edu.ujs.websocket.WebSocket;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/3/3.
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    WebSocket webSocket;

    @Autowired
    ResourceService resourceService;

    @Autowired
    ChildCategoryMapper childCategoryMapper;

    @RequestMapping("/websocket")
    public String testWebsocket() {
        webSocket.sendMessage("hello");
        return "websocket";
    }

    @GetMapping
    public void comeFromMaster() {

    }

    @RequestMapping("/data/{category}/{type}/{point}/{sort}/{pageNum}")
    public String getData(@PathVariable(value = "category") String category,
                          @PathVariable(value = "type") String type,
                          @PathVariable(value = "point") String point,
                          @PathVariable(value = "sort") String sort,
                          @PathVariable(value = "pageNum") String pageNum) {

        GetResourceData getResourceData = new GetResourceData();
        //List<Resource> resourceList = getResourceData.getData("0","10","0","0","1");
        List<Map<String,Object>> list = childCategoryMapper.findAll();
        for (Map<String,Object> one : list) {

            String keyword = one.get("child_category_name").toString();
            Integer categoryId = new Integer(one.get("id").toString());
            if (!keyword.equals("其他")) {
                for (int i = 0; i < 4; i++) {
                    type = "1";
                    type = Integer.toString(new Integer(type) + i);
                    List<Resource> resourceList = getResourceData.getData(category, type, point, sort, pageNum, keyword);

                    resourceList.forEach((item) -> {
                        item.setCategoryId(categoryId);
                        item.setPosition("E://test//");
                        item.setCheckStatus(1);
                        if (!item.getSuffixName().equals(".unkonw")) {
                            //写入到数据库中
                            resourceService.uploadResource(item);
                            //创建文件
                            File file = new File("E://test//" + item.getTitle() + item.getSuffixName());
                            if (!file.exists())
                                try {
                                    file.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                        }
                    });
                }
            }

        }

        return "success!";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    //文件上传相关代码
    @RequestMapping(value = "upload")
    public String upload(@RequestParam("test") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        String originalFilename = file.getOriginalFilename();
        // 获取文件名
        String fileName = file.getOriginalFilename().substring((file.getOriginalFilename().lastIndexOf("\\"))+1);
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);

        long size = file.getSize();
        String resultSize = FileUtil.setSize(size);
        logger.info("上传的文件大小：" + size);
        // 文件上传后的路径
        String filePath = "E://test//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    //文件下载相关代码
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response){
        String fileName = "外文翻译(有道文档翻译结果).zip";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = "e://test//";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                //response.setContentType("application/octet-stream; charset=utf-8");// 设置强制下载不打开
                //response.addHeader("Content-Disposition",
                 //       "attachment;fileName=" +  fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(HttpServletResponse res) {
        String fileName = "外文翻译(有道文档翻译结果).zip";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("e://test//"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }
}


