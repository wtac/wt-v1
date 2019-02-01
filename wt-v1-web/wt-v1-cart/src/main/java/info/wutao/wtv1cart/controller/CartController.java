package info.wutao.wtv1cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import info.wutao.v1.api.cart.ICartService;
import info.wutao.v1.api.cart.constant.CartCookieConstant;
import info.wutao.v1.pojo.MultiResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.UUID;

@Controller
@RequestMapping(value = "cart")
public class CartController {

    @Reference
    private ICartService cartService;



    /**
     * 添加进购物车
     * @param uuid
     * @param productId
     * @param count
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addToCart/{productId}/{count}")
    public String addToCart(@CookieValue(name = CartCookieConstant.GUEST_TAKEN, required = false) String uuid,
                            @PathVariable("productId") long productId, @PathVariable("count") int count,
                            HttpServletResponse response) {

        //判断是否第一次访问
        if (uuid == null || "".equals(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        MultiResultBean resultBean = cartService.addToCart(uuid, productId, count);

        //将cookie写回去
       flushCookie(uuid, response);

        return new Gson().toJson(resultBean);
    }

    @ResponseBody
    @RequestMapping(value = "/resetCount/{productId}/{count}")
    public String resetCount(@CookieValue(name = CartCookieConstant.GUEST_TAKEN, required = false) String uuid,
                             @PathVariable("productId") long productId, @PathVariable("count")int count,
                            HttpServletResponse response ) {
        if (uuid == null || "".equals(uuid)) {
            //购物车不存在
            MultiResultBean resultBean = MultiResultBean.errorResult("不存在购物车");
            return new Gson().toJson(resultBean);
        }

        MultiResultBean resultBean = cartService.resetCount(uuid, productId, count);

        flushCookie(uuid, response);

        return new Gson().toJson(resultBean);

    }

    @ResponseBody
    @RequestMapping(value = "/remove/{productId}")
    public String remove(@CookieValue(name = CartCookieConstant.GUEST_TAKEN, required = false) String uuid,
                         @PathVariable("productId") long productId,
                         HttpServletResponse response) {

        if (uuid == null || "".equals(uuid)) {
            MultiResultBean resultBean = MultiResultBean.errorResult("不存在购物车");
            return new Gson().toJson(resultBean);
        }

        MultiResultBean resultBean = cartService.remove(uuid, productId);

        flushCookie(uuid, response);

        return new Gson().toJson(resultBean);

    }

    @ResponseBody
    @RequestMapping(value = "/getCart")
    public String getCart(@CookieValue(name = CartCookieConstant.GUEST_TAKEN, required = false) String uuid) {

        if (uuid == null || uuid.equals("")) {
            MultiResultBean resultBean = MultiResultBean.errorResult("没有购物车");
        }

        MultiResultBean resultBean = cartService.getCart(uuid);

        return null;
    }


    public void flushCookie(String uuid, HttpServletResponse response) {
        Cookie cookie = new Cookie(CartCookieConstant.GUEST_TAKEN, uuid);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        response.addCookie(cookie);
    }
}
