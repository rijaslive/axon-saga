package com.demo.saga.core.commands.payment;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.demo.saga.core.models.ServiceTypes;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompletePaymentCommand {

    @TargetAggregateIdentifier
    private final String paymentId;
    private String orderId;
    private String userId;
    private String price;
    private String paymentStatus = ServiceTypes.PAYMENT_STATUS.COMPLETE.toString();
}