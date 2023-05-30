package com.kafkatut.controller;


import com.kafkatut.kafkaservice.KafkaService;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WelcomeController {

    @Autowired
    @Qualifier("topicSpringBoot")
    NewTopic topic;

    @Autowired
    KafkaService kafkaService;
    @GetMapping
    public void welcome(@RequestParam("message") String message ){
        // in this we are not sending any key for the value so by default it partition is decided by round-robin mechanism
        kafkaService.sendToBroker(message);
    }


}
