package com.rabbitMq.direct;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/22
 * @Version
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel ();
        channel.exchangeDeclare ( "logs_direct","direct");
        // 创建临时队列
        String queue = channel.queueDeclare ().getQueue ();
        // 绑定队列与交换机
        channel.queueBind (queue, "logs_direct","routingKey" );

        channel.basicConsume ( queue,true ,new DefaultConsumer ( channel ){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println ("消费者1："+new String (body));
            }
        } );
    }
}
