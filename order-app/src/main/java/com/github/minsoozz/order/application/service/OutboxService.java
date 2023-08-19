package com.github.minsoozz.order.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.minsoozz.order.adapter.out.persistence.outbox.OutboxJpaEntity;
import com.github.minsoozz.order.adapter.out.persistence.outbox.OutboxJpaRepository;
import com.github.minsoozz.order.application.event.DomainEvent;
import com.github.minsoozz.order.application.event.EnrichedDomainEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OutboxService {

    private final OutboxJpaRepository outboxJpaRepository;
    private final ObjectMapper objectMapper;

    public OutboxService(OutboxJpaRepository outboxJpaRepository,
                         ObjectMapper objectMapper) {
        this.outboxJpaRepository = outboxJpaRepository;
        this.objectMapper = objectMapper;
    }

    @EventListener
    public void handleOrderDomainEvent(EnrichedDomainEvent<? extends DomainEvent> enrichedDomainEvent) {
        outboxJpaRepository.save(
                new OutboxJpaEntity(
                        enrichedDomainEvent.getAggregateType(),
                        enrichedDomainEvent.getAggregateId(),
                        "order-created",
                        objectMapper.convertValue(enrichedDomainEvent.getDomainEvent(), JsonNode.class)));
    }
}
