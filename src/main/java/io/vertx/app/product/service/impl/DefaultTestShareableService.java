package io.vertx.app.product.service.impl;

import io.vertx.app.product.service.TestShareableService;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;

import java.util.List;

public class DefaultTestShareableService implements TestShareableService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTestShareableService.class);


    private final LocalMap<String, JsonArray> testsCollectionsSharedData;

    public DefaultTestShareableService(LocalMap<String, JsonArray> testsCollectionsSharedData) {
        this.testsCollectionsSharedData = testsCollectionsSharedData;
    }

    @Override
    public JsonArray listTests() {
        return testsCollectionsSharedData.get("tests");
    }
    @Override
    @SuppressWarnings("unchecked")
    public JsonObject testById(Number testId) {
        logger.info(testId);
        logger.info(testsCollectionsSharedData.get("tests").getList());
        return ((List<JsonObject>) testsCollectionsSharedData.get("tests").getList())
                .stream()
                .filter(test -> Boolean.TRUE.equals(test.getNumber("id").equals(testId)))
                .findFirst()
                .orElse(null);
    }
}