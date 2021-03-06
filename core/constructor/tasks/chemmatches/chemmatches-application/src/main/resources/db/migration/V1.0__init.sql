CREATE TABLE chemistry_matching_task
(
    task_id      character varying(255) NOT NULL,
    chapter_id   integer,
    description  character varying(10000),
    reference_id integer
);

CREATE TABLE chemistry_matching_task_event
(
    chemistry_matching_task_event_id                     character varying(255) NOT NULL,
    chemistry_matching_task_event_author_id              character varying(255),
    chemistry_matching_task_event_entity_id              character varying(255),
    chemistry_matching_task_event_occurring_context      character varying(255),
    chemistry_matching_task_event_occurring_context_time timestamp without time zone,
    chemistry_matching_task_event_payload                jsonb,
    chemistry_matching_task_event_type                   character varying(255),
    version                                              character varying(255)
);

CREATE TABLE couples_for_matching
(
    couple_id    bigint generated by default as identity not null primary key,
    left_couple  character varying(255),
    right_couple character varying(255),
    task_id      character varying(255)
);

ALTER TABLE ONLY chemistry_matching_task_event
    ADD CONSTRAINT chemistry_matching_task_event_pkey PRIMARY KEY (chemistry_matching_task_event_id);

ALTER TABLE ONLY chemistry_matching_task
    ADD CONSTRAINT chemistry_matching_task_pkey PRIMARY KEY (task_id);

ALTER TABLE ONLY couples_for_matching
    ADD CONSTRAINT fkqrg9epawfu7v3uju9daokxa08 FOREIGN KEY (task_id) REFERENCES chemistry_matching_task (task_id);