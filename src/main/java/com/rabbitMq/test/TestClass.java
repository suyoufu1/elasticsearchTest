package com.rabbitMq.test;

import com.rabbitMq.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/11
 * @Version
 */
@Slf4j
public class TestClass {

    private ApplicationContext context = null;

    @Before
    public void setUp()  {
        context = new ClassPathXmlApplicationContext ("application.xml");
    }

    @Test
    public void should_send_a_amq_message() throws Exception {
        MessageProducer messageProducer = (MessageProducer) context.getBean("messageProducer");
        int a = 100;
        while (a > 0) {
            messageProducer.sendMessage("Hello, I am amq sender num :" + a--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
