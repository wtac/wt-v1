package info.wutao.wtv1background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import info.wutao.v1.api.item.ItemService;
import info.wutao.v1.api.product.IProductService;
import info.wutao.v1.api.vo.ProductVo;
import info.wutao.v1.entity.Page;
import info.wutao.v1.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("ProductController")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private ItemService itemService;


    @RequestMapping(value = "/getList")
    public String getList (ModelMap modelMap) {
        List<Product> list = productService.getList();

        modelMap.put("list", list);

        return "product/productList";
    }

    @RequestMapping(value = "/getPage/{currentPage}/{pageSize}")
    public String getPage (@PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize, ModelMap modelMap) {
        PageInfo<Product> pageInfo =  productService.getPage(currentPage, pageSize);

        modelMap.put("pageInfo", pageInfo);
        modelMap.put("url", "ProductController/getPage");

        return "product/productList";
    }

    @RequestMapping(value = "/add")
    public String add (ProductVo productVo) {
        productVo = productService.add(productVo);

        //添加到solr库

        //添加商品详情页面
        itemService.createPage(productVo.getProduct());


        return "redirect:/ProductController/getPage/1/5";
    }
}
