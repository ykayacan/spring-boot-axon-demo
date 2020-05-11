package io.github.ykayacan.handler;

import io.github.ykayacan.domain.OrderedProduct;
import io.github.ykayacan.event.OrderConfirmedEvent;
import io.github.ykayacan.event.OrderPlacedEvent;
import io.github.ykayacan.event.OrderShippedEvent;
import io.github.ykayacan.query.FindAllOrderedProductsQuery;
import io.github.ykayacan.query.FindFirstOrderedProductQuery;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ProcessingGroup("ordered-products")
@Service
public class OrderedProductsEventHandler {

  private final Map<String, OrderedProduct> orderedProducts = new ConcurrentHashMap<>();

  @EventHandler
  public void on(OrderPlacedEvent event) {
    orderedProducts.put(event.orderId(), OrderedProduct.create(event.orderId(), event.product()));
  }

  @EventHandler
  public void on(OrderConfirmedEvent event) {
    orderedProducts.computeIfPresent(
        event.orderId(), (orderId, orderedProduct) -> orderedProduct.orderConfirmed());
  }

  @EventHandler
  public void on(OrderShippedEvent event) {
    orderedProducts.computeIfPresent(
        event.orderId(), (orderId, orderedProduct) -> orderedProduct.orderShipped());
  }

  @QueryHandler
  public List<OrderedProduct> handle(FindAllOrderedProductsQuery query) {
    return List.copyOf(orderedProducts.values());
  }

  @QueryHandler
  public OrderedProduct handle(FindFirstOrderedProductQuery query) {
    return orderedProducts.values().stream().findFirst().orElseThrow();
  }
}
