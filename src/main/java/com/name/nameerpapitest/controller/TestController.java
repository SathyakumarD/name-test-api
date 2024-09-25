package com.name.nameerpapitest.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {
	
	@GetMapping("/test")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String getHelloName() {
		return "Not Another Moderate ERP";
	}
	
	@GetMapping("/admintest")
	@Secured("ROLE_ADMIN")
	public String getHelloNameAdmin() {
		return "ADMIN of Not Another Moderate ERP";
	}

}
