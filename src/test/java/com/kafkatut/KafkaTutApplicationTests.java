package com.kafkatut;

import com.kafkatut.kafkaservice.KafkaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaTutApplicationTests {

	@Autowired
	KafkaService kafkaService;
	@Test
	void contextLoads() {
		String topic = "quickstart-events-aditya";
		for (int i=0;i<1000;i++){
			kafkaService.sendToBroker("event="+i);
		}
	}

}
