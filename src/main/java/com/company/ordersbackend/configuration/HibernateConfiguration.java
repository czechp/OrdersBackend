package com.company.ordersbackend.configuration;

import org.hibernate.EmptyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration()
public class HibernateConfiguration implements HibernatePropertiesCustomizer {

    private EmptyInterceptor emptyInterceptor;

    @Autowired()
    public HibernateConfiguration(EmptyInterceptor emptyInterceptor) {
        this.emptyInterceptor = emptyInterceptor;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", emptyInterceptor);
    }
}


