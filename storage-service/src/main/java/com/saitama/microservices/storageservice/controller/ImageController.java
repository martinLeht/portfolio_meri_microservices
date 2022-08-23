package com.saitama.microservices.storageservice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saitama.microservices.commonlib.constant.MediaType;
import com.saitama.microservices.commonlib.dto.MediaFileDTO;
import com.saitama.microservices.storageservice.service.IGoogleCloudStorageService;

@RestController
@RequestMapping("/storage")
public class ImageController {
	
    
    private IGoogleCloudStorageService cloudStorageService;

    @Autowired
	public ImageController(IGoogleCloudStorageService cloudStorageService) {
		this.cloudStorageService = cloudStorageService;
	}

	@PostMapping("/upload")
    public MediaFileDTO uploadFile(@RequestPart("file") MultipartFile file) throws IOException {		
        URL url = cloudStorageService.uploadFile(file);
        return new MediaFileDTO(file.getOriginalFilename(), url.toString(), MediaType.IMAGE);
    }
	
	@GetMapping("/get")
    public MediaFileDTO getFileUrl(@RequestParam String fileName) throws IOException {		
        Optional<URL> urlOpt = cloudStorageService.getFileByName(fileName);
        if (urlOpt.isPresent()) {
        	 return new MediaFileDTO(fileName, urlOpt.get().toString(), MediaType.IMAGE);
        }
        return new MediaFileDTO(null, null, null);
        
    }
	
	@PostMapping("/get/files")
    public Map<String, MediaFileDTO> getFilesUrls(@RequestBody List<String> fileNames) throws IOException {		
        Map<String, URL> urls = cloudStorageService.getFilesByNames(fileNames);
        if (urls.size() > 0) {
        	Map<String, MediaFileDTO> mediaFileDtos = fileNames.stream()
        			.map(name -> new MediaFileDTO(name, urls.get(name).toString(), MediaType.IMAGE))
        			.collect(Collectors.toMap(MediaFileDTO::getName, Function.identity()));
    		return mediaFileDtos;
        }
        return Collections.emptyMap();
        
    }
	
	@DeleteMapping("/delete")
	public String deleteFile(@RequestParam String fileName) {
		boolean success = cloudStorageService.deleteFile(fileName);
		return success ? fileName : null;
	}
	
	@PostMapping("/delete/files")
	public void deleteFiles(@RequestBody List<String> fileNames) {
		cloudStorageService.deleteFiles(fileNames);
	}


}
