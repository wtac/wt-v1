package info.wutao.wtv1serach.serach;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import info.wutao.v1.api.serach.ISerachService;
import info.wutao.v1.entity.Product;
import info.wutao.v1.pojo.PageResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "SerachController")
public class SerachController {

    @Reference
    private ISerachService serachService;

    @RequestMapping(value = "/query/{pageNum}/{pageSize}")
    public String query (String queryName, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, ModelMap modelMap) {
        PageResultBean<Product> pageResultBean = serachService.page(queryName, pageNum, pageSize);

        modelMap.put("pageInfo", pageResultBean);

        return "search";
    }
}
