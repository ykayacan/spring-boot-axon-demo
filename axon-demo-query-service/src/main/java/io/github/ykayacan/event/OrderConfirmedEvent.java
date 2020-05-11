package io.github.ykayacan.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record OrderConfirmedEvent(String orderId) {
}
