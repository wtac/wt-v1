package info.wutao.v1.api.email;

import info.wutao.v1.entity.User;

public interface IEmailService {
    Integer sendActivateEmail(String to, String subject, String url, User user);
}
