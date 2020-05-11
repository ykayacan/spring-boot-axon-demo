package io.github.ykayacan;

import io.github.ykayacan.domain.OrderedProduct;
import io.github.ykayacan.query.FindAllOrderedProductsQuery;
import io.github.ykayacan.query.FindFirstOrderedProductQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderQueryEndpoint {

  private final QueryGateway queryGateway;

  public OrderQueryEndpoint(QueryGateway queryGateway) {
    this.queryGateway = queryGateway;
  }

  @GetMapping("/all-orders")
  public CompletableFuture<List<OrderedProduct>> findAllOrderedProducts() {
    return queryGateway.query(
        new FindAllOrderedProductsQuery(), ResponseTypes.multipleInstancesOf(OrderedProduct.class));
  }

  @GetMapping("/fist-product")
  public CompletableFuture<OrderedProduct> findFirstOrderedProduct() {
    return queryGateway.query(new FindFirstOrderedProductQuery(), OrderedProduct.class);
  }
}
