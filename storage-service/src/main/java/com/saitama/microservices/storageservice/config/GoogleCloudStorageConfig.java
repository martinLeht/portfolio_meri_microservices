package com.saitama.microservices.storageservice.config;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.spring.core.GcpProjectIdProvider;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class GoogleCloudStorageConfig {

	@Autowired
	private CredentialsProvider credentialsProvider;

	@Autowired
	private GcpProjectIdProvider projectIdProvider;

	@Bean
	public Storage configStorageClient() throws GeneralSecurityException, IOException {
		Storage storage = StorageOptions.newBuilder()
				.setCredentials(credentialsProvider.getCredentials())
				.setProjectId(projectIdProvider.getProjectId())
				.build().getService();
		return storage;
	}

}
