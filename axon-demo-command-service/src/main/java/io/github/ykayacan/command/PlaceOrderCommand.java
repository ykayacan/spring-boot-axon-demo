package io.github.ykayacan.command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record PlaceOrderCommand(@TargetAggregateIdentifier String orderId, String product) {
}
