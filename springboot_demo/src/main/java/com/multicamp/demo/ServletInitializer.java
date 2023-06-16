package com.multicamp.demo;


import java.util.Collections;


import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/*
 * ServletInitializer 클래스는 SpringBootServletInitializer 클래스를 상속받고 있다. 
 * 스프링에서는 웹 어플리케이션을 생성하면 web.xml을 생성해 몇가지 설정을 하였다.
 * 스프링 부트에서는  ServletInitializer가 web.xml 기능을 수행함
 * ServletInitializer는 web.xml을 없애주는 기능을 한다. 
 * 즉,spring boot프로젝트는 web.xml에 설정을 할 필요가 없는 것이다.
 * */
@Component
//@Slf4j
public class ServletInitializer extends SpringBootServletInitializer {

    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory ( ) {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                super.postProcessContext(context);
                JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
                jspPropertyGroup.addUrlPattern("*.jsp");
                jspPropertyGroup.addUrlPattern("*.jspf");
                jspPropertyGroup.setPageEncoding("UTF-8");
                jspPropertyGroup.setScriptingInvalid("true");
                jspPropertyGroup.addIncludePrelude("/WEB-INF/views/inc/top.jspf");
                jspPropertyGroup.addIncludeCoda("/WEB-INF/views/inc/bottom.jspf");
                jspPropertyGroup.setTrimWhitespace("true");
                jspPropertyGroup.setDefaultContentType("text/html");
                JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(jspPropertyGroup);
                context.setJspConfigDescriptor(new JspConfigDescriptorImpl(Collections.singletonList(jspPropertyGroupDescriptor), Collections.emptyList()));
            }
        };
    }
}
