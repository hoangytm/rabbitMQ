package com.javainuse.producer;

import com.javainuse.ConfigureRabbitMq;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    private final RabbitTemplate rabbitTemplate;
    public static final String ROUTING_KEY = "mike.springmessages";

    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String themessage) {
        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME,ROUTING_KEY
                , themessage);
        rabbitTemplate.convertAndSend(ROUTING_KEY, "", message -> {
            message.getMessageProperties().getHeaders().put("foo", "bar");
            message.getMessageProperties().setPriority(10000);
            return message;
        });
//        MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
//        props.setHeader("headerKey1", "headerValue1");
//
//        Message msg = new Message("{'body':'value1','body2':value2}".getBytes(), props);
//
//        rabbitTemplate.send(ROUTING_KEY, msg);
        return "We have sent a message! :" + themessage;
    }

}
