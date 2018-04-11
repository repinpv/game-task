package com.demo.gametask;

import com.demo.gametask.statistic.HibernateStatisticsInterceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernateInterceptorCustomizer implements HibernatePropertiesCustomizer {

    private final HibernateStatisticsInterceptor hibernateStatisticsInterceptor;

    public HibernateInterceptorCustomizer(HibernateStatisticsInterceptor hibernateStatisticsInterceptor) {
        this.hibernateStatisticsInterceptor = hibernateStatisticsInterceptor;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", hibernateStatisticsInterceptor);
    }
}
