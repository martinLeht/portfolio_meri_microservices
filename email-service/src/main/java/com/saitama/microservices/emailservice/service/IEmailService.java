package com.saitama.microservices.emailservice.service;

import com.saitama.microservices.emailservice.dto.MessageDTO;

public interface IEmailService {
	
	boolean sendMail(MessageDTO message);

}
