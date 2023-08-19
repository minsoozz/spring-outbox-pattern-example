package com.github.minsoozz.order.application.service;

import com.github.minsoozz.order.application.event.EnrichedDomainEvent;
import com.github.minsoozz.order.application.port.in.OrderCreateCommand;
import com.github.minsoozz.order.application.port.in.OrderUseCase;
import com.github.minsoozz.order.application.port.out.OrderPersistencePort;
import com.github.minsoozz.order.domain.Order;
import com.github.minsoozz.order.domain.OrderCreatedDomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements OrderUseCase {

    private final OrderPersistencePort orderPersistencePort;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderPersistencePort orderPersistencePort,
                        ApplicationEventPublisher eventPublisher) {
        this.orderPersistencePort = orderPersistencePort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void createOrder(OrderCreateCommand orderCreateCommand) {
        Order order = orderPersistencePort.save(Order.of(orderCreateCommand.productId()));
        eventPublisher.publishEvent(
                new EnrichedDomainEvent<>(
                        "order",
                        order.getOrderNumber(),
                        new OrderCreatedDomainEvent(
                                order.getId(),
                                order.getOrderNumber(),
                                orderCreateCommand.productId())));
    }

    @Override
    public void deleteByOrderNumber(String orderNumber) {
        orderPersistencePort.deleteByOrderNumber(orderNumber);
    }
}
