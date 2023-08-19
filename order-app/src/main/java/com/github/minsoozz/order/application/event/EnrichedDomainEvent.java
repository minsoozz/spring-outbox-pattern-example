package com.github.minsoozz.order.application.event;

public class EnrichedDomainEvent<T extends DomainEvent> {
    private String aggregateType;
    private String aggregateId;
    private T domainEvent;

    public EnrichedDomainEvent(String aggregateType, String aggregateId, T domainEvent) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.domainEvent = domainEvent;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public T getDomainEvent() {
        return domainEvent;
    }
}
