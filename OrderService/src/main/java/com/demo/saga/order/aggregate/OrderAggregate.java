package com.demo.saga.order.aggregate;

import com.demo.saga.core.commands.order.CreateOrderCommand;
import com.demo.saga.core.events.order.CancelOrderEvent;
import com.demo.saga.core.events.order.CompleteOrderEvent;
import com.demo.saga.core.events.order.OrderCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

@Aggregate
@Slf4j
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        // Create and apply event
        OrderCreateEvent orderCreateEvent = OrderCreateEvent.builder()
                .orderId(createOrderCommand.getOrderId())
                .orderStatus(createOrderCommand.getOrderStatus())
                .price(createOrderCommand.getPrice())
                .productId(createOrderCommand.getProductId())
                .quantity(createOrderCommand.getQuantity())
                .userId(createOrderCommand.getUserId())
                .dateTime(LocalDateTime.now())
                .build();
        AggregateLifecycle.apply(orderCreateEvent);
    }

    // Other command handlers...

    @EventSourcingHandler
    public void on(OrderCreateEvent event) {
        this.orderId = event.getOrderId();
        log.info("OrderCreateEvent dateTime : {}", event.getDateTime());
    }

    @EventSourcingHandler
    public void on(CompleteOrderEvent event) {
        this.orderId = event.getOrderId();
    }

    @EventSourcingHandler
    public void on(CancelOrderEvent event) {
        this.orderId = event.getOrderId();
    }
}