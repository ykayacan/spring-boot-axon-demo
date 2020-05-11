package io.github.ykayacan.exception;

public class UnconfirmedOrderException extends Exception {

  public UnconfirmedOrderException(String orderId) {
    super("Order Id %s is not confirmed".formatted(orderId));
  }
}
