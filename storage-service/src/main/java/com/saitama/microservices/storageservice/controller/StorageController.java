package com.saitama.microservices.storageservice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saitama.microservices.commonlib.constant.MediaType;
import com.saitama.microservices.commonlib.dto.MediaFileDTO;
import com.saitama.microservices.storageservice.constant.MediaCategory;
import com.saitama.microservices.storageservice.dto.MediaListQueryDTO;
import com.saitama.microservices.storageservice.dto.MediaQueryDTO;
import com.saitama.microservices.storageservice.service.ICloudStorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {
	
    
    private ICloudStorageService cloudStorageService;

    @Autowired
	public StorageController(ICloudStorageService cloudStorageService) {
		this.cloudStorageService = cloudStorageService;
	}

	@PostMapping("/upload")
    public MediaFileDTO uploadFile(@RequestPart MediaCategory mediaCategory, @RequestPart MultipartFile file) throws IOException {		
        URL url = cloudStorageService.uploadFile(mediaCategory, file);
        return new MediaFileDTO(file.getOriginalFilename(), url.toString(), MediaType.IMAGE);
    }
	
	@PostMapping("/get")
    public MediaFileDTO getFileUrl(@RequestBody @Valid MediaQueryDTO mediaQueryDto) {		
        Optional<URL> urlOpt = cloudStorageService.getFileByName(mediaQueryDto.getMediaCategory(), mediaQueryDto.getFileName());
        if (urlOpt.isPresent()) {
        	 return new MediaFileDTO(mediaQueryDto.getFileName(), urlOpt.get().toString(), MediaType.IMAGE);
        }
        return new MediaFileDTO(null, null, null);
        
    }
	
	@PostMapping("/get/files")
    public Map<String, MediaFileDTO> getFilesUrls(@RequestBody @Valid MediaListQueryDTO mediaListQueryDto) {		
        Map<String, URL> urls = cloudStorageService.getFilesByNames(mediaListQueryDto.getMediaCategory(), mediaListQueryDto.getFileNames());
        if (urls.size() > 0) {
        	Map<String, MediaFileDTO> mediaFileDtos = mediaListQueryDto.getFileNames().stream()
        			.map(name -> new MediaFileDTO(name, urls.get(name).toString(), MediaType.IMAGE))
        			.collect(Collectors.toMap(MediaFileDTO::getName, Function.identity()));
    		return mediaFileDtos;
        }
        return Collections.emptyMap();
        
    }
	
	@PostMapping("/delete")
	public String deleteFile(@RequestBody @Valid MediaQueryDTO mediaQueryDto) {
		boolean success = cloudStorageService.deleteFile(mediaQueryDto.getMediaCategory(), mediaQueryDto.getFileName());
		return success ? mediaQueryDto.getFileName() : null;
	}
	
	@PostMapping("/delete/files")
	public void deleteFiles(@RequestBody @Valid MediaListQueryDTO mediaListQueryDto) {
		cloudStorageService.deleteFiles(mediaListQueryDto.getMediaCategory(), mediaListQueryDto.getFileNames());
	}


}
