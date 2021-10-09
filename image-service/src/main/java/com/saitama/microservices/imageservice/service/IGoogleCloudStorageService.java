package com.saitama.microservices.imageservice.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IGoogleCloudStorageService {
	
	public URL uploadFile(MultipartFile file) throws IOException;
	
	public boolean deleteFile(String fileName);
	
	public List<Boolean> deleteFiles(List<String> fileNames);
	
}
