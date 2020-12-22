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
public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel ();
        String exName = "logs_direct" ;
        String type = "direct" ;
        String routingKey = "info";
        channel.exchangeDeclare ( exName,type );
        String queue = channel.queueDeclare ().getQueue ();
        channel.queueBind ( queue,exName, routingKey);
        channel.basicConsume ( queue,true,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println ("消费者2："+new String (body));
            }
        } );
    }
}
