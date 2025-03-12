package com.demo.saga.order.aggregate;

import com.demo.saga.core.events.order.CancelOrderEvent;
import com.demo.saga.core.events.order.CompleteOrderEvent;
import com.demo.saga.core.events.order.OrderCreateEvent;
import com.demo.saga.order.handler.OrderEventsHandler;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ReplayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventProcessor {
    private final OrderEventsHandler orderEventsHandler;

    public OrderEventProcessor(OrderEventsHandler orderEventsHandler) {
        this.orderEventsHandler = orderEventsHandler;
    }
    
    @EventHandler
    public void on(OrderCreateEvent event, ReplayStatus replayStatus) {
        log.info("OrderCreateEvent EventHandler : {} -> {}", event.getOrderId(), replayStatus);
        if (!replayStatus.isReplay()) {
            orderEventsHandler.onOrderCreateEvent(event);
        }
    }
    
    @EventHandler
    public void on(CompleteOrderEvent event, ReplayStatus replayStatus) {
        log.info("CompleteOrderEvent EventHandler : {}", event);
        if (!replayStatus.isReplay()) {
            orderEventsHandler.onCompleteOrderEvent(event);
        }
    }
    
    @EventHandler
    public void on(CancelOrderEvent event, ReplayStatus replayStatus) {
        log.info("CancelOrderEvent EventHandler : {}", event);
        if (!replayStatus.isReplay()) {
            orderEventsHandler.onCancelOrderEvent(event);
        }
    }
}