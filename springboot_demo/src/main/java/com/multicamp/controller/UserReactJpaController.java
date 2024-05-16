package com.multicamp.controller;

import java.time.Duration;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.multicamp.cmm.exception.InvalidRefreshTokenException;
import com.multicamp.domain.ReactUserVO;
import com.multicamp.domain.RefreshToken;
import com.multicamp.domain.ResponseVO;
import com.multicamp.domain.UserEntity;
import com.multicamp.dto.ReactUserResponseDTO;
import com.multicamp.dto.RefreshTokenDTO;
import com.multicamp.service.RefreshTokenService;
import com.multicamp.service.TokenProvider;
import com.multicamp.service.UserJpaService;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

//참고: https://github.com/urstoryp/fakeshopapi/tree/step04,
//https://www.youtube.com/watch?v=hDoa7Zw1r6c
//https://jwt.io/#debugger-io  ==> token값을 encode할 수 있는 사이트. 토큰에 대한 정보가 출력됨
@RestController
@RequestMapping("/api")
@Slf4j
public class UserReactJpaController {
	//log log = logFactory.getlog(getClass());
	@Inject
	private UserJpaService userService;

	@Inject
	private RefreshTokenService refreshTokenService;

	@Inject
	private TokenProvider tokenProvider;

	@PostMapping(value = "/join")
	public ResponseEntity<?> joinProcess(@RequestBody ReactUserVO userVo) {

		log.info("userVo=={}", userVo);
		try {
			if (userVo == null || userVo.getName() == null) {
				throw new RuntimeException("Invalid Name Value!!-null value");
			}
			if (userVo == null || userVo.getPwd() == null) {
				throw new RuntimeException("Invalid Password Value!!-null value");
			}

			// 요청을 이용해 저장할 유저 만들기
			UserEntity user = UserEntity.builder().name(userVo.getName()).nickname(userVo.getNickname())
					.pwd(userVo.getPwd()).build();

			// 서비스를 이용해 리포지터리에 유저 저장
			UserEntity registeredUser = userService.create(user);
			ReactUserVO responseUserVo = ReactUserVO.builder().idx(registeredUser.getIdx())
					.name(registeredUser.getName()).nickname(registeredUser.getNickname()).build();

			return ResponseEntity.ok().body(responseUserVo);

		} catch (Exception e) {
			// TODO: handle exception
			ResponseVO resVo = ResponseVO.builder().error(e.getMessage()).build();
			log.error("error={} ", e.getMessage());
			return ResponseEntity.badRequest().body(resVo);
		}
	}// ----------------------------------
		// 참고: https://github.com/urstoryp/fakeshopapi/tree/step04
	/*
	 * Refresh 요청 Access token이 만료되었을 때는 refresh 요청을 보내는데, 두 가지 구현 방법이 있다. [1] 인가가
	 * 필요한 요청에 대해 access token이 만료되었을 경우 401 응답을 보내고, 응답을 받은 클라이언트는 refresh 요청을 보내
	 * 새로운 access token을 발급 받은 뒤 재요청을 보낸다. [2] 클라이언트가 요청을 보내기 전, access token의
	 * payload를 통해 만료 기한을 얻고 만료 기한이 지난 토큰이라면 refresh 요청을 보낸 후에 새로운 access token을
	 * 발급받아 원래 하려던 요청을 한다.
	 */

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody ReactUserVO userVo, HttpServletRequest req,
			HttpServletResponse res) {
		log.info("userVo={}", userVo);
		try {
		// 사용자가 로그인 요청을 보내면, 닉네임과 비번이 맞는지 체크하여 사용자 인증을 수행한다.
		UserEntity user = userService.getByCredentials(userVo.getNickname(), userVo.getPwd());
		log.info("userEntity user={}", user);
		if (user != null) {
			// 기존 accessToken 확인
			String existAccessToken = req.getHeader(HttpHeaders.AUTHORIZATION);
			log.info("existAccessToken=={}",existAccessToken);
			if (existAccessToken != null && tokenProvider.validToken(existAccessToken.substring(7))) {
				log.info("1. 기존 accessToken이 있는 경우: {}", existAccessToken);

				// 기존 accessToken이 유효하면 새 토큰을 발급하지 않음
				final ReactUserResponseDTO resVo = ReactUserResponseDTO.builder().accessToken(existAccessToken)
						.userIdx(user.getIdx()).nickname(user.getNickname()).build();
				return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + existAccessToken).body(resVo);
			} else {
				log.info("2. 기존 accessToken이 없거나 유효하지 않는 경우: existAccessToken={}", existAccessToken);
				// 유효하지 않은 액세스 토큰이라면
				//쿠키에 저장된 리프레시 토큰을 가져온뒤 해당 토큰이 유효한지 체크해본다.
				Cookie[] cks=req.getCookies();
				String existingRefreshToken=null;
				if(cks!=null) {
					for(Cookie ck:cks) {
						if(ck.getName().equals("refreshToken")) {
							existingRefreshToken=ck.getValue();
						}
					}
				}
				try {
				//쿠키에서 꺼낸 토큰이 유효한지 여부를 체크 ==> 일치하지 않으면 InvalidRefreshTokenException 발생
					if(existingRefreshToken!=null) {
					refreshTokenService.matches(existingRefreshToken, user.getIdx());
					log.info("쿠키에서 가져온 기존 refreshToken 유효함************");
					final String token = tokenProvider.createToken(user, Duration.ofHours(1));// 토큰 만료 1시간 뒤로 설정
					log.info("새 accessToken발급*********");
					
					final ReactUserResponseDTO resVo = ReactUserResponseDTO.builder().accessToken(token)
							.refreshToken(existingRefreshToken).userIdx(user.getIdx()).nickname(user.getNickname()).build();
					return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(resVo);
					}else {
						log.info("쿠키에서 가져온 기존 refreshToken 이 널인 경우************");
						throw new InvalidRefreshTokenException();
					}
				}catch(InvalidRefreshTokenException ex) {
					log.error("InvalidRefreshTokenException ex==={}",ex);
					///새 access토큰과 refresh토큰 발급//////////////////////////
					// final String token=tokenProvider.create(user);//토큰 만료일 1일(디폴트 1일로 설정함)
					
					final String token = tokenProvider.createToken(user, Duration.ofHours(1));// 토큰 만료 1시간 뒤로 설정
					log.info("1. 새 accessToken을 발급하는 경우: {}", token);
					// refresh token도 발급
					Duration expiry = Duration.ofDays(1);//
					final String refreshToken = tokenProvider.createRefreshToken(user, expiry);// refresh토큰 만료 1일 뒤로 설정
					////////////////////////////////
					// refreshToken db에 저장(일련번호, 회원idx, 토큰값, 만기시간[밀리초])
					RefreshToken rEntity = new RefreshToken(user.getIdx(), refreshToken, expiry);
					refreshTokenService.addRefreshToken(rEntity);
					log.info("refreshToken테이블에 저장");
					/*
					 * final ReactUserVO resVo=ReactUserVO.builder() //==>이걸 사용하면 사용자정보 name,pwd 등도
					 * null로 응답데이터에 포함됨. .nickname(user.getNickname()) .idx(user.getIdx())
					 * .token(token) .refreshToken(refreshToken) .build();
					 */
					// refreshToken은 쿠키에 저장하여 응답에 추가하고
					// accessToken은 response 응답데이터에 포함하여 보내면=> 리액트에서 이를 받아 요청 보낼때 헤더에 포함시켜 전송한다
					Cookie ck = new Cookie("refreshToken", refreshToken);
					ck.setMaxAge((int)Duration.ofDays(1).toSeconds());//쿠키 유효시간은 밀리초가 아닌 초단위로 설정
					ck.setPath("/");
					ck.setHttpOnly(true);//쿠키의 보안 속성 (예: HttpOnly, Secure)을 설정하여 보안을 강화
					ck.setSecure(true);
					res.addCookie(ck);
	
					final ReactUserResponseDTO resVo = ReactUserResponseDTO.builder().accessToken(token)
							.refreshToken(refreshToken).userIdx(user.getIdx()).nickname(user.getNickname()).build();
					return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(resVo);
				}
			}
		}
		//인증받지 못한 경우=> user 가 null인 경우
		ResponseVO resErrVo = ResponseVO.builder().error("Login Failed").build();
		return ResponseEntity.badRequest().body(resErrVo);
	} catch (Exception e) {
        log.error("Error during authentication", e);
        ResponseVO resErrVo = ResponseVO.builder().error("Internal Server Error").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resErrVo);
    }
	}

	// 리액트에서 로그아웃 처리는 localStorage에서 access token을 삭제하는 것으로만 처리하고 별도로 백엔드 요청을 보내지는
	// 않았음
	// but 만약 백엔드로 요청을 보낸다면 아래와 같이 처리하면 될 듯===>다시 수정.react에서 api요청을 보내 refreshToken을
	// 삭제해줘야 함. 안그러면 db에 계속 쌓임
	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cks = request.getCookies();
		if (cks != null) {
			for (Cookie ck : cks) {
				if (ck.getName().equals("refreshToken")) {
					String refreshToken = ck.getValue();
					 try {
		                    this.refreshTokenService.deleteRefreshToken(refreshToken); // DB에서 refreshToken 삭제
		                    log.info("refreshToken 삭제함");

		                    // 쿠키 삭제
		                    Cookie deleteCookie = new Cookie("refreshToken", null);
		                    deleteCookie.setMaxAge(0);
		                    deleteCookie.setPath("/");
		                    deleteCookie.setHttpOnly(true);
		                    deleteCookie.setSecure(true);
		                    response.addCookie(deleteCookie);

		                } catch (Exception e) {
		                    log.error("Error deleting refreshToken", e);
		                    ResponseVO resErrVo = ResponseVO.builder().error("Error during logout").build();
		                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resErrVo);
		                }
				}//if-------------
			}//for---------------
		}//if--------------------------------

		// this.refreshTokenService.deleteRefreshToken(refreshDto.getRefreshToken());//DB에서
		// refreshToken삭제

		// SecurityContextHolder에서 등록된 인증받은 객체를 로그아웃 핸들러로 로그아웃 처리
		// logout() 메서드를 호출함으로써, 세션에서 사용자의 인증 정보를 제거하고, 필요한 경우 세션을 무효화하거나 새로운 세션을 생성할 수
		// 있다.
		new SecurityContextLogoutHandler().logout(request, response,
				SecurityContextHolder.getContext().getAuthentication());
		ResponseVO resVo = ResponseVO.builder().data(Arrays.asList("Logout Success")).build();
		return ResponseEntity.ok(resVo);
		// return new ResponseEntity(HttpStatus.OK);
    
	}

	/*
	 * 1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다. 2. RefreshToken이 유효한지 체크한다. 3. AccessToken을
	 * 발급하여 기존 RefreshToken과 함께 응답한다.
	 */
	@PostMapping("/refreshToken")
	public ResponseEntity<?> requestRefresh(@RequestBody RefreshTokenDTO refreshDto) {

		RefreshToken rtoken = this.refreshTokenService.findByRefreshToken(refreshDto.getRefreshToken());
		// db에서 요청에 들어온 refreshToken을 이용해 토큰정보를 가져온다.
		// 리프레시토큰을 parse하여 해당 토큰이 일치하는지 여부를 파악하자
		log.info("rtoken=={}: ", rtoken);

		Claims claims = this.tokenProvider.parseToken(refreshDto.getRefreshToken());
		Long userIdx = Long.valueOf((Integer) claims.get("id"));
		String nickname = claims.getSubject();
		// 리프레시토큰이 유효한지 여부를 파악하여 일치하면 db에서 해당 토큰을 삭제한다, 일치하지 않으면 예외 발생
		this.refreshTokenService.matches(rtoken.getRefreshToken(), userIdx);

		UserEntity userEnty = userService.findById(userIdx);
		String accessToken = this.tokenProvider.create(userEnty);
		ReactUserResponseDTO dto = ReactUserResponseDTO.builder().accessToken(accessToken)
				.refreshToken(refreshDto.getRefreshToken()).userIdx(userIdx).nickname(nickname).build();
		return ResponseEntity.ok(dto);
	}

}/////////////////////////////////////
