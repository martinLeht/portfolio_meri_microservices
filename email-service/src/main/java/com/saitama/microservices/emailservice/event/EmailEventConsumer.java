package com.saitama.microservices.emailservice.event;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.saitama.microservices.emailservice.dto.MessageDTO;
import com.saitama.microservices.emailservice.service.IEmailService;

@Component
public class EmailEventConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailEventConsumer.class);
	
	private final IEmailService emailService;
	
	@Autowired
	public EmailEventConsumer(IEmailService emailService) {
		this.emailService = emailService;
	}
	
	@Bean
	public Consumer<Message<MessageDTO>> emailSendConsumer() {
		return (msg) -> {
			LOG.info("Recieved new message to emailSendConsumer");
			MessageDTO messageDto = msg.getPayload();
	        LOG.info("MessageDTO: {}", messageDto.toString());
	        emailService.sendMail(messageDto);
		};
	}

}
