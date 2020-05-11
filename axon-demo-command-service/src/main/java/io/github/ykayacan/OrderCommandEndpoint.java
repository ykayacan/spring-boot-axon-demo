package io.github.ykayacan;

import io.github.ykayacan.command.ConfirmOrderCommand;
import io.github.ykayacan.command.PlaceOrderCommand;
import io.github.ykayacan.command.ShipOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderCommandEndpoint {

  private final CommandGateway commandGateway;

  public OrderCommandEndpoint(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("/ship-order")
  public void shipOrder() {
    var orderId = UUID.randomUUID().toString();
    commandGateway
        .send(new PlaceOrderCommand(orderId, "Deluxe Chair"))
        .thenCompose(o -> commandGateway.send(new ConfirmOrderCommand(orderId)))
        .thenCompose(o -> commandGateway.send(new ShipOrderCommand(orderId)));
  }

  @PostMapping("/ship-unconfirmed-order")
  public CompletableFuture<Object> shipUnconfirmedOrder() {
    var orderId = UUID.randomUUID().toString();
    return commandGateway
        .send(new PlaceOrderCommand(orderId, "Deluxe Chair"))
        .thenCompose(o -> commandGateway.send(new ShipOrderCommand(orderId)));
  }
}
