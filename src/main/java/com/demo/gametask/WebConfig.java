package com.demo.gametask;

import com.demo.gametask.statistic.HibernateStatisticsInterceptor;
import com.demo.gametask.statistic.RequestStatisticsInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestStatisticsInterceptor requestStatisticsInterceptor;
    private final MessageSource messageSource;

    public WebConfig(RequestStatisticsInterceptor requestStatisticsInterceptor, MessageSource messageSource) {
        this.requestStatisticsInterceptor = requestStatisticsInterceptor;
        this.messageSource = messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestStatisticsInterceptor).addPathPatterns("/**");
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
}
