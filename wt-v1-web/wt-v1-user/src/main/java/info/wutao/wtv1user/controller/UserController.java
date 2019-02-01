package info.wutao.wtv1user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import info.wutao.v1.api.User.IUserService;
import info.wutao.v1.api.email.IEmailService;
import info.wutao.v1.api.sms.ISmsService;
import info.wutao.v1.entity.User;
import info.wutao.wtv1user.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Reference
    private IUserService userService;

    @Reference
    private IEmailService emailService;

    @Reference
    private ISmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/register")
    public String register(User user, ModelMap modelMap) {

        //暂时写死
        user.setUsername("root");
        user.setPassword("root123");
        user.setEmail("mrwutao@foxmail.com");
        String uuid = UUID.randomUUID().toString();
        user.setUuid(uuid);
        //注册，还未激活
        user.setFlag((byte) 0);

        int id = userService.insertSelective(user);

        //发送邮件
        Integer flag = emailService.sendActivateEmail(user.getEmail(), "邮件激活", "localhost:8084/user/activateUser?id="+id+"&uuid="+uuid, user);

        return "success";

    }

    @ResponseBody
    @RequestMapping(value = "/activateUser")
    public String activateUser(User user, ModelMap modelMap) {
        //将flag置为1
        user.setFlag((byte) 1);

        //表中字段设置为1
        userService.activateUser(user);

        user.setUuid(UUID.randomUUID().toString());

        int i = userService.updateByPrimaryKeySelective(user);
        if(i == 0) {
            return "error";
        }
        return "success";
    }

    @RequestMapping(value = "/gotoRegister")
    public String gotoRegister() {
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "getSecurityCode/{phone}")
    public String getSecurityCode(@PathVariable("phone") String phone, ModelMap modelMap) {

        //TODO 先查询该用户是否已经注册
        //User user = userService.getUserByPhone(phone);

        //生成四位验证码
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10)+"");
        }

        //验证码存入redis中，设置生效时间
        //存储方式key value
        //key：user_phone_code+"_id"  例如user_phone_code_13237978737
        String key = "user_phone_code_"+phone;
        String value = code.toString();
        redisTemplate.opsForValue().set(key, value, Config.FAILURE_TIME, TimeUnit.MINUTES);

        //发送邮件
        String info = smsService.smsSender(phone, code.toString(), Config.FAILURE_TIME + "");

        System.err.println(info);

        return info;
    }

    /**
     * 通过手机注册
     */
    @RequestMapping("/registerByPhone")
    public String registerByPhone(User user, String code) {
        //比较验证码是否相同
        String redisCode = (String) redisTemplate.opsForValue().get("user_phone_code_" + user.getPhone());
        if (!code.equals(redisCode)) {
            return "error";
        }

        int userId = userService.insertSelective(user);

        if (userId > 0) {
            //注册成功，清除验证码
            redisTemplate.delete("user_phone_code_" + user.getPhone());
            return "success";
        } else {
            return "error";
        }
    }
}
