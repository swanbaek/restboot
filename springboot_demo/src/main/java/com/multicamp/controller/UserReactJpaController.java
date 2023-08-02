package com.multicamp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multicamp.domain.ReactUserVO;
import com.multicamp.domain.ResponseVO;
import com.multicamp.domain.UserEntity;
import com.multicamp.service.TokenProvider;
import com.multicamp.service.UserJpaService;

@RestController
@RequestMapping("/api")
public class UserReactJpaController {
	Logger logger=LoggerFactory.getLogger(getClass());
	@Inject
	private UserJpaService userService;
	
	@Inject
	private TokenProvider tokenProvider;
	
	@PostMapping(value="/join")
	public ResponseEntity<?> joinProcess(@RequestBody ReactUserVO  userVo){
		
		logger.info("userVo=={}",userVo);
		try {
			if(userVo==null||userVo.getName()==null) {
				throw new RuntimeException("Invalid Name Value!!-null value");
			}
			if(userVo==null||userVo.getPwd()==null) {
				throw new RuntimeException("Invalid Password Value!!-null value");
			}
			
			//요청을 이용해 저장할 유저 만들기
			UserEntity user=UserEntity.builder()
					.name(userVo.getName())
					.nickname(userVo.getNickname())
					.pwd(userVo.getPwd())
					.build();
			
			//서비스를 이용해 리포지터리에 유저 저장
			UserEntity registeredUser=userService.create(user);
			ReactUserVO responseUserVo=ReactUserVO.builder()
					.idx(registeredUser.getIdx())
					.name(registeredUser.getName())
					.nickname(registeredUser.getNickname())
					.build();
			
			return ResponseEntity.ok().body(responseUserVo);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			ResponseVO resVo=ResponseVO.builder().error(e.getMessage()).build();
			logger.error("error={} ",e.getMessage());
			return ResponseEntity.badRequest().body(resVo);
		}
	}//----------------------------------
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody ReactUserVO userVo){
		logger.info("userVo={}",userVo);
		UserEntity user=userService.getByCredentials(userVo.getNickname(), userVo.getPwd());
		if(user!=null) {
			/////////////////////////////
			final String token=tokenProvider.create(user);
			///////////////////////////////
			final ReactUserVO resVo=ReactUserVO.builder()
					.nickname(user.getNickname())
					.idx(user.getIdx())
					.token(token)
					.build();
			return ResponseEntity.ok().body(resVo);
		}
		
		ResponseVO resErrVo=ResponseVO.builder()
				.error("Login Failed")
				.build();
		return ResponseEntity.badRequest().body(resErrVo);
	}
	

}
