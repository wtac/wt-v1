package info.wutao.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    //定义队列名字
    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.2.2");
        connectionFactory.setVirtualHost("/java1807");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("wutao123");

        Connection connection = connectionFactory.newConnection();

        //基于连接对象创建Channel对象
        Channel channel = connection.createChannel();

        //声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送消息
        for (int i = 0; i < 100; i++) {
            String message = "今天天气好冷，冻死人"+i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("第"+i+"条消息发送完成");
        }


    }
}
