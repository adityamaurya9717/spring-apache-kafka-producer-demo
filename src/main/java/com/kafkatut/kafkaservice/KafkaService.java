package com.kafkatut.kafkaservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

@Service
public class KafkaService {

    Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate; // use to send events in broker so that consumer consume it

    @Autowired
    KafkaAdmin kafkaAdmin;

    private final String topicName = "quickstart-events-aditya";

    public void sendToBroker(String message){

        kafkaTemplate.send(topicName,message)
                .addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                     logger.error("KafkaService::sendToBroker failed to send message to topicname={} and message= {}",topicName,message);
                    }
                    @Override
                    public void onSuccess(SendResult<String, String> result) {

                        logger.info("KafkaService::sendToBroker successfully send messag to topicname={} and message= {}",topicName,message);
                    }
                });



    }
}
