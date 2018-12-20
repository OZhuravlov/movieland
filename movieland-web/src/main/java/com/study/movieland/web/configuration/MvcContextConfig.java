package com.study.movieland.web.configuration;

import com.study.movieland.web.controller.interceptor.PermissionCheckInterceptor;
import com.study.movieland.web.controller.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.study.movieland.web")
public class MvcContextConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(new SecurityInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new PermissionCheckInterceptor())
                .addPathPatterns("/review/**")
                .addPathPatterns("/movie/**");
    }

}
