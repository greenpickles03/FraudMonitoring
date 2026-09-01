package com.fraudmonitoring.transactionservicce.messaging;

import com.fraudmonitoring.transactionservicce.dto.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventProducer {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final String TOPIC = "banking.transaction.completed";


    public TransactionEventProducer(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(TransactionEvent event) {
        kafkaTemplate.send(TOPIC, event.transactionReference(), event);
    }



}
