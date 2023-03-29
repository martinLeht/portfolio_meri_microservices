package com.saitama.microservices.blogservice.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.saitama.microservices.commonlib.dto.MediaFileDTO;
import com.saitama.microservices.commonlib.dto.MediaListQueryDTO;
import com.saitama.microservices.commonlib.dto.MediaQueryDTO;


@FeignClient(name = "storage-service")
public interface StorageServiceClient {
	
	@PostMapping("/storage/get")
    public MediaFileDTO getFileUrl(@RequestBody MediaQueryDTO mediaQueryDto);
	
	@PostMapping("/storage/get/files")
    public Map<String, MediaFileDTO> getFilesUrls(@RequestBody MediaListQueryDTO mediaListQueryDto);
	
	@PostMapping("/storage/delete")
	public void deleteFile(@RequestBody MediaQueryDTO mediaQueryDto);
	
	@PostMapping("/storage/delete/files")
	public void deleteFiles(@RequestBody MediaListQueryDTO mediaListQueryDto);


}
