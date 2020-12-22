package com.rabbitMq;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/10
 * @Version
 */
public class Send {
    private final static  String QUEUE_HELLO = "hello";

    public static void main(String[] args) throws Exception{
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        try (Channel channel = connectionFactory.createChannel() ) {
            channel.queueDeclare ( QUEUE_HELLO, false, false, false, null );
            String message = "Hello World!";
            channel.basicPublish ( "", QUEUE_HELLO, null, message.getBytes ( StandardCharsets.UTF_8 ) );
            System.out.println ( " [x] Sent '" + message + "'" );
            RabbitUtil.closeConnect (channel,connectionFactory);
        }

    }
}
