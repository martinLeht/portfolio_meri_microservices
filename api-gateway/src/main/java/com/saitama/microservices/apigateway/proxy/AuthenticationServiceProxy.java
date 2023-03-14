package com.saitama.microservices.apigateway.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.saitama.microservices.commonlib.dto.TemporaryAccessGrantDTO;
import com.saitama.microservices.commonlib.dto.UserDTO;

@FeignClient(name = "authentication-service", url = "http://authentication-service:8083")
public interface AuthenticationServiceProxy {
	
	@GetMapping("/auth/user/{id}")
	UserDTO getUserByUuid(@PathVariable String uuid);
	
	@GetMapping("/access/temp/key/authenticate")
	TemporaryAccessGrantDTO authenticateTemporaryAccessKey(@PathVariable String accessKey);

}
