package com.multicamp.cmm.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.multicamp.service.TokenProvider;
/*교재 272p
 * 모든 요청에 대해 한번씩 사용자를 인증하기 위해 필터를 구현해보자 
 * ******************************************
 * OncePerRequestFilter 클래스를 상속받으면
 * - 한 요청당 반드시 한번만 실행되는 필터
 * - 따라서 한 번만 인증하면 되는 경우에 맞는 필터다
 * **********************************************
 * #작업 절차
 * 1. 요청 헤더에서 Bearer토큰을 가져온다. =>parseBearerToken() 메서드에서
 * 2. TokenProvider를 이용해 토큰을 인등하고 UsernamePasswordAuthenticationToken을 작성한다.
 * 	  이 객체에 사용자의 인증정보를 저장하고 SecurityContext에 인증된 사용자를 등록한다. 서버가 요청이
 *    끝나기 전까지 방금 인증한 사용자의 정보를 갖고 있어야 하기 때문. (사용자가 인증됐는지 여부나 
 *    사용자가 누구인지 알아야 할 때가 있기 때문)
 * */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Inject
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//요청에서 토큰 가져오기
			String token=parseBearerToken(request);
			//log.info("JwtAuthenticationFilter is running... token==={}",token);
			//log.info("token==={}",token);
			//토큰 검사
			if(token!=null&&!token.equalsIgnoreCase("null")) {
				//닉네임 가져오기. 위조된 경우 예외가 발생됨
				String nickname=tokenProvider.validateAndGetUserId(token);
				log.info("Authenticated Nickname: {}", nickname);
				//인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다
				
			 Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
				
				AbstractAuthenticationToken authentication
				=new UsernamePasswordAuthenticationToken(nickname, token,authorities);//AuthorityUtils.NO_AUTHORITIES);
					//인증된 사용자정보(nickname)을 넣어준다
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContext ctx=SecurityContextHolder.createEmptyContext();
				ctx.setAuthentication(authentication);
				//SecurityContext에 인증정보  authentication을 넣고 다시 SecurityContextHolder에 컨텍스트로 등록해야 한다
				SecurityContextHolder.setContext(ctx);
				//이제 이 필터를 컨테이너가 사용하도록 설정작업을 해야 한다. 즉 스프링 시큐리티에게 이 필터를 사용하도록 설정하자	
				
				/*//아래 코드로 실행하면 에러가 남. post글쓰기 할때 username이 null이란 에러가 발생함
				 * if(tokenProvider.validToken(token)) { Authentication authentication =
				 * tokenProvider.getAuthentication(token);
				 * SecurityContextHolder.getContext().setAuthentication(authentication);
				 * log.info("SecurityContextHolder에 인증객체 설정 완료!!!"); }
				 */
				
			}
			
		} catch (Exception e) {
			log.error("Could not set user authentication in security context!! {}", e);
		}
		filterChain.doFilter(request, response);
	}//----------------------
	private String parseBearerToken(HttpServletRequest req) {
		//http요청의 헤더를 파싱해 Bearer토큰을 반환한다
		String bearerToken=req.getHeader("Authorization");
		//log.info("bearerToken==={}",bearerToken);
		if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	/*회원가입과 로그인등의 url은 filter을 거치지 않게 해야 하므로
		OncePerRequestFilter의 shouldNotFilter()을 오버라이딩 하여 해결해보자
	 * */
 	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {"/api/join", "/api/login"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

}
