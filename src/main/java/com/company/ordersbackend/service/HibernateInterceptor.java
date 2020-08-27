package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Task;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Component()
public class HibernateInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        if (entity instanceof Task && ((Task) entity).getId() == 0L)
            ((Task) entity).setCreatingDate(LocalDateTime.now());

        return super.onSave(entity, id, state, propertyNames, types);
    }
}
