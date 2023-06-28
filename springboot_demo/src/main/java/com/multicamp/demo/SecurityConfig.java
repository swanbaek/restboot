package com.multicamp.demo;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.multicamp.service.LoginValidatorService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//회원 인증 서비스 객체 주입
	@Inject
	private LoginValidatorService loginValidatorService;

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/index","/swagger-ui.html", "/swagger-resources/**", "/user/signup", "/user/login",
						"/exception/**", "/common/**", "/v2/api-docs", "/configuration/**", "/swagger*/**",
						"/webjars/**","/js/**","/img/**") ///js와 /img도 추가해야 됨				
				.permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")//.hasAnyAuthority("ROLE_ADMIN")//관리자로만 "/admin" url패턴 접근 가능 추가				
				.antMatchers("/user/**").access("hasRole('ADMIN') or hasRole('USER')") //hasAnyAuthority("ROLE_USER")///관리자 또는 인증 회원들만 "/user" url패턴 접근 가능 추가				
				.anyRequest() // 어떠한 URI로 접근하든지
				.authenticated()// 인증이 필요함을 설정				
				.and().formLogin()// 폼 로그인 방식을 사용할 것임
				.loginPage("/user/login") //커스텀 페이지로 로그인 페이지를 변경한다////////////
				.loginProcessingUrl("/user/loginProc")
				.usernameParameter("userid")
				.passwordParameter("passwd")
				.defaultSuccessUrl("/login/result", true)// 로그인 성공시 이동할 url																//HomeController에 매핑되어 있음
				.permitAll()
				.and()
				.logout()// 로그아웃 처리함 디폴트로 로그아웃시 url은  "/logout"로 잡혀있다.
				//.logoutUrl("/user/logout");//다른 url로 설정하고 싶다면 왼쪽과 같이 설정한다.
				.and()
				.exceptionHandling()
				.accessDeniedPage("/accessDenied");

	}// --------------------------------
	 
	/*
	 * 백엔드, 프론트엔드가 분리되지 않은 프로젝트의 경우(스프링부트에서 jsp나 타임리프를 붙여서 하나의 프로젝트로
	 *  백엔드+프론트엔드 전부 처리하는 프로젝트) css나 이미지 파일 등의 경우 인증이 되지 않은 상태에서도 
	 *  보여져야 하는 경우가 대부분입니다. 
	 *  이 경우 별도로 WebSecurity 하나를 인자로 갖는 configure를 오버라이딩해서 
	 *  예외 처리를 할 수 있습니다.=> 이게 적용이 안되는 느낌???
	 * */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/js/**", 
				"/static/css/**", 
				"/static/img/**", 
				"/static/frontend/**");
	}
	
	/*********************************************************************************
- 사용자 정의 인증 처리 (Optional): configure(AuthenticationManagerBuilder auth) 메서드를 사용하여 
사용자 정의 인증 처리를 구성할 수 있습니다. 아래 예제에서는 간단한 인메모리 인증을 설정했습니다. 
실제 프로덕션 환경에서는 데이터베이스 기반의 사용자 인증 또는 LDAP, OAuth 등의 외부 인증을 사용할 수 있습니다.
	 ******************************************************************************** */
	/* ==> 이부분은 데이터베이스 기반의 사용자 인증으로 수정함*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//System.out.println("configure(): "+passwordEncoder()); //passwordEncoder는 LoginValidatorService로 옮김
		auth.userDetailsService(loginValidatorService);
			//auth.inMemoryAuthentication()
			//일단 비밀번호를 암호화하지 않고 테스트하려면 {noop}을 앞에 붙여주고 테스트하자.  
			//.withUser("user1").password("{noop}tiger").roles("USER");
			//.and()
			//.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
	}
	
}
//참조: https://github.com/nahwasa/spring-security-basic-project/tree/spring_boot_2.7.7
