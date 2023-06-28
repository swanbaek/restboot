package com.multicamp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/index")
	public String home() {
		return "home";
	}
	@GetMapping("/login/result")
	public void loginResult() {
		
	}
	@GetMapping("/user/userMain")
	public String showUserMain() {
		return "memberMain";
	}
	@GetMapping("/admin/adminMain")
	public String showAdminMain() {
		return "usermanagerMain";
	}
	@GetMapping("/accessDenied")
	public String showDeny() {
		return "security/accessDenied";
	}
}