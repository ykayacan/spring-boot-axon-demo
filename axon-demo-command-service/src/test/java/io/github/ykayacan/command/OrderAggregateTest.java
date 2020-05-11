package io.github.ykayacan.command;

import io.github.ykayacan.OrderAggregate;
import io.github.ykayacan.event.OrderConfirmedEvent;
import io.github.ykayacan.event.OrderPlacedEvent;
import io.github.ykayacan.event.OrderShippedEvent;
import io.github.ykayacan.exception.UnconfirmedOrderException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class OrderAggregateTest {

  private FixtureConfiguration<OrderAggregate> fixture;

  @BeforeEach
  void setUp() {
    fixture = new AggregateTestFixture<>(OrderAggregate.class);
  }

  @Test
  void givenNoPriorActivity_whenPlaceOrderCommand_thenShouldPublishOrderPlacedEvent() {
    var orderId = UUID.randomUUID().toString();
    var product = "Deluxe Chair";
    fixture
        .givenNoPriorActivity()
        .when(new PlaceOrderCommand(orderId, product))
        .expectEvents(new OrderPlacedEvent(orderId, product));
  }

  @Test
  void givenOrderPlacedEventAndOrderConfirmedEvent_whenShipOrderCommand_thenShouldPublishOrderShippedEvent() {
    var orderId = UUID.randomUUID().toString();
    var product = "Deluxe Chair";
    fixture
        .given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
        .when(new ShipOrderCommand(orderId))
        .expectEvents(new OrderShippedEvent(orderId));
  }

  @Test
  void givenOrderPlacedEvent_whenConfirmOrderCommand_thenShouldPublishOrderConfirmedEvent() {
    var orderId = UUID.randomUUID().toString();
    var product = "Deluxe Chair";
    fixture
        .given(new OrderPlacedEvent(orderId, product))
        .when(new ConfirmOrderCommand(orderId))
        .expectEvents(new OrderConfirmedEvent(orderId));
  }

  @Test
  void givenOrderPlacedEvent_whenShipOrderCommand_thenShouldThrowUnconfirmedOrderException() {
    var orderId = UUID.randomUUID().toString();
    var product = "Deluxe Chair";
    fixture
        .given(new OrderPlacedEvent(orderId, product))
        .when(new ShipOrderCommand(orderId))
        .expectException(UnconfirmedOrderException.class);
  }
}
