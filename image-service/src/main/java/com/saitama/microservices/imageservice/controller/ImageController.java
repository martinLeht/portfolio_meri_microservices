package com.saitama.microservices.imageservice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saitama.microservices.imageservice.dto.ImageInfoDto;
import com.saitama.microservices.imageservice.service.IGoogleCloudStorageService;

@RestController
@RequestMapping("/storage")
public class ImageController {
	
    @Autowired
    private IGoogleCloudStorageService cloudStorageService;

	
	@PostMapping("/upload")
    public ImageInfoDto uploadFile(@RequestPart("file") MultipartFile file) throws IOException {		
        URL url = cloudStorageService.uploadFile(file);
        ImageInfoDto imageInfo = new ImageInfoDto(file.getOriginalFilename(), url.toString());
        return imageInfo;
    }
	
	@PostMapping("/delete")
	public void deleteFile(String fileName) {
		cloudStorageService.deleteFile(fileName);
	}
	
	@PostMapping("/delete/files")
	public void deleteFiles(@RequestBody List<String> fileNames) {
		cloudStorageService.deleteFiles(fileNames);
	}


}
