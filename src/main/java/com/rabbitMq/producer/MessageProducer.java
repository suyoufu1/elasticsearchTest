package com.rabbitMq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/11
 * @Version
 */
@Service
@Slf4j
public class MessageProducer {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Resource(name="amqpTemplate2")
    private AmqpTemplate amqpTemplate2;

    public void sendMessage(Object message) throws IOException {
        log.info("to send message:{}", message);
        amqpTemplate.convertAndSend("queueTestKey", message);
        amqpTemplate.convertAndSend("queueTestChris", message);
        amqpTemplate2.convertAndSend("shijj.xxxx.wsdwd", message);
    }
}
