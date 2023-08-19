package com.github.minsoozz.order.scheduler;

import com.github.minsoozz.order.adapter.out.persistence.outbox.OutboxJpaEntity;
import com.github.minsoozz.order.adapter.out.persistence.outbox.OutboxJpaRepository;
import com.github.minsoozz.order.application.port.out.OrderPublishPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Transactional
public class OutboxMessageRelay {

    private static Logger logger = LoggerFactory.getLogger(OutboxMessageRelay.class);
    private final OutboxJpaRepository outboxJpaRepository;
    private final OrderPublishPort orderPublishPort;

    public OutboxMessageRelay(OutboxJpaRepository outboxJpaRepository,
                              OrderPublishPort orderPublishPort) {
        this.outboxJpaRepository = outboxJpaRepository;
        this.orderPublishPort = orderPublishPort;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishOutboxMessages() {
        logger.info("Publishing outbox messages");
        List<OutboxJpaEntity> entities = outboxJpaRepository.findAllByOrderByIdAsc(Pageable.ofSize(10)).toList();
        for (OutboxJpaEntity entity : entities) {
            orderPublishPort.publishOrderCreatedEvent(entity.getAggregateId());
        }
        outboxJpaRepository.deleteAllInBatch();
    }
}
