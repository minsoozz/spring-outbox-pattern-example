package com.github.minsoozz.order.adapter.out.persistence.outbox;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface OutboxJpaRepository extends JpaRepository<OutboxJpaEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Page<OutboxJpaEntity> findAllByOrderByIdAsc(Pageable pageable);
}
