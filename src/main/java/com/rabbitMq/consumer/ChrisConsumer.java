package com.rabbitMq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/11
 * @Version
 */
@Slf4j
public class ChrisConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        log.info ( "chris receive message------->:{}", message );
    }
}
