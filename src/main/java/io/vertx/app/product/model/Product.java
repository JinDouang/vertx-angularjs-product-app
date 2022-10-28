package io.vertx.app.product.model;

import io.vertx.core.json.JsonObject;

public class Product {
    private final String id;
    private String name;
    private int price;

    public Product(JsonObject productObj) {
        this.id = productObj.getString("id");
        this.name = productObj.getString("name");
        this.price = productObj.getNumber("price").intValue();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public JsonObject toJSON() {
        return new JsonObject()
                .put("id", this.id)
                .put("name", this.name)
                .put("price", this.price);
    }

}
