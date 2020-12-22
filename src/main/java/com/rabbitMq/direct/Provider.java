package com.rabbitMq.direct;

import com.rabbitMq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import org.springframework.amqp.rabbit.connection.RabbitAccessor;

import java.io.IOException;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/22
 * @Version
 */
public class Provider {
    /**
     * 在发布订阅fanout进行了改进->利用路由关联队列消息发送那个消费者消费
     * 在之前的是生成一个消息，每个订阅的消费者都要消费，现在想让固定的消费者消费某个消息
     * 所以引进路由routing来进行消息分转发，队列绑定交换机，然后关联路由key就能知道那个消费者消息那个消息
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //创建连接
        Connection connectionFactory = RabbitUtil.getConnectionFactory ();
        // 声明通道
        Channel channel = connectionFactory.createChannel ();
        // 声明交换机 参数1：交换机名称 参数2：交换机类型
        channel.exchangeDeclare ( "logs_direct","direct" );
        // 创建routing key
        String key = "info";
        // 发送消息
        channel.basicPublish ( "logs_direct",key,null,("生产消息:"+key).getBytes () );
        // 关闭连接
        RabbitUtil.closeConnect ( channel,connectionFactory );
    }
}
