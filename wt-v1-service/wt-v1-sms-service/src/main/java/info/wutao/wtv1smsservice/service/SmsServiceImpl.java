package info.wutao.wtv1smsservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import info.wutao.v1.api.sms.ISmsService;
import info.wutao.wtv1smsservice.common.Config;
import info.wutao.wtv1smsservice.common.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

@Component
@Service
public class SmsServiceImpl implements ISmsService {



    @Override
    public String smsSender(String phone, String code, String failureTime) {
        StringBuilder smsContent = new StringBuilder();
        smsContent.append("【武拓科技】您的验证码为").append(code).append("，请于").append(failureTime).append("分钟内正确输入，如" +
                "非本人操作，请忽略此短信。");
        String tmpSmsContent = null;
        try{
            tmpSmsContent = URLEncoder.encode(smsContent.toString(), "UTF-8");
        }catch(Exception e){

        }
        String url = Config.BASE_URL + Config.operation;
        String body = "accountSid=" + Config.ACCOUNT_SID + "&to=" + phone + "&smsContent=" + tmpSmsContent
                + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);

        return result;
    }
}
