package com.rabbitMq.topic;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

import static com.rabbitMq.constant.RabbitConstant.TYPE_TOPIC;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/22
 * @Version
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        /**
         * 使用通配符进行匹配路由
         */
        String exName = "logs_topic" ;
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel ();
        channel.exchangeDeclare ( exName,TYPE_TOPIC );
        String key = "journal.logs";
        channel.basicPublish ( exName,key,null,("动态发送消息").getBytes () );
        RabbitUtil.closeConnect ( channel,connectionFactory );
    }
}
