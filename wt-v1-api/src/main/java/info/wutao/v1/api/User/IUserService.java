package info.wutao.v1.api.User;

import info.wutao.v1.base.IBaseService;
import info.wutao.v1.entity.User;

public interface IUserService extends IBaseService<User> {
    int activateUser(User user);

    User getUserByPhone(String user);

    User login(User user);

    String checkIsLogin(String uuid);
}
