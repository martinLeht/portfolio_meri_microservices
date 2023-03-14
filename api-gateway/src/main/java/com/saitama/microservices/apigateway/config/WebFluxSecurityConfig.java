package com.saitama.microservices.apigateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.saitama.microservices.apigateway.security.KeycloakReactiveTokenIntrospector;

import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {
	
	@Bean
    public ReactiveOpaqueTokenIntrospector keycloakIntrospector(OAuth2ResourceServerProperties props) {
        NimbusReactiveOpaqueTokenIntrospector delegate = new NimbusReactiveOpaqueTokenIntrospector(
           props.getOpaquetoken().getIntrospectionUri(),
           props.getOpaquetoken().getClientId(),
           props.getOpaquetoken().getClientSecret());
        
        return new KeycloakReactiveTokenIntrospector(delegate);
    }
	
	/*
	@Bean
	public ReactiveJwtDecoder jwtDecoder() {
	    return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
	}
	*/
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges
				/* Authentication service */
				.pathMatchers(HttpMethod.POST, "/access/temp/logout").authenticated()
				/* Blog service */
				.pathMatchers(HttpMethod.POST, "/blog/").authenticated()
				.pathMatchers(HttpMethod.PUT, "/blog/**").authenticated()
				.pathMatchers(HttpMethod.DELETE, "/blog/**").authenticated()
				/* Portfolio data service */
				.pathMatchers(HttpMethod.GET, "/portfolio/experience").authenticated()
				.pathMatchers(HttpMethod.POST, "/portfolio/experience/").authenticated()
				.pathMatchers(HttpMethod.PUT, "/portfolio/experience/**").authenticated()
				.pathMatchers(HttpMethod.DELETE, "/portfolio/experience/**").authenticated()
				/* Comment service */
				.pathMatchers(HttpMethod.POST, "/comment/").authenticated()
				.pathMatchers(HttpMethod.POST, "/comment/*/thread").authenticated()
				.pathMatchers(HttpMethod.PUT, "/comment/**").authenticated()
				.pathMatchers(HttpMethod.DELETE, "/comment/**").authenticated()
				/* Storage service */
				.pathMatchers(HttpMethod.POST, "/storage/upload").authenticated()
				.pathMatchers(HttpMethod.POST, "/storage/delete").authenticated()
				.pathMatchers(HttpMethod.POST, "/storage/delete/files").authenticated()
				.pathMatchers(HttpMethod.POST, "/storage/get").authenticated()
				.pathMatchers(HttpMethod.POST, "/storage/get/files").authenticated()
				.anyExchange().permitAll()
			)
			.oauth2ResourceServer(OAuth2ResourceServerSpec::jwt)
			.csrf().disable()
			.cors()
			.and()
        	.httpBasic().disable();
        return http.build();
    }
	
}
