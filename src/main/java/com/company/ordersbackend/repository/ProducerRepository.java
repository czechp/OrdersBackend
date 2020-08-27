package com.company.ordersbackend.repository;

import com.company.ordersbackend.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    boolean existsByName(String name);

    Optional<Producer> findByName(String name);
}
