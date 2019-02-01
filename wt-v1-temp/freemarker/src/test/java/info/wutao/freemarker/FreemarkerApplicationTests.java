package info.wutao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import info.wutao.freemarker.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreemarkerApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() {
        try {
            Template template = configuration.getTemplate("hello.ftl");
            Map map = new HashMap<>();
            map.put("message", "你好，freemarker");
            FileWriter fileWriter = new FileWriter("C:\\MyDataFile\\ideaWork\\wt-v1\\wt-v1-temp\\freemarker\\src\\main\\resources\\static\\hello.html");
            template.process(map, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFreemarker () {
        try {
            Template template = configuration.getTemplate("hello.ftl");
            Map dataModel = new HashMap();

            dataModel.put("message", "hello freemarker");

            Person person = new Person();
            person.setId(1024857918L);
            person.setAge(21);
            person.setName("邬涛");
            person.setFlag(true);

            person.setBirth(new Date());
            dataModel.put("person", person);
            FileWriter fileWriter = new FileWriter("C:\\MyDataFile\\ideaWork\\wt-v1\\wt-v1-temp\\freemarker\\src\\main\\resources\\static\\hello1.html");

            template.process(dataModel, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}

