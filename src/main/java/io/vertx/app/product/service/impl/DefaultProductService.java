package io.vertx.app.product.service.impl;

import io.vertx.app.product.MainVerticle;
import io.vertx.app.product.model.Product;
import io.vertx.app.product.service.ProductService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultProductService implements ProductService {

    private LocalMap<String, JsonArray> productsCollectionsSharedData;

    public DefaultProductService(LocalMap<String, JsonArray> productsCollectionsSharedData) {
        this.productsCollectionsSharedData = productsCollectionsSharedData;
    }

    @SuppressWarnings("unchecked")
    private List<Product> products() {
        return ((List<JsonObject>) this.productsCollectionsSharedData.get(MainVerticle.PRODUCTS_COLLECTION)
                .getList())
                .stream()
                .map(Product::new)
                .collect(Collectors.toList());
    }

    @Override
    public JsonArray listProducts(String orderPrice) {
        Stream<Product> productsStream = products().stream();

        if (Boolean.TRUE.equals(orderPrice.equals(SortOrder.ASCENDING.toString()))) {
            productsStream = productsStream.sorted(Comparator.comparingInt(Product::getPrice));
        } else if (Boolean.TRUE.equals(orderPrice.equals(SortOrder.DESCENDING.toString()))) {
            productsStream = productsStream.sorted(Comparator.comparingInt(Product::getPrice).reversed());
        }

        return new JsonArray(productsStream.map(Product::toJSON).collect(Collectors.toList()));
    }

    @Override
    public JsonObject getProduct(String productId) {
        Product product = products()
                .stream()
                .filter(p -> Boolean.TRUE.equals(p.getId().equals(productId)))
                .findFirst()
                .orElse(null);

        return product != null ? product.toJSON() : null;
    }

    @Override
    public void createProduct(JsonObject productBody) {
        Product product = new Product(productBody.put("id", UUID.randomUUID()));
        List<Product> products = products();
        products.add(product);
        this.productsCollectionsSharedData.put(MainVerticle.PRODUCTS_COLLECTION,
                new JsonArray(products.stream().map(Product::toJSON).collect(Collectors.toList()))
        );
    }

    @Override
    public void updateProduct(Product updatedProduct) {
        List<Product> products = products();
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(updatedProduct.getId()))
                .findFirst();

        product.ifPresent(p -> {
            p.setPrice(updatedProduct.getPrice());
            p.setName(updatedProduct.getName());
        });

        this.productsCollectionsSharedData.put(MainVerticle.PRODUCTS_COLLECTION,
                new JsonArray(products.stream().map(Product::toJSON).collect(Collectors.toList()))
        );
    }

    @Override
    public void deleteProduct(String productId) {
        this.productsCollectionsSharedData.put(MainVerticle.PRODUCTS_COLLECTION,
                new JsonArray(products()
                        .stream()
                        .filter(product -> !product.getId().equals(productId))
                        .map(Product::toJSON)
                        .collect(Collectors.toList())));
    }
}