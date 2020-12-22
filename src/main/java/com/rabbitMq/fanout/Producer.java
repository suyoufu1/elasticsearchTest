package com.rabbitMq.fanout;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/21
 * @Version
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        // 创建连接
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        Channel channel = connectionFactory.createChannel ();
        // 声明交换机
        channel.exchangeDeclare ( "logs","fanout" );
        // 发布消息
        channel.basicPublish ( "logs","",null,"message logs".getBytes () );
        // 关闭连接
        RabbitUtil.closeConnect ( channel,connectionFactory );
    }
}
