package com.saitama.microservices.authenticationservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "email-service")
public interface EmailServiceProxy {

}
