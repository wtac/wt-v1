package info.wutao.wtv1background;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1BackgroundApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void contextLoads() {
    }


    @Test
    public void FileUpload () throws FileNotFoundException {
        File file = new File("C:\\MyDataFile\\ideaWork\\wt-v1\\wt-v1-web\\wt-v1-background\\src\\main\\resources\\static\\images\\dva.jpg");
        FileInputStream fis = new FileInputStream(file);

        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(fis, file.length(), "jpg", null);
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());

    }
}

