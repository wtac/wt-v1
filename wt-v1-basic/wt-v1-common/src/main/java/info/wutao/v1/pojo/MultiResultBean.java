package info.wutao.v1.pojo;

import java.io.Serializable;

public class MultiResultBean implements Serializable {

    private Integer errno;

    private Object[] data;

    private String message;

    public static MultiResultBean errorResult(String message){
        MultiResultBean resultBean = new MultiResultBean();
        resultBean.setErrno(1);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static MultiResultBean successResult(Object[] data){
        MultiResultBean resultBean = new MultiResultBean();
        resultBean.setErrno(0);
        resultBean.setData(data);
        return resultBean;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
