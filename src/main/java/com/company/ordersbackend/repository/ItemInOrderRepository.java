package com.company.ordersbackend.repository;

import com.company.ordersbackend.domain.ItemInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Long> {
    List<ItemInOrder> findByIsOrderedAndIsDelivered(boolean isOrdered, boolean isDelivered);
}
