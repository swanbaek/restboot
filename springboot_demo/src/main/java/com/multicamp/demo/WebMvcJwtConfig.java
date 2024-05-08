package com.multicamp.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebMvcJwtConfig {
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/", 
						"/configuration/**",
						"/webjars/**","/js/**","/img/**",///js와 /img도 추가해야 됨
						"/upload/**",
						"/api/**","/static/**")//react요청 처리 위해 추가
                .permitAll() 
                .antMatchers("/admin/**").hasRole("ADMIN")//.hasAnyAuthority("ROLE_ADMIN")//관리자로만 "/admin" url패턴 접근 가능 추가				
				.antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                //.logout().invalidateHttpSession(true)
                    //.logoutSuccessUrl("/login")
                .csrf().disable()
                .httpBasic().disable()//token을 사용하므로 basic인증 비활성화
                .sessionManagement()//session기반이 아님을 선언
        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        		.and()
                .build();
    }

}
