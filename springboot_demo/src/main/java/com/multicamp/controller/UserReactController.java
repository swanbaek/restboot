package com.multicamp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multicamp.domain.ReactUserVO;
import com.multicamp.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserReactController {
	Logger log=LoggerFactory.getLogger(getClass());
	private final UserService userService;
	
	/*리액트와 연동 처리 위해 SecurityConfig에 "/api/**" 패턴에 대한 허용(permitAll())추가해야 함*/
	//@CrossOrigin("*")
	@PostMapping(value="/join", produces = "application/json")
	public ModelMap joinProcess(@RequestBody ReactUserVO user) {
		log.info("user=={}",user);
		ModelMap m=new ModelMap();
		m.addAttribute("result", "ok");
		return m;
	}

}
