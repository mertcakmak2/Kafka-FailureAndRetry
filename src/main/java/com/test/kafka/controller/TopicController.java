package com.test.kafka.controller;

import com.test.kafka.model.Todo;
import com.test.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TopicController {

    private final KafkaProducer kafkaProducer;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Todo createTodo() {
        var todo = Todo.builder()
                .id(UUID.randomUUID().toString())
                .todo("todo description")
                .build();
        kafkaProducer.sendTodoToKafka(todo);
        return todo;
    }

}
