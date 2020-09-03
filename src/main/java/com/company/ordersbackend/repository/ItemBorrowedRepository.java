package com.company.ordersbackend.repository;

import com.company.ordersbackend.domain.ItemBorrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ItemBorrowedRepository extends JpaRepository<ItemBorrowed, Long> {
}
