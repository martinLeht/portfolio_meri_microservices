package com.saitama.microservices.commentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "authentication-service")
public interface AuthenticationServiceProxy {
	
	

}
