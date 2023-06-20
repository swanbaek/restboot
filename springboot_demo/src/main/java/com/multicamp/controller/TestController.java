package com.multicamp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController	//전역 ResponseBody
@RequestMapping("/api")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @ApiOperation(value="react와 springboot연동", notes="reactTest", produces = "application/json")
    @GetMapping(value="/reactTest", produces = "application/json")
    public Map<String, Object> testHandler() {
        logger.info("TEST RestAPI / Test 핸들러 실행");

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("msg", "Hello SpringBoot & React");

        return res;
    }
}
