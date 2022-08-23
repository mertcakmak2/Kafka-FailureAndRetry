package com.test.kafka.producer;

import com.test.kafka.model.Todo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTodoToKafka(Todo todo) {

        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send("todo-topic", todo.getId(), todo);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                handleSuccess(todo.getId(), todo, result);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(todo.getId(), todo, ex);
            }

        });
    }

    private void handleSuccess(String key, Todo value, SendResult<String, Object> result) {
        System.out.println("Success: Message sent success for the key: " + key + " "
                + "and the value is " + value + " , "
                + "patition is a " + result.getRecordMetadata().partition());
    }

    private void handleFailure(String key, Todo value, Throwable ex) {
        System.out.println("Error sending the message: exception is " + ex.getMessage());
    }

}
