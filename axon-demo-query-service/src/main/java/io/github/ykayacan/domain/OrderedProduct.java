package io.github.ykayacan.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record OrderedProduct(
    String orderId,
    String product,
    OrderStatus status
) {

  public static OrderedProduct create(String orderId, String product) {
    return new OrderedProduct(orderId, product, OrderStatus.PLACED);
  }

  public OrderedProduct orderConfirmed() {
    return new OrderedProduct(orderId, product, OrderStatus.CONFIRMED);
  }

  public OrderedProduct orderShipped() {
    return new OrderedProduct(orderId, product, OrderStatus.SHIPPED);
  }
}
