package com.davidefella.infoquiz.utility.interceptor.configuration;

import com.davidefella.infoquiz.utility.interceptor.web.SessionCleanupInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private SessionCleanupInterceptor sessionCleanupInterceptor;

    @Autowired
    public WebConfig(SessionCleanupInterceptor sessionCleanupInterceptor) {
        this.sessionCleanupInterceptor = sessionCleanupInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionCleanupInterceptor).addPathPatterns("/thank-you");
    }
}
