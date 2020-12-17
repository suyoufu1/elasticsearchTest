package com.rabbitMq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/17
 * @Version
 */
public class RabbitUtil {
    private static ConnectionFactory factory = null;
    static {
        factory = new ConnectionFactory ();
        factory.setHost("172.20.52.208");
        factory.setPort ( 5672 );
        factory.setUsername ( "admin" );
        factory.setPassword ( "admin" );
        factory.setVirtualHost ( "my_vhost" );

    }
    // 创建连接
   public static Connection getConnectionFactory(){
        try{
            Connection connection = factory.newConnection();
            return connection;
        }catch (Exception e){
            e.printStackTrace ();
        }
        return null;

   }
   // 关闭连接
    public static void closeConnect(){
        try {
            Connection connection = factory.newConnection();
            connection.close ();
        }catch (Exception e){
            e.printStackTrace ();
        }
    }
}
