package com.test.kafka.consumer;

import com.test.kafka.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "todo-topic", properties = {"spring.json.value.default.type=com.test.kafka.model.Todo"})
    public void todoTopicListener(Todo todo) {
        logger.info("Received a todo message {}, date {}", todo.getId(), new Date());

        // if throw exception while consuming data, retry policy works.

        //throw new IllegalStateException("consume topic exception");
    }

}
