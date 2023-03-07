package com.example.petfoodanalyzer.config;

import com.example.petfoodanalyzer.web.interceptor.SessionTimerInterceptor;
import com.example.petfoodanalyzer.web.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private UserInterceptor userInterceptor;
    private SessionTimerInterceptor sessionTimerInterceptor;

    public WebConfig(UserInterceptor userInterceptor, SessionTimerInterceptor sessionTimerInterceptor) {
        this.userInterceptor = userInterceptor;
        this.sessionTimerInterceptor = sessionTimerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
        registry.addInterceptor(sessionTimerInterceptor);
    }
}