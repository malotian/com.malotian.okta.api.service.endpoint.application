package com.malotian.okta.api.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MicroServiceController {

	@Value("${appname}")
	private String appname;

	@Value("${appname}-everyone")
	private String everyone;
	
	@Value("${appname}-admin")
	private String admin;
	
	public String everyone() {
		return everyone;
	}
	
	public String admin() {
		return admin;
	}

	@GetMapping(path = "/app-name")
	public String appname() {
		return appname;
	}

	@GetMapping(path = "/user-service", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority(#this.this.everyone()) && #oauth2.hasScope('email')")
	public Map<String, String> user(Principal principal, HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		result.put("user", principal.getName());
		result.put("api", UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString());
		result.put("api-result", "SUCCESS");
		return result;
	}

	@GetMapping(path = "/admin-service", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority(#this.this.admin()) && #oauth2.hasScope('email')")
	public Map<String, String> admin(Principal principal, HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		result.put("user", principal.getName());
		result.put("api", UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString());
		result.put("api-result", "SUCCESS");
		return result;
	}
}