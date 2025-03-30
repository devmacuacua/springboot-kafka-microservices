package com.devmacuacua.orderservice.kafka;

import com.devmacuacua.basedomains.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    private NewTopic orderTopic;

    private KafkaTemplate<String, OrderEvent> orderKafkaTemplate;

    public OrderProducer(NewTopic orderTopic, KafkaTemplate<String, OrderEvent> orderKafkaTemplate) {
        this.orderTopic = orderTopic;
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    public  void sendMessage(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event => %s", orderEvent.toString()));

        //create a message
        Message<OrderEvent> message = MessageBuilder.withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,orderTopic.name())
                .build();

        orderKafkaTemplate.send(message);
    }
}
