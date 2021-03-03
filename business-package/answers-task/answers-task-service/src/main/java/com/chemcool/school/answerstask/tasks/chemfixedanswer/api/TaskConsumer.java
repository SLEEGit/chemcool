package com.chemcool.school.answerstask.tasks.chemfixedanswer.api;

import com.chemcool.school.answerstask.tasks.chemfixedanswer.domain.ChemFixedAnswerTaskEvent;
import com.chemcool.school.answerstask.tasks.chemfixedanswer.service.ChemFixedAnswerTaskEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Service
@EnableKafka
@EnableTransactionManagement
@RequiredArgsConstructor
public class TaskConsumer {

    private final ChemFixedAnswerTaskEventService eventService;

    @KafkaListener(topics = "fixed-answer-task")
    @KafkaHandler
    public void handleChemFixedAnswerTask(ConsumerRecord<String, ChemFixedAnswerTaskEvent> record) {
        ChemFixedAnswerTaskEvent event = record.value();
        log.info("Пойман журнал для логирования с ID: " + event.getEventId());
        eventService.handleEvent(event);
    }
}
