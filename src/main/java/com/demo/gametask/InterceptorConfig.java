package com.demo.gametask;

import com.demo.gametask.statistic.HibernateStatisticsInterceptor;
import com.demo.gametask.statistic.RequestStatisticsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

    @Bean
    public RequestStatisticsInterceptor requestStatisticsInterceptor() {
        return new RequestStatisticsInterceptor();
    }

    @Bean
    public HibernateStatisticsInterceptor hibernateInterceptor() {
        return new HibernateStatisticsInterceptor();
    }
}
