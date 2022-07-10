package com.saitama.microservices.blogservice.proxy;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.saitama.microservices.blogservice.dto.ImageInfoDto;


@FeignClient(name = "storage-service")
public interface StorageServiceProxy {
	
	@GetMapping("/storage/get")
    public ImageInfoDto getFileUrl(@RequestParam String fileName);
	
	@PostMapping("/storage/get/files")
    public Map<String, ImageInfoDto> getFilesUrls(@RequestBody List<String> fileNames);
	
	@PostMapping("/storage/delete")
	public void deleteFile(String fileName);
	
	@PostMapping("/storage/delete/files")
	public void deleteFiles(@RequestBody List<String> fileNames);


}
