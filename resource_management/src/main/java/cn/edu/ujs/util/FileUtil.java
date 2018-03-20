package cn.edu.ujs.util;

import cn.edu.ujs.entity.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by DELL on 2018/3/3.
 */
public class FileUtil {

    /**上传资源*/
    public static Resource uploadResource(MultipartFile file, String filePath, Resource resource) {

        // TODO: 2018/3/3 判断文件是否为空放在controller里
        //获取文件全名（包括路径）
        String originalFilename = file.getOriginalFilename();
        // 获取文件名
        String fileName = originalFilename.substring((originalFilename.lastIndexOf("\\")) + 1);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //获取文件大小
        String size = FileUtil.setSize(file.getSize());
        // 文件上传后的路径
        // TODO: 2018/3/3 后面将文件上传的路径写进配置文件里
        //String filePath = "E://test//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            resource.setSize(size);
            resource.setTitle(fileName.substring(0,fileName.lastIndexOf(".")-1));
            resource.setSuffixName(suffixName);
            return resource;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**上传资源*/
    public static boolean uploadResource(MultipartFile file, String filePath, String fileName) {

        boolean isUpload = false;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            isUpload = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    /**上传后缀图标*/
    public static boolean uploadResource(MultipartFile file) {

        boolean isUpload = false;
        String fileName = file.getOriginalFilename();
        // TODO: 2018/3/14 后面配置路径 
        String filePath = "E:\\毕业设计\\resource_management\\src\\main\\resources\\static\\image\\suffix_icon\\";
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            isUpload = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public static boolean downloadResource(HttpServletResponse res, String fileName) {

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
            return true;
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
        return false;
    }

    /**将文件大小进行单位转换*/
    public static String setSize(long size) {
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }
}
