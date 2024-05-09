package com.multicamp.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.multicamp.service.UserJpaDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcJwtConfig {
	
	private final UserJpaDetailService userService;

	/*BCrypt는 비밀번호를 안전하게 저장하기 위한 해시 함수. 
	 * BCrypt는 비밀번호 해싱을 위해 Blowfish 암호화 알고리즘을 사용하며, 
	 * 암호화된 비밀번호를 저장할 때 임의의 솔트(salt)를 생성하여 비밀번호의 보안성을 높입니다.
	   BCrypt는 강력한 암호화 알고리즘을 사용하기 때문에 해독이 거의 불가능합니다. 
	   이는 해커가 데이터베이스를 공격하여 해시된 비밀번호를 복원하는 것을 어렵게 만듭니다. 
	 * */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests().antMatchers("/", "/configuration/**", "/webjars/**", "/js/**", "/img/**", /// js와
				"/upload/**", "/api/**", "/static/**")// react요청 처리 위해 추가
				.permitAll().antMatchers("/admin/**").hasRole("ADMIN")// .hasAnyAuthority("ROLE_ADMIN")//관리자로만 "/admin"
																		// url패턴 접근 가능 추가
				.antMatchers("/user/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated().and().formLogin()
				.disable()
				// .logout().invalidateHttpSession(true)
				// .logoutSuccessUrl("/login")
				.csrf().disable().httpBasic().disable()// token을 사용하므로 basic인증 비활성화
				.sessionManagement()// session기반이 아님을 선언
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();
	}
	/*AuthenticationManager는 주어진 인증 요청을 처리하여 인증된 사용자에 대한 Authentication 객체를 반환하는 인터페이스이며, 
	 * 이를 통해 Spring Security는 다양한 인증 방식을 지원하고 사용자의 인증을 관리함.
			- 인터페이스의 주요 메서드:
			Authentication authenticate(Authentication auth): 주어진 인증 객체를 사용하여 인증을 시도하고, 
									인증된 사용자에 대한 Authentication 객체를 반환합니다. 
									예외를 발생시키거나 인증에 실패한 경우 null을 반환할 수 있다.
	 * */
	//사용자의 인증을 처리하는 데 필요한 구성을 정의하는 메서드
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, 
    		UserJpaDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)//사용자의 인증정보를 가져오는 서비스를 설정함
                .passwordEncoder(bCryptPasswordEncoder)//사용자 비번을 암호화하는 방법 설정
                .and()//이전 설정과 다음 설정을 연결
                .build();
        
    }
}
