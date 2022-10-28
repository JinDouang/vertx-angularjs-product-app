package io.vertx.app.product.controller;

import io.vertx.app.product.model.Product;
import io.vertx.app.product.service.ProductService;
import io.vertx.app.product.service.impl.DefaultProductService;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;

import javax.swing.*;

public class ProductController {

    private final ProductService productService;

    private static final String CONTENT_TYPE = "content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ID = "id";

    public ProductController(LocalMap<String, JsonArray> productsCollectionsSharedData) {
        this.productService = new DefaultProductService(productsCollectionsSharedData);
    }

    public void lists(RoutingContext routingContext) {
        String order = routingContext.request().getParam("order", "");

        if (Boolean.TRUE.equals(order.equalsIgnoreCase("ASC"))) {
            order = SortOrder.ASCENDING.toString();
        } else if (Boolean.TRUE.equals(order.equalsIgnoreCase("DESC"))) {
            order = SortOrder.DESCENDING.toString();
        } else {
            order = "";
        }

        routingContext.response()
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .setStatusCode(200)
                .end(Json.encodePrettily(productService.listProducts(order)));
    }

    public void getProduct(RoutingContext routingContext) {
        String productId = routingContext.request().getParam(ID);
        routingContext.response()
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .setStatusCode(200)
                .end(Json.encodePrettily(productService.getProduct(productId)));
    }

    public void createProduct(RoutingContext routingContext) {
        productService.createProduct(routingContext.body().asJsonObject());

        routingContext.response()
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .setStatusCode(201)
                .end();
    }

    public void updateProduct(RoutingContext routingContext) {
        String productId = routingContext.request().getParam(ID);
        JsonObject productObj = routingContext.body().asJsonObject();

        productObj.put("id", productId);
        productService.updateProduct(new Product(productObj));

        routingContext.response()
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .setStatusCode(204)
                .end();
    }

    public void deleteProduct(RoutingContext routingContext) {
        String productId = routingContext.request().getParam(ID);
        productService.deleteProduct(productId);
        routingContext.response()
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .setStatusCode(204)
                .end();
    }
}
