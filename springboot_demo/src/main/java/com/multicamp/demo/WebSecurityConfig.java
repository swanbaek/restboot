package com.multicamp.demo;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import com.multicamp.cmm.filter.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{
	//회원 인증 서비스 객체 주입
	@Inject
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().httpBasic().disable() //token을 사용하므로 basic인증 비활성화
		.sessionManagement()//session기반이 아님을 선언
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
				.antMatchers("/", 
						"/configuration/**",
						"/webjars/**","/js/**","/img/**",///js와 /img도 추가해야 됨
						"/upload/**",
						"/api/**","/static/**")//react요청 처리 위해 추가함 				
				.permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")//.hasAnyAuthority("ROLE_ADMIN")//관리자로만 "/admin" url패턴 접근 가능 추가				
				.antMatchers("/user/**").hasAnyRole("ADMIN","USER")
				//.access("hasRole('ADMIN') or hasRole('USER')") //hasAnyAuthority("ROLE_USER")///관리자 또는 인증 회원들만 "/user" url패턴 접근 가능 추가				
				.anyRequest() // 어떠한 URI로 접근하든지
				.authenticated();// 인증이 필요함을 설정
		
		//////filter등록////////////
		//http.addFilter(jwtAuthFilter);//이거 사용하면 에러남
		// 매 요청마다
	    // CorsFilter 실행한 후에
	    // jwtAuthenticationFilter 실행한다.
	    http.addFilterAfter(
	    		jwtAuthFilter,
	        CorsFilter.class
	    );	
	   
	}// --------------------------------

	 
	/*
	 * 백엔드, 프론트엔드가 분리되지 않은 프로젝트의 경우(스프링부트에서 jsp나 타임리프를 붙여서 하나의 프로젝트로
	 *  백엔드+프론트엔드 전부 처리하는 프로젝트) css나 이미지 파일 등의 경우 인증이 되지 않은 상태에서도 
	 *  보여져야 하는 경우가 대부분입니다. 
	 *  이 경우 별도로 WebSecurity 하나를 인자로 갖는 configure를 오버라이딩해서 
	 *  예외 처리를 할 수 있습니다.=> 이게 적용이 안되는 느낌???
	 * 
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/js/**", 
				"/static/css/**", 
				"/static/img/**", 
				"/static/frontend/**");
	}
	*/
	
}
//참조: https://github.com/nahwasa/spring-security-basic-project/tree/spring_boot_2.7.7
