package com.saitama.microservices.authenticationservice.service;

import com.saitama.microservices.authenticationservice.dto.MessageDTO;

public interface IEmailService {
	
	boolean sendMail(MessageDTO message);

}
