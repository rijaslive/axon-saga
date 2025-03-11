package com.demo.saga.core.events.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelOrderEvent {
    private String orderId;
    private String orderStatus;
}