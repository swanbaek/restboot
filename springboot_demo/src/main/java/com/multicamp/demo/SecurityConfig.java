package com.multicamp.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/user/signup", "/user/login",
						"/exception/**", "/common/**", "/v2/api-docs", "/configuration/**", "/swagger*/**",
						"/webjars/**")
				.permitAll().anyRequest() // 어떠한 URI로 접근하든지
				.authenticated()// 인증이 필요함을 설정
				.and().formLogin()// 폼 로그인 방식을 사용할 것임
				.defaultSuccessUrl("/login/result", true)// 로그인 성공시 이동할 url
																//HomeController에 매핑되어 있음
				.permitAll().and().logout();// 로그아웃 처리함

	}// --------------------------------
	 
	/*
	 * 백엔드, 프론트엔드가 분리되지 않은 프로젝트의 경우(스프링부트에서 jsp나 타임리프를 붙여서 하나의 프로젝트로
	 *  백엔드+프론트엔드 전부 처리하는 프로젝트) css나 이미지 파일 등의 경우 인증이 되지 않은 상태에서도 
	 *  보여져야 하는 경우가 대부분입니다. 
	 *  이 경우 별도로 WebSecurity 하나를 인자로 갖는 configure를 오버라이딩해서 
	 *  예외 처리를 할 수 있습니다.
	 * */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/js/**", 
				"/static/css/**", 
				"/static/img/**", 
				"/static/frontend/**");
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/*********************************************************************************
- 사용자 정의 인증 처리 (Optional): configure(AuthenticationManagerBuilder auth) 메서드를 사용하여 
사용자 정의 인증 처리를 구성할 수 있습니다. 아래 예제에서는 간단한 인메모리 인증을 설정했습니다. 
실제 프로덕션 환경에서는 데이터베이스 기반의 사용자 인증 또는 LDAP, OAuth 등의 외부 인증을 사용할 수 있습니다.
	 ******************************************************************************** */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configure(): "+passwordEncoder());
		auth.inMemoryAuthentication()
			//일단 비밀번호를 암호화하지 않고 테스트하려면 {noop}을 앞에 붙여주고 테스트하자.  
			.withUser("user1").password("{noop}tiger").roles("USER");
			//.and()
			//.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
	}
	
}
