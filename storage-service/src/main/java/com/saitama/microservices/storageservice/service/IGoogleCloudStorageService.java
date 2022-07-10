package com.saitama.microservices.storageservice.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface IGoogleCloudStorageService {
	
	public URL uploadFile(MultipartFile file) throws IOException;
	
	public Optional<URL> getFileByName(String name) throws IOException;
	
	public Map<String, URL> getFilesByNames(List<String> names);
	
	public boolean deleteFile(String fileName);
	
	public List<Boolean> deleteFiles(List<String> fileNames);
	
}
