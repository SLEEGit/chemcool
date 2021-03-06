package com.chemcool.school.tasks.chemfixedanswer.service;

import com.chemcool.school.tasks.chemfixedanswer.domain.ChemFixedAnswerTaskEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaChemFixedAnswerTaskEventNotificationService implements ChemFixedAnswerTaskEventNotificationService{

    private final KafkaTemplate<String, ChemFixedAnswerTaskEvent> kafkaTemplate;

    @Override
    public void send(ChemFixedAnswerTaskEvent event) {
        ListenableFuture<SendResult<String, ChemFixedAnswerTaskEvent>> future = kafkaTemplate.send("fixed-answer", UUID.randomUUID().toString(), event);
        future.addCallback(System.out::println, System.out::println);
        kafkaTemplate.flush();
    }
}
