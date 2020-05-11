package io.github.ykayacan;

import io.github.ykayacan.command.ConfirmOrderCommand;
import io.github.ykayacan.command.PlaceOrderCommand;
import io.github.ykayacan.command.ShipOrderCommand;
import io.github.ykayacan.event.OrderConfirmedEvent;
import io.github.ykayacan.event.OrderPlacedEvent;
import io.github.ykayacan.event.OrderShippedEvent;
import io.github.ykayacan.exception.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

  @AggregateIdentifier private String orderId;
  private boolean orderConfirmed;

  protected OrderAggregate() {}

  @CommandHandler
  public OrderAggregate(PlaceOrderCommand command) {
    apply(new OrderPlacedEvent(command.orderId(), command.product()));
  }

  @CommandHandler
  public void handle(ConfirmOrderCommand command) {
    apply(new OrderConfirmedEvent(command.orderId()));
  }

  @CommandHandler
  public void handle(ShipOrderCommand command) throws UnconfirmedOrderException {
    if (!orderConfirmed) {
      throw new UnconfirmedOrderException(command.orderId());
    }
    apply(new OrderShippedEvent(command.orderId()));
  }

  @EventSourcingHandler
  public void on(OrderPlacedEvent event) {
    this.orderId = event.orderId();
    this.orderConfirmed = false;
  }

  @EventSourcingHandler
  public void on(OrderConfirmedEvent event) {
    this.orderConfirmed = true;
  }
}
