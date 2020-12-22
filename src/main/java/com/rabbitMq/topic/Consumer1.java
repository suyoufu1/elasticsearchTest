package com.rabbitMq.topic;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

import static com.rabbitMq.constant.RabbitConstant.TYPE_TOPIC;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/22
 * @Version
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        String exName = "logs_topic" ;
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel ();
        channel.exchangeDeclare ( exName,TYPE_TOPIC );
        String queue = channel.queueDeclare ().getQueue ();
        channel.queueBind ( queue,exName,"journal.*" );
        channel.basicConsume ( queue,true,new DefaultConsumer ( channel ){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println ("消费者1:"+new String (body));
            }
        } );
    }
}
