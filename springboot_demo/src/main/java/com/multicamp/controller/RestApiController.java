package com.multicamp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
public class RestApiController {
	@ApiOperation(value = "board springboot react test", produces = "application/json")
	@GetMapping(value="/test",  produces = "application/json")
	public Map<String, Object> test() {
		Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("msg", "Welcome to SpringBoot & React App");
        
        return res;
	}

}
