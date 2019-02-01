package info.wutao.wtv1emailservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import info.wutao.v1.api.email.IEmailService;
import info.wutao.v1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${email.form}")
    private String emailForm;

    @Override
    public Integer sendActivateEmail(String to, String subject, String url, User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(emailForm);
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("url", url);
            context.setVariable("email", user.getEmail());
            String info = templateEngine.process("RegisterActivate", context);

            helper.setText(info, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }
}
