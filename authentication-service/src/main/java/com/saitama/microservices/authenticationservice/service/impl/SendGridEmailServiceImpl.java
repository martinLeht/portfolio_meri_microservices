package com.saitama.microservices.authenticationservice.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.dto.MessageDTO;
import com.saitama.microservices.authenticationservice.service.IEmailService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class SendGridEmailServiceImpl implements IEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SendGridEmailServiceImpl.class);
			
	@Value("${email.address.from}")
	private String emailFrom;
	
	@Value("${email.sendgrid.api.key}")
	private String sendGridKey;
	
	
	@Override
	public boolean sendMail(MessageDTO message) {
		Mail mail = createMailFromMessageDTO(message);
		
		Request request = new Request();
		LOG.info("Sending email with SendGrid API...");
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			SendGrid sendGridClient = new SendGrid(sendGridKey);
			Response response = sendGridClient.api(request);
			LOG.info("Email sent:");
			LOG.info("{}", response.getStatusCode());
			LOG.info("{}", response.getBody());
			LOG.info("{}", response.getHeaders());
			return true;
		} catch (IOException ex) {
			LOG.info("Email sending failed");
			LOG.info(ex.getMessage());
		}
		return false;
	}
	
	private Mail createMailFromMessageDTO(MessageDTO message) {
		Email from = new Email(emailFrom);
		Email to = new Email(message.getTo());
		Content content;
		if (message.getContent().startsWith("<!DOCTYPE html>")) {
			content = new Content("text/html", message.getContent());
		} else {
			content = new Content("text/plain", message.getContent());
		}
		return new Mail(from, message.getSubject(), to, content);
	}

}
