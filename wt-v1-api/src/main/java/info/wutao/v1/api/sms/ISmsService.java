package info.wutao.v1.api.sms;

public interface ISmsService {

    String smsSender(String phone, String code, String failureTme);
}
