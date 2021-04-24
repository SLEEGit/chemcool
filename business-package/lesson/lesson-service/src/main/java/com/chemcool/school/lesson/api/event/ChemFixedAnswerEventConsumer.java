package com.chemcool.school.lesson.api.event;

import com.chemcool.school.lesson.tasks.chemfixedanswer.domain.ChemFixedAnswerTaskEvent;
import com.chemcool.school.lesson.tasks.chemfixedanswer.service.ChemFixedAnswerTaskEventService;
import com.chemcool.school.lesson.tasks.chemfixedanswer.service.ChemFixedAnswerTaskService;
import com.chemcool.school.lesson.tasks.chemsingleselect.domain.ChemSingleSelectTaskEvent;
import com.chemcool.school.lesson.tasks.chemsingleselect.service.ChemSingleSelectTaskEventService;
import com.chemcool.school.lesson.tasks.chemsingleselect.service.ChemSingleSelectTaskService;
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
public class ChemFixedAnswerEventConsumer {
    private final ChemFixedAnswerTaskEventService eventService;
    private final ChemFixedAnswerTaskService taskService;

    @KafkaListener(topics = "fixed-answer", containerFactory = "chemFixedAnswerKafkaListenerContainerFactory")
    @KafkaHandler
    public void handleChemSingleSelectTask(ConsumerRecord<String, ChemFixedAnswerTaskEvent> record) {
        ChemFixedAnswerTaskEvent event = record.value();
        log.info("Пойман журнал для логирования: " + event.getEventId());
        eventService.handleEvent(event);
        taskService.save(event.getEventPayload());
    }
}
