package info.wutao.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    //1.定义交换机的名称
    private static final String EXCHANGE_NAME = "fanout_exchange";

    //2.发送消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.2.2");
        connectionFactory.setVirtualHost("/java1807");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("wutao123");

        Connection connection = connectionFactory.newConnection();
        //2. 基于连接对象创建channel
        Channel channel = connection.createChannel();
        //3. 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //4.发送消息
        for (int i = 0; i < 100; i++) {
            String message = "message:"+i;
            //发送到交换机
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("第"+i+"个消息发送成功！");
        }
    }
}
