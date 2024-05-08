package com.multicamp.controller;

import java.time.Duration;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multicamp.domain.ReactUserVO;
import com.multicamp.domain.RefreshToken;
import com.multicamp.domain.ResponseVO;
import com.multicamp.domain.UserEntity;
import com.multicamp.dto.ReactUserResponseDTO;
import com.multicamp.service.RefreshTokenService;
import com.multicamp.service.TokenProvider;
import com.multicamp.service.UserJpaService;
//참고: https://github.com/urstoryp/fakeshopapi/tree/step04,
//https://www.youtube.com/watch?v=hDoa7Zw1r6c
//https://jwt.io/#debugger-io  ==> token값을 encode할 수 있는 사이트. 토큰에 대한 정보가 출력됨
@RestController
@RequestMapping("/api")
public class UserReactJpaController {
	Logger logger=LoggerFactory.getLogger(getClass());
	@Inject
	private UserJpaService userService;
	
	@Inject
	private RefreshTokenService refreshTokenService;
	
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
	//참고: https://github.com/urstoryp/fakeshopapi/tree/step04
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody ReactUserVO userVo){
		logger.info("userVo={}",userVo);
		UserEntity user=userService.getByCredentials(userVo.getNickname(), userVo.getPwd());
		logger.info("userEntity user={}",user);
		if(user!=null) {
			///토큰 발급//////////////////////////
			//final String token=tokenProvider.create(user);//토큰 만료일 1일(디폴트 1일로 설정함)
			final String token=tokenProvider.createToken(user, Duration.ofHours(1));//토큰 만료 1시간 뒤로 설정
			
			//refresh token도 발급
			final String refreshToken=tokenProvider.createRefreshToken(user, Duration.ofDays(7));//refresh토큰 만료 7일 뒤로 설정			
			////////////////////////////////
			//refreshToken db에 저장(일련번호, 회원idx, 토큰값)
			RefreshToken rEntity=new RefreshToken(user.getIdx(), refreshToken);
			refreshTokenService.addRefreshToken(rEntity);
			logger.info("refreshToken테이블에 저장");
			/*
			final ReactUserVO resVo=ReactUserVO.builder() //==>이걸 사용하면 사용자정보 name,pwd 등도 null로 응답데이터에 포함됨.
					.nickname(user.getNickname())
					.idx(user.getIdx())
					.token(token)
					.refreshToken(refreshToken)
					.build();*/
			final ReactUserResponseDTO resVo=ReactUserResponseDTO.builder()
					.accessToken(token)
					.refreshToken(refreshToken)
					.userIdx(user.getIdx())
					.nickname(user.getNickname())
					.build();
			return ResponseEntity.ok().body(resVo);
		}
		
		ResponseVO resErrVo=ResponseVO.builder()
				.error("Login Failed")
				.build();
		return ResponseEntity.badRequest().body(resErrVo);
	}
	//리액트에서 로그아웃 처리는 localStorage에서 access token을 삭제하는 것으로만 처리하고 별도로 백엔드 요청을 보내지는 않았음
	//but 만약 백엔드로 요청을 보낸다면 아래와 같이 처리하면 될 듯
	 @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		 	//SecurityContextHolder에서 등록된 인증받은 객체를 로그아웃 핸들러로 로그아웃 처리
		 	//logout() 메서드를 호출함으로써, 세션에서 사용자의 인증 정보를 제거하고, 필요한 경우 세션을 무효화하거나 새로운 세션을 생성할 수 있다.
	        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	        ResponseVO resVo=ResponseVO.builder().data(Arrays.asList("Logout Success")).build();
	        return ResponseEntity.ok(resVo);
    }
}/////////////////////////////////////
