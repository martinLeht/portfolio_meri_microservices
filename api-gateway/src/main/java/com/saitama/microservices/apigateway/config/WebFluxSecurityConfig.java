package com.saitama.microservices.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity security) {
		security.csrf().disable()
        		.httpBasic().disable();
        return security.build();
    }
	
}
