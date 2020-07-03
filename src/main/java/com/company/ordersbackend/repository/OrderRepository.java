package com.company.ordersbackend.repository;

import com.company.ordersbackend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface OrderRepository extends JpaRepository<Order, Long> {
}
