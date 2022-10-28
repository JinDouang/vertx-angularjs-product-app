package io.vertx.app.product.service;

import io.vertx.app.product.model.Product;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface ProductService {

    JsonArray listProducts(String orderPrice);

    JsonObject getProduct(String productId);

    void createProduct(JsonObject productBody);

    void updateProduct(Product product);

    void deleteProduct(String productId);
}
