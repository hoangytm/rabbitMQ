package com.javainuse.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.LongString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

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

    protected String extractCorrelationIdFromHeaders(AMQP.BasicProperties properties) throws UnsupportedEncodingException {

        String decodedCorrelationId = null;

        if (properties.getHeaders() != null) {

            try {
                Object rawCorrelationId = properties.getHeaders().get(X_TENANT_CODE);

                if (rawCorrelationId == null) {
                    log.info("no correlationId provided in headers");
                    return null;
                }

                byte[] correlationIdAsByteArray = ((LongString) rawCorrelationId).getBytes();

                decodedCorrelationId = new String(correlationIdAsByteArray, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.warn("extracted correlationId, but unable to decode it", e);
            }
        }

        return decodedCorrelationId;


    }
}
