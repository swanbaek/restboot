package com.multicamp.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
 WebMvcConfigurer	해당 인터페이스를 구현하면 @EnableWebMvc의 자동 설정을 베이스로 가져가며, 
 개발자가 원하는 설정까지 추가할 수 있다는 장점이 있습니다. (Override 가능)
addInterceptors( )	애플리케이션 내에 인터셉터를 등록해 줍니다. 
이 과정에서 excludePathPatterns( )를 이용하면, 메서드의 인자로 전달하는 주소(URI)와 경로(Path)는 
인터셉터 호출에서 제외시킵니다. 
여기서 해당 메서드는 resources의 모든 정적(static) 파일을 무시(ignore)하겠다는 의미로 사용됩니다.

반대의 경우로 addPathPatterns( )가 있습니다. excludePathPatterns( )가 주소와 경로를 
인터셉터 호출에서 제외한다면, 
addPathPatterns( )는 인터셉터를 호출하는 주소와 경로를 추가하는 개념입니다. 
 * */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new LoggerInterceptor())
         //       .excludePathPatterns("/css/**", "/images/**", "/js/**");
    }

} 