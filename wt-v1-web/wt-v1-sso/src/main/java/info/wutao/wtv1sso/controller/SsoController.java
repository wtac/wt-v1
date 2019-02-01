package info.wutao.wtv1sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import info.wutao.v1.api.User.IUserService;
import info.wutao.v1.entity.User;
import info.wutao.v1.pojo.MultiResultBean;
import info.wutao.wtv1sso.common.CookieConstant;
import info.wutao.wtv1sso.common.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class SsoController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 转发到登录页面
     * @return
     */
    @RequestMapping(value = "/gotoLogin")
    public String gotoLogin() {
        return "login";
    }

    /**
     * 登录
     * @param user
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(User user, HttpServletResponse response, ModelMap modelMap) {

        User resultUser = userService.login(user);

        //账号不存在
        if (resultUser == null) {
            modelMap.put("errmsg", "账号不存在");
            return "login";
        }

        //账号密码不匹配
        if (!user.getPassword().equals(resultUser.getPassword())) {
            modelMap.put("errmsg", "账号或者密码不正确");
            return "login";
        }

        //登录名和密码匹配
        String uuid = UUID.randomUUID().toString();
        String redisKey = RedisConstant.USER_TOKEN+uuid;
        //存入redis中对象，清空密码
        resultUser.setPassword(null);

        //登录成功，存放凭证，有效时间30分钟
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(redisKey, user);
        redisTemplate.expire(redisKey, 30, TimeUnit.MINUTES);

        //写cookie
        Cookie cookie = new Cookie(CookieConstant.USER_TOKEN, uuid);
        cookie.setPath("/");
        //设置无法由客户端访问
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return "redirect:http://localhost:8082/showIndex";
    }

    /**
     * 检查是否登录
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkIsLogin", produces = "text/html;charset=utf-8")
    public String checkIsLogin(@CookieValue(name = CookieConstant.USER_TOKEN, required = false) String uuid, String callback) {
        String json = userService.checkIsLogin(uuid);
        return json;
//        String json = userService.checkIsLogin(uuid);
//        return callback+"("+json+")";
    }


    @RequestMapping(value = "/logout")
    public String logout (@CookieValue(name = CookieConstant.USER_TOKEN, required = false) String uuid) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.delete(RedisConstant.USER_TOKEN+uuid);
        return "redirect:http://localhost:8085/gotoLogin/";
    }

    @ResponseBody
    @RequestMapping(value = "/checkIsLoginForJsonp", produces = "text/html;charset=utf-8")
    public String checkIsLoginForJsonp(@CookieValue(name = CookieConstant.USER_TOKEN, required = false) String uuid, String callback){
        String json = userService.checkIsLogin(uuid);
        return callback+"("+json+")";
    }
}
