package com.multicamp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multicamp.domain.UserVO;
import com.multicamp.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Inject
	private UserService userService;
	
	@GetMapping("/login")
	public String loginForm() {
		
		return "login/login";
	}

	@GetMapping("/signup")
	public String joinForm() {
		
		return "member/join";
	}
	@PostMapping("/signup")
	public String joinProc(@ModelAttribute UserVO user) {
		log.info("user={}",user);
		int n=userService.createUser(user);
		log.info("n={}",n);
		return "redirect:/login/result";
	}
	
	@GetMapping("/idCheck")
	public String idCheckForm() {
		
		return "member/idCheck";
	}
	@PostMapping("/idCheck")
	public String idCheckResult(Model m, @RequestParam(defaultValue="") String userid) {
		boolean isUse=userService.idCheck(userid);
		String result=(isUse)? "success":"fail";
		
		m.addAttribute("result", result);
		m.addAttribute("userid",userid);
		
		return "member/idCheckResult";
	}
}
