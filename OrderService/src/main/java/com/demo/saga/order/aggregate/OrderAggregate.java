package com.demo.saga.order.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.demo.saga.core.commands.order.CancelOrderCommand;
import com.demo.saga.core.commands.order.CompleteOrderCommand;
import com.demo.saga.core.commands.order.CreateOrderCommand;
import com.demo.saga.core.events.order.CancelOrderEvent;
import com.demo.saga.core.events.order.CompleteOrderEvent;
import com.demo.saga.core.events.order.OrderCreateEvent;
import com.demo.saga.order.handler.OrderEventsHandler;
import org.springframework.beans.factory.annotation.Autowired;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;


    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //Publish the Order Create Event
        OrderCreateEvent orderCreateEvent = OrderCreateEvent.builder()
                .orderId(createOrderCommand.getOrderId())
                .orderStatus(createOrderCommand.getOrderStatus())
                .price(createOrderCommand.getPrice())
                .productId(createOrderCommand.getProductId())
                .quantity(createOrderCommand.getQuantity())
                .userId(createOrderCommand.getUserId())
                .build();
        AggregateLifecycle.apply(orderCreateEvent);
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        //Publish the Order Completed Event
        CompleteOrderEvent event = CompleteOrderEvent.builder()
                .orderId(completeOrderCommand.getOrderId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {
        //Publish the Order Cancel Event
        CancelOrderEvent event = CancelOrderEvent.builder()
                .orderId(cancelOrderCommand.getOrderId())
                .orderStatus("Cancelled")
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreateEvent event, OrderEventsHandler orderEventsHandler) {
        this.orderId = event.getOrderId();
        orderEventsHandler.onOrderCreateEvent(event);
    }

    @EventSourcingHandler
    public void on(CompleteOrderEvent event, OrderEventsHandler orderEventsHandler) {
        this.orderId = event.getOrderId();
        orderEventsHandler.onCompleteOrderEvent(event);
    }

    @EventSourcingHandler
    public void on(CancelOrderEvent event, OrderEventsHandler orderEventsHandler) {
        this.orderId = event.getOrderId();
        orderEventsHandler.onCancelOrderEvent(event);
    }

}