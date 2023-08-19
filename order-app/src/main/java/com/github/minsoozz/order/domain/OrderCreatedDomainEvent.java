package com.github.minsoozz.order.domain;

import com.github.minsoozz.order.application.event.DomainEvent;

public class OrderCreatedDomainEvent extends DomainEvent {
    private Long id;

    private String orderNumber;

    private Long productId;

    public OrderCreatedDomainEvent(Long id, String orderNumber, Long productId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "OrderCreatedDomainEvent{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", productId=" + productId +
                '}';
    }
}
