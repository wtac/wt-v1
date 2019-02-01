package info.wutao.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //创建连接对象
        connectionFactory.setHost("192.168.2.2");
        connectionFactory.setVirtualHost("/java1807");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("wutao123");

        Connection connection = connectionFactory.newConnection();

        //基于连接对象创建channel对象
        Channel channel = connection.createChannel();

        //3. 声明一个队列
        //如果这个队列不存在，则创建
        //存在，则不需要创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //处理消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        //监听队列
        //autoAck :自动确认
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}
