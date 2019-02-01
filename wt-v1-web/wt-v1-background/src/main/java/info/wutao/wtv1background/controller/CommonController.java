package info.wutao.wtv1background.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import info.wutao.v1.pojo.MultiResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping(value = "common")
public class CommonController {

    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    @Value("${images.server}")
    private String IMAGES_SERVER;

    @ResponseBody
    @RequestMapping(value = "/upload")
    public MultiResultBean upload(MultipartFile[] files) {
        String [] path = new String[files.length];

        for(int i = 0; i < files.length; i++){
            //获取文件后缀名
            String originalFilename = files[i].getOriginalFilename();
            String[] split = originalFilename.split("[.]");
            String postfixName = split[split.length - 1];
            try {
                InputStream inputStream = files[i].getInputStream();
                StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, files[i].getSize(), postfixName, null);

                //拼接成文件全路径，放入数组
                path[i] = IMAGES_SERVER + storePath.getFullPath();
            } catch (IOException e) {
                e.printStackTrace();
                return MultiResultBean.errorResult("系统繁忙，请稍后再试");
            }
        }

        for (String s : path) {
            System.err.println(s);
            
        }
        return MultiResultBean.successResult(path);
    }
}
