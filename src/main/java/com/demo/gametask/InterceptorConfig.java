package com.demo.gametask;

import com.demo.gametask.statistics.HibernateStatisticsInterceptor;
import com.demo.gametask.statistics.RequestStatisticsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

    @Bean
    public static RequestStatisticsInterceptor requestStatisticsInterceptor() {
        return new RequestStatisticsInterceptor();
    }

    @Bean
    public static HibernateStatisticsInterceptor hibernateInterceptor() {
        return new HibernateStatisticsInterceptor();
    }
}
