package com.saitama.microservices.authenticationservice.task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.saitama.microservices.authenticationservice.dto.MessageDTO;
import com.saitama.microservices.authenticationservice.service.IEmailService;
import com.saitama.microservices.commonlib.util.FileUtils;

@Component
public class EmailVerificationTask {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailVerificationTask.class);
	private static final String EMAIL_TEMPLATE = "templates/email_user_verification_template.html";
	
	private final IEmailService emailService;
	
	@Value("${user.verification.url}")
	private String userVerificationUrl;
	
	
	@Autowired
	public EmailVerificationTask(IEmailService emailService) {
		this.emailService = emailService;
	}
	
	@Async
	public void execute(String toEmail, String callbackUrl, UUID userUuid, UUID verificationToken) {
		LOG.info("Performing email verification task for user - " + userUuid);
		
		try {
			String emailTemplateHtml = FileUtils.readFileAsString(EMAIL_TEMPLATE, getClass().getClassLoader());
			emailTemplateHtml = emailTemplateHtml.replace("#TEXT_TOP#", "Verify temporary user access by clicking the link below: ");
			emailTemplateHtml = emailTemplateHtml.replace("#TEXT_BOTTOM#", 
					"\n\nAfter successful verification, you will be authorized to leave comments on the blog section."
					+ "\nNOTE: Verified access will expire after 10 days of inactivity in the platform. "
					+ "After expiration you will need to verify the access again.");
			emailTemplateHtml = emailTemplateHtml.replace("#VERIFICATION_URL#", userVerificationUrl);
			emailTemplateHtml = emailTemplateHtml.replace("#VERIFICATION_TOKEN#", verificationToken.toString());
			emailTemplateHtml = emailTemplateHtml.replace("#USER_ID#", userUuid.toString());
			emailTemplateHtml = emailTemplateHtml.replace("#CALLBACK_URL#", callbackUrl);
			
			MessageDTO verificationMessageDto = MessageDTO.builder()
					.to(toEmail)
					.subject("Temporary user verification - www.merijohanna.com")
					.content(emailTemplateHtml)
					.createdAt(LocalDateTime.now())
					.build();
			emailService.sendMail(verificationMessageDto);
			
		} catch (IOException e) {
			LOG.error("Failed to send user verification email for temporary user id: {}", userUuid);
			LOG.error(e.getLocalizedMessage());
		}
		
		LOG.info("Task completed!");
	}

}
