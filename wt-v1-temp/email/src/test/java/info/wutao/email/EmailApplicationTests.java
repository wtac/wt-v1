package info.wutao.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;



    @Test
    public void contextLoads() {
    }

    /**
     * 简单邮件发送
     */
    @Test
    public void sendEmail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        //邮件发送者，需要与授权用户账号一样
        mailMessage.setFrom("13237978737@163.com");

        //邮件接受者
        mailMessage.setTo("mrwutao@foxmail.com");

        //邮件内容
        mailMessage.setText("<span color='red'>hello</span>你好啊");

        //邮件标题
        mailMessage.setSubject("这是标题");

        javaMailSender.send(mailMessage);
    }

    /**
     * 带html格式的邮件发送
     */
    @Test
    public void sendEmail2() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            //设置为true
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            //邮件发送者，需要与授权账号一样
            helper.setFrom("13237978737@163.com");

            //邮件发送者
            helper.setTo("mrwutao@foxmail.com");

            //邮件标题
            helper.setSubject("这是带html格式的邮件测试");

            //邮件内容，需要设置为true
            helper.setText("<b>你好啊</b>", true);

            //发送
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带附件的邮件
     */
    @Test
    public void sendEmail3() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("13237978737@163.com");
            helper.setTo("mrwutao@foxmail.com");
            helper.setSubject("这是一封带附件的邮件");
            helper.setText("<a href='www.baidu.com'>百度</a>", true);

            //添加附件
            String filePath = "C:\\vms\\a.txt";
            FileSystemResource fileSystemResource = new FileSystemResource(filePath);

            //这样会有\\
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, fileSystemResource);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用模板发送邮件
     */
    @Test
    public void sendTemplataEmail() {
        Context context = new Context();
        context.setVariable("username", "邬涛");

        String info = templateEngine.process("hello", context);

        System.out.println(info);

    }

    public static void main(String[] args) {
        /*char c = 'a';
        switch ('a') {
            case 'a':
                System.out.println("a");
            default:
                System.out.println("def");
        }*/
        System.out.println(1.0/100);
    }

    public int count(char c, int i, double d) {
        char a = new Character('a');
        char r = 97;
        return 0;
    }


}

