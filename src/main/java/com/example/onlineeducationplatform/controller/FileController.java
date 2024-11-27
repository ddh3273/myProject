//package com.example.springboot.controller;
//
//import cn.hutool.core.io.FileUtil;
//import com.example.springboot.common.AuthAccess;
//import com.example.springboot.common.Result;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
///**
// * 功能：
// * 作者：ddh
// * 日期： 2024/10/4 20:00
// */
//@RestController
//@RequestMapping("/file")
//public class FileController {
//
//    @Value("${ip:localhost}")
//    String ip;
//    @Value("${server.port}")
//    String port;
//
//    private static final String ROOT_PATH=System.getProperty("user.dir")+File.separator+"files";  //D:\springboot\springboot\files
//
//    @AuthAccess  //允许跨域
//    @PostMapping("/upload")
//    public Result upload(MultipartFile file) throws IOException {
//        String originalFilename=file.getOriginalFilename();//文件的原始名称
//        String mainName= FileUtil.mainName((originalFilename)); //aaa
//        String extName = FileUtil.extName("文件的后缀");//png
//
////  ①      File parentFile = new File(ROOT_PATH);
////        if(!parentFile.exists()){
////            parentFile.mkdirs();//如果当前文件的父级目录不存在，则创建
////        }②
//        if(FileUtil.exists(ROOT_PATH)){
//            FileUtil.mkdir(ROOT_PATH);
//        }
//
//       //D:\springboot\springboot\files\123131_aaa.png
////        File saveFile=new File(ROOT_PATH+File.separator+originalFilename);
//        if(FileUtil.exists(ROOT_PATH+File.separator+originalFilename)){//若果当前文件存在，那么就该重命名文件
//            originalFilename=System.currentTimeMillis()+"_"+ mainName+"."+extName;
//        }
//        File saveFile=new File(ROOT_PATH+File.separator+originalFilename);
//        file.transferTo(saveFile); //存储文件
//        String url="http://"+ip+":"+port+"/file/download/"+originalFilename;
//        return  Result.success(url);//返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
//    }
//
//    @AuthAccess
//    @GetMapping("/download/{fileName}")
//    public void download(@PathVariable String fileName , HttpServletResponse response) throws IOException {
//        String filePath = ROOT_PATH+File.separator+fileName;
//        if(FileUtil.exists(filePath)){
//
//        }
//        ServletOutputStream outputStream=response.getOutputStream();
//        outputStream.write(new byte[1024]);  //数组是一个字节数组，也就是文件的字节流数组
//    }
//}
//
//
//
package com.example.onlineeducationplatform.controller;

import cn.hutool.core.io.FileUtil;
import com.example.onlineeducationplatform.common.AuthAccess;
import com.example.onlineeducationplatform.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 功能：
 * 作者：ddh
 * 日期： 2024/10/4 20:00
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;
    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";  //D:\springboot\springboot\files

    @AuthAccess  //允许跨域
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); // 文件的原始名称
        String mainName = FileUtil.mainName(originalFilename); // aaa
        String extName = FileUtil.extName(originalFilename); // 获取文件的后缀

        Path rootPath = Paths.get(ROOT_PATH); // 将 ROOT_PATH 转换为 Path 对象

        // 确保文件目录存在
        if (!Files.exists(rootPath)) {
            Files.createDirectories(rootPath); // 如果当前文件的父级目录不存在，则创建
        }

        // 处理文件重命名
        Path saveFilePath = rootPath.resolve(originalFilename); // 组合路径
        if (Files.exists(saveFilePath)) { // 如果当前文件存在，重命名文件
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
            saveFilePath = rootPath.resolve(originalFilename); // 更新 saveFilePath
        }

        // 存储文件
        file.transferTo(saveFilePath.toFile());
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url); // 返回文件的链接
    }

    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));//附件下载
        response.addHeader("Content-Disposition","inline;filename="+ URLEncoder.encode(fileName,"UTF-8"));//预览

        Path filePath = Paths.get(ROOT_PATH, fileName); // 使用 Path 组合文件路径
        if (! Files.exists(filePath)) { // 检查文件是否存在
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream=response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}

