package info.wutao.v1.mapper;

import info.wutao.v1.base.IBaseDao;
import info.wutao.v1.entity.User;

public interface UserMapper extends IBaseDao<User> {

    int activateUser(User user);

    User getUserByPhone(String phone);

    User selectByLoginName(User user);
}