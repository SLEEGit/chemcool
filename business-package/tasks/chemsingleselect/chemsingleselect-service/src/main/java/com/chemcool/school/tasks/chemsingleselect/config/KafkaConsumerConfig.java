package com.chemcool.school.tasks.chemsingleselect.config;

import com.chemcool.school.tasks.chemsingleselect.configuration.properties.ChemSingleSelectTaskDeserializer;
import com.chemcool.school.tasks.chemsingleselect.configuration.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    public static final String TRUSTED_PACKAGES = "com.chemcool.school.tasks.chemsingleselect.domain";

    private final KafkaProperties kafkaProperties;

    public Map<String, Object> consumerConfig() {
        Map<String, Object> prop = new HashMap<>();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServer());
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ChemSingleSelectTaskDeserializer.class);
        prop.put(JsonDeserializer.TRUSTED_PACKAGES, TRUSTED_PACKAGES);
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        return prop;
    }

    @Bean
    public KafkaListenerContainerFactory kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

}
