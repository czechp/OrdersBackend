package com.company.ordersbackend.repository;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByAppUser(AppUser appUser);
}
