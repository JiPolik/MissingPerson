package com.kedop.missingperson.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author oleksandrpolishchuk on 26.02.2023
 * @project MissingPerson
 */
@Slf4j
public class MessageConsumer {

    //TODO make a listener to listen dia team
    @KafkaListener(topics = "missingPerson", groupId = "kedop")
    public void missingPersonLister(String incomingMessage) {
        log.info("Retrieved message: {}", incomingMessage);
    }
}
