package com.saitama.microservices.blogservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "image-service")
public interface StorageServiceProxy {
	
	@PostMapping("/storage/delete")
	public void deleteFile(String fileName);
	
	@PostMapping("/storage/delete/files")
	public void deleteFiles(@RequestBody List<String> fileNames);


}
