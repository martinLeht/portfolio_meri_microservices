package com.saitama.microservices.commonlib.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;


@Component
public class KafkaMessagingClient {

	@Autowired
    private StreamBridge streamBridge;

	
    public <T> void sendMessageToChannel(EventType eventType, T msg){
        streamBridge.send(eventType.getSupplierBinding(), msg);
    }
}
