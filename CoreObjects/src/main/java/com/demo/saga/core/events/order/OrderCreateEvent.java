package com.demo.saga.core.events.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderCreateEvent {
    private String orderId;
    private String productId;
    private String userId;
    private String quantity;
    private String price;
    private String orderStatus;
    private String comment;
    private LocalDateTime dateTime;
}
