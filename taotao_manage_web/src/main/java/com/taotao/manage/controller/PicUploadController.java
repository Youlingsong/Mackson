package com.taotao.manage.controller;

import com.taotao.common.vo.PicUploadResult;
import com.taotao.manage.service.impl.PropertiesService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("pic")
public class PicUploadController {
    /*只是为了拿到连个地址,非普通service*/
    @Autowired
    public PropertiesService propertiesService;

    /*图片保存的本地路径*/
    public    String REPOSITORY_PATH ="";
    /*返回url的nigix路径*/
    public   String IMAGE_BASE_URL="";



    /*图片文件结尾的后缀,放在集合里*/
    private static final List<String> extensions = Arrays.asList("gif", "png", "jpg", "jpeg", "bmp");

    @PostMapping("upload")
    @ResponseBody
    public PicUploadResult uploadFile(@RequestParam(value="uploadFile", required=false) MultipartFile file) throws IOException {
        System.out.println(propertiesService+"---");
        REPOSITORY_PATH=propertiesService.REPOSITORY_PATH;
        IMAGE_BASE_URL=propertiesService.IMAGE_BASE_URL;
        PicUploadResult picUploadResult = new PicUploadResult();
        /*要封装的格式*/
        //先判断用户上传的文件是否是以jpg,png..结尾的文件
          //这拿到是文件名
        String originalFilename = file.getOriginalFilename();
        //拿到后缀.jpg
        String saftre = StringUtils.substringAfterLast(originalFilename, ".");
        //判断用户上传的是不是图片格式
        if(extensions.contains(saftre)){
            //接着判断内容是不是file
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null){
                picUploadResult.setError(1);
                picUploadResult.setMessage("文件内容不合法");
            }else{
                //生成绝对路径的文件
                String filePath = getFilePath(originalFilename);
                file.transferTo(new File(filePath));
                picUploadResult.setError(0);
                //上传的文件经过检查了，返回个url，图片经过nigix,给一个nigix  http请求
                // 生成图片的绝对引用地址
                String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, REPOSITORY_PATH), "\\", "/");
                picUploadResult.setUrl(IMAGE_BASE_URL + picUrl);
            }
        }else{
            picUploadResult.setError(1);
            picUploadResult.setMessage("文件不是图片格式");
        }
        //生成绝对路径的文件
        System.out.println(picUploadResult);
       return picUploadResult;
    }


    private String getFilePath(String sourceFileName) {
        String baseFolder = REPOSITORY_PATH + File.separator + "images";
        Date nowDate = new Date();
        // yyyy/MM/dd
        String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy") + File.separator
                + new DateTime(nowDate).toString("MM") + File.separator + new DateTime(nowDate).toString("dd");
        File file = new File(fileFolder);
        if (!file.isDirectory()) {
            // 如果目录不存在，则创建目录
            file.mkdirs();
        }
        // 生成新的文件名
        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS") + RandomUtils.nextInt(100, 9999) + "."
                + StringUtils.substringAfterLast(sourceFileName, ".");
        return fileFolder + File.separator + fileName;
    }
}
