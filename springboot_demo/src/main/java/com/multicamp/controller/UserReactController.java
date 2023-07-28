package com.multicamp.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multicamp.domain.ReactUserVO;
import com.multicamp.service.ReactUserService;

//@RequestMapping("/api") ===>주석 처리 UserReactJapController에서 사용 예정
@RestController
public class UserReactController {
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Resource(name="reactUserService")
	private ReactUserService userService;
	
	/*리액트와 연동 처리 위해 SecurityConfig에 "/api/**" 패턴에 대한 허용(permitAll())추가해야 함*/
	//@CrossOrigin("*")
	@PostMapping(value="/join", produces = "application/json")
	public ModelMap joinProcess(@RequestBody ReactUserVO user) {
		log.info("user=={}",user);
		int n=userService.createUser(user);
		String str=(n>0)?"ok":"fail";
		ModelMap m=new ModelMap();
		m.addAttribute("result", str);
		return m;
	}
	
	//https://coor.tistory.com/13
	//join과 동일하게 ModelMap반환했는데 json변환이 안돼 에러가 발생함???=>임시로 String반환
	@PostMapping(value="/login",produces="appliaction/json")
	public String loinProcess(@RequestBody ReactUserVO user) {
		log.info("user>>>{}",user);
		//userService.loginCheck(user)
		ModelMap m=new ModelMap();
		m.addAttribute("result", "ok");
		
		return "{\"result\":\"ok\"}";
	}
	
	@PostMapping(value="/logout", produces="application/json")
	public ModelMap logoutProcess() {
		
		int n=1;
		String str=(n>0)?"ok":"fail";
		ModelMap m=new ModelMap();
		m.addAttribute("result", str);
		return m;
	}

}
