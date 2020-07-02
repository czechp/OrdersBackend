package com.company.ordersbackend.repository;

import com.company.ordersbackend.domain.ItemInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Long> {
}
