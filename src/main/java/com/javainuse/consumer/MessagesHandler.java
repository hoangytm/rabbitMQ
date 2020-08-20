package com.javainuse.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Map;

public class MessagesHandler implements MessageListener {

    public void onMessage(Message message) {
        System.out.println("message da vao day");
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        for (Map.Entry<String, Object> header : headers.entrySet()) {
            System.out.println(header.getKey() + " : " + header.getValue());
        }
    }
}