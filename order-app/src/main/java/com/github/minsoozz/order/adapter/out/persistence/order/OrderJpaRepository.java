package com.github.minsoozz.order.adapter.out.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    void deleteByOrderNumber(String orderNumber);
}
