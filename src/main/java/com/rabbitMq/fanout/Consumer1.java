package com.rabbitMq.fanout;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/21
 * @Version
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel ();
        channel.exchangeDeclare ( "logs","fanout" );
        // 创建临时队列
        String queue = channel.queueDeclare ().getQueue ();
        // 队列绑定交换机
        channel.queueBind ( queue,"logs","" );
        // 处理消息
        channel.basicConsume ( queue,true,new DefaultConsumer ( channel ){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println ("消费者-1："+new String (body));
            }
        } );
    }
}
