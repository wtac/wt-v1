package info.wutao.wtv1itemservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import info.wutao.v1.api.item.ItemService;
import info.wutao.v1.entity.Product;
import info.wutao.v1.pojo.MultiResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private Configuration configuration;

    @Value("${template.path}")
    private String TEMPLATE_PATH;

    @Override
    public MultiResultBean createPage(Product product) {
        try {
            Template template = configuration.getTemplate("detail.ftl");

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("product", product);

            FileWriter fileWriter = new FileWriter(TEMPLATE_PATH+product.getId()+".html");

            template.process(dataModel, fileWriter);

            if (fileWriter != null) {

                fileWriter.close();
            }

            return MultiResultBean.successResult(new String[]{"success"});
        } catch (IOException e) {
            e.printStackTrace();
            return MultiResultBean.errorResult("error,id="+product.getId());
        } catch (TemplateException e) {
            e.printStackTrace();
            return MultiResultBean.errorResult("error,id="+product.getId());
        }

    }
}
