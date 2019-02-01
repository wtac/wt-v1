package info.wutao.wtv1userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import info.wutao.v1.api.User.IUserService;
import info.wutao.v1.base.BaseServiceImpl;
import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.entity.User;
import info.wutao.v1.mapper.UserMapper;
import info.wutao.v1.pojo.MultiResultBean;
import info.wutao.wtv1userservice.common.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public IBaseDao getBaseDao() {
        return userMapper;
    }


    public int activateUser(User user) {
        return userMapper.activateUser(user);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public User login(User user) {
       return userMapper.selectByLoginName(user);
    }

    @Override
    public String checkIsLogin(String uuid) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        User user = (User) redisTemplate.opsForValue().get(RedisConstant.USER_TOKEN + uuid);

        if (user == null) {
            MultiResultBean resultBean = MultiResultBean.errorResult("没有登录");
            return new Gson().toJson(resultBean);
        }

        //刷新凭证时间
        redisTemplate.expire(RedisConstant.USER_TOKEN + uuid, 30 , TimeUnit.MINUTES);

        MultiResultBean resultBean = MultiResultBean.successResult(new Object[]{user});
        return new Gson().toJson(resultBean);
    }

    @Override
    public int insert(User user) {
        userMapper.insert(user);
        return Integer.parseInt(user.getId().toString());
    }

    @Override
    public int insertSelective(User user) {
        userMapper.insertSelective(user);
        return Integer.parseInt(user.getId().toString());
    }


}
