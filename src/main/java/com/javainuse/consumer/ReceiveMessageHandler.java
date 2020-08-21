package com.javainuse.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.LongString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static com.javainuse.ConfigureRabbitMq.QUEUE_NAME;
import static com.javainuse.ConfigureRabbitMq.QUEUE_NAME2;
import static com.javainuse.producer.SendMessageController.X_TENANT_CODE;

@Service
@Slf4j
public class ReceiveMessageHandler {

    public void handleMessage(byte[] message) {
//        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        log.info("HandleMessage!!!");
        log.info(message.toString());
        String encoded = Base64.getEncoder().encodeToString(message);
        log.info(encoded);
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void handleBookDocNotifyMessage(Message message, @Header(X_TENANT_CODE) String tenantCode) {
        System.out.println("i'm coming 2nd");
        System.out.println(tenantCode);
        log.error(message.getMessageProperties().getHeaders().get(X_TENANT_CODE).toString());
    }

    @RabbitListener(queues = QUEUE_NAME2)
    public void abc(Message message, @Header(X_TENANT_CODE) String tenantCode) {
        System.out.println("i'm coming 2nd");
        System.out.println(tenantCode);
        log.error(message.getMessageProperties().getHeaders().get(X_TENANT_CODE).toString());
    }
}
