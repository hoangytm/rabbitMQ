package com.javainuse.consumer;

import jdk.internal.instrumentation.Tracer;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author PhanHoang
 * 8/20/2020
 */
public class TestPostProcessor implements MessagePostProcessor {

    @Autowired
    Tracer defaultTracer;

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        System.out.println(message);
        return message;
    }
}