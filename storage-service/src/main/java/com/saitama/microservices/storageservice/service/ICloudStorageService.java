package com.saitama.microservices.storageservice.service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.saitama.microservices.storageservice.constant.MediaCategory;

public interface ICloudStorageService {
	
	public URL uploadFile(MediaCategory mediaCategory, MultipartFile file);
	
	public Optional<URL> getFileByName(MediaCategory mediaCategory, String name);
	
	public Map<String, URL> getFilesByNames(MediaCategory mediaCategory, List<String> names);
	
	public boolean deleteFile(MediaCategory mediaCategory, String fileName);
	
	public List<Boolean> deleteFiles(MediaCategory mediaCategory, List<String> fileNames);
	
}
