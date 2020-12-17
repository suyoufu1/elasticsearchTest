package com.rabbitMq;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/10
 * @Version
 */
public class Recv {
    private final static  String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
