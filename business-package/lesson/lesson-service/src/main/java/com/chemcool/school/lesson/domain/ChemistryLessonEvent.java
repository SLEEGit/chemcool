package com.chemcool.school.lesson.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "chemistry_lesson_event")
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs(
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
)

public class ChemistryLessonEvent {
    @Id
    @Column(name = "event_id")
    private String eventId;

    @Column(name = "event_author")
    private String eventAuthor;

    @Column(name = "event_occurring_context")
    private String eventOccurringContext;

    @Column(name = "event_occurring_context_time")
    private LocalDateTime eventOccurringContextTime;

    @Column(name = "event_type")
    private ChemistryLessonEventType eventType;

    @Column(name = "version")
    private String version;

    @Column(name = "event_entity_id")
    private String EventEntityId;


    @Type(type = "jsonb")
    @Column(name = "event_payload", columnDefinition = "jsonb")
    private ChemistryLesson eventPayload;

    public static ChemistryLessonEvent createEvent(ChemistryLesson lesson,ChemistryLessonEventType type){
        return new ChemistryLessonEvent(
                UUID.randomUUID().toString(),
                "123",
                "ChemistryLessonEvent",
                LocalDateTime.now(),
                type,
                "1.0",
                lesson.getLessonId(),
                lesson
        );
    }

}
