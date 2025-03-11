package com.demo.saga.core.events.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompleteOrderEvent {
    private String orderId;
    private String orderStatus;
}