package com.kafkatut.kafkaconfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Value("${bootstrap.server}")
    String bootstrapServer;

    @Autowired
    KafkaAdmin kafkaAdmin;
    @Bean
    public ProducerFactory<String, String> producerFactoryString() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplateString() {
        logger.info("creating bean for kafka Template");
        return new KafkaTemplate<>(producerFactoryString());
    }

    @Bean("topicSpringBoot") // by default kafka allowed to create new topic when topic not found but for production should not be done
    public NewTopic topic(){
        /* kafkaAdmin is responsible for creating a new topic

          1) for production don not allow a programmer to create a topic always create a by kafka admin
          2) partiton is used to parralism so depend upon use case and server config
          3) replica of a topic should be by use case
         */
       NewTopic topic =  TopicBuilder.name("topic-spring-boot-sasas")
               .partitions(4).build();
       return topic;
    }


}
