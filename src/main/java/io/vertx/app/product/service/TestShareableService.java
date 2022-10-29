package io.vertx.app.product.service;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface TestShareableService {

    /**
     * list all tests collection
     * @return JsonArray {@link JsonArray} containing all items in tests collections
     */
    JsonArray listTests();

    /** get test by id
     *
     * @param testId test identifier in collections
     * @return JsonObject {@link JsonObject} containing item fetched by id
     */
    JsonObject testById(Number testId);
}
