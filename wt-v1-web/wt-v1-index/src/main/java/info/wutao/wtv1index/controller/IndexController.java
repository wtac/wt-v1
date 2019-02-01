package info.wutao.wtv1index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import info.wutao.v1.api.productType.IProductTypeService;
import info.wutao.v1.entity.Product;
import info.wutao.v1.entity.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "showIndex")
    public String showIndex(ModelMap modelMap){

        List<ProductType> list = (List<ProductType>) redisTemplate.opsForValue().get("index:prodectType:list");

        if (list == null) {

            //获取分布式锁
            String lockValue = UUID.randomUUID().toString();
            Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("index:prodectType:list:lock", lockValue);
            try {

                if (ifAbsent) {
                    //拿到了锁，从数据库获取信息，保存到缓存
                    list= productTypeService.getList();
                    redisTemplate.opsForValue().set("index:prodectType:list", list);
                    modelMap.put("productTypes", list);
                } else {
                    //没拿到锁，休眠十毫秒后调用本方法
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        showIndex(modelMap);
                        e.printStackTrace();
                    }
                }
            } finally {
                Object currentValue = redisTemplate.opsForValue().get("index:prodectType:list:lock");
                if (lockValue.equals(currentValue)) {
                    //删除锁
                    redisTemplate.delete("index:prodectType:list:lock");
                }
            }

        } else {

            list = productTypeService.getList();
            modelMap.put("productTypes", list);
        }

        return "index";
    }
}
