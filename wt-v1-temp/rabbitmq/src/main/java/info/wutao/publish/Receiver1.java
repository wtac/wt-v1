package info.wutao.publish;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver1 {
    //1.定义交换机的名称
    private static final String EXCHANGE_NAME = "fanout_exchange";
    //2.定义队列的名称
    private static final String QUEUE_NAME = "fanout_queue_1";

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
        //避免消息的堆积，设置每次最多处理一个消息
        channel.basicQos(1);
        //3. 声明一个队列
        //如果这个队列不存在，则创建
        //存在，则不需要创建
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //将队列绑定到交换机上
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //4. 处理消息
        Consumer consumer = new DefaultConsumer(channel){
            //一旦队列有消息，会回调这个方法来处理
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1，接收到消息："+message);
                //手工确认消息已经被处理
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //channel.basicConsume(QUEUE_NAME,true,consumer);
        //将消息的确认模式由自动改为手动
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
