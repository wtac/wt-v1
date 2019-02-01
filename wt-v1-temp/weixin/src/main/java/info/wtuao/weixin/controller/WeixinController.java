package info.wtuao.weixin.controller;

import info.wtuao.weixin.util.SHA1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping(value = "weixin")
public class WeixinController {

    private static final String TOKEN  = "mytoken";

    @RequestMapping(value = "/authentication")
    @ResponseBody
    public String authentication (HttpServletRequest request, @RequestBody String content) {

        /*signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        timestamp	时间戳
        nonce	随机数
        echostr	随机字符串*/
        System.err.println(content);

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        Set<String> set = new TreeSet<>();
        set.add(TOKEN);
        set.add(timestamp);
        set.add(nonce);

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : set) {
            stringBuilder.append(s);
        }

        String encode = SHA1.encode(stringBuilder.toString());

        if (encode.equals(signature)) {
            return echostr;
        }

        return "";
    }
}
