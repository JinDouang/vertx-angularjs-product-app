package io.vertx.app.product;

import io.vertx.app.product.controller.ProductController;
import io.vertx.app.product.controller.TestShareableController;
import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;


public class MainVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
  private static final String TESTS_COLLECTION = "tests";
  public static final String PRODUCTS_COLLECTION = "products";

  private TestShareableController testShareableController;
  private ProductController productController;

  @Override
  public void start(Promise<Void> startFuture) {
    // Configure routes to handlers
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    // init data
    initDataShareable();

    testShareableController = new TestShareableController(this.vertx.sharedData().getLocalMap(TESTS_COLLECTION));
    productController = new ProductController(this.vertx.sharedData().getLocalMap(PRODUCTS_COLLECTION));


    // Create the server
    vertx.createHttpServer().requestHandler(getRouter(router)).listen(8080, result -> {
      if (result.succeeded()) {
        logger.info("Vert.x server listening on port 8080");
        startFuture.complete();
      } else {
        logger.info("Vert.x server failed to start");
        logger.error(result.cause());
        startFuture.fail(result.cause().getMessage());
      }
    });
  }

  private Router getRouter(Router router) {
    // Sample endpoint "test"
    router.get("/api/tests").handler(testShareableController::tests);
    router.get("/api/tests/:id").handler(testShareableController::test);

    // Product endpoints
    router.get("/api/products").handler(productController::lists);
    router.get("/api/products/:id").handler(productController::getProduct);
    router.post("/api/products").handler(productController::createProduct);
    router.put("/api/products/:id").handler(productController::updateProduct);
    router.delete("/api/products/:id").handler(productController::deleteProduct);

    // Catch-all for non-existent API routes to return a Bad Request status code
    router.route("/api/*").handler(routingContext -> routingContext.response().setStatusCode(400).end());

    // Serve the static resources
    router.route().handler(StaticHandler.create("app").setIndexPage("static/index.html"));

    return router;
  }

  private void initDataShareable() {
    // Sample usage of Shareable
    LocalMap<String, JsonArray> testsCollectionsSharedData = this.vertx.sharedData().getLocalMap(TESTS_COLLECTION);
    testsCollectionsSharedData.put(TESTS_COLLECTION, new JsonArray()
            .add(new JsonObject().put("id", 1).put("name", "test"))
            .add(new JsonObject().put("id", 2).put("name", "test2"))
    );

    // Init some data
    LocalMap<String, JsonArray> productsCollectionsSharedData = this.vertx.sharedData().getLocalMap(PRODUCTS_COLLECTION);
    productsCollectionsSharedData.put(PRODUCTS_COLLECTION, new JsonArray()
            .add(new JsonObject()
                    .put("id", "cfd556e8-abbd-42ab-89df-956b4da308c1")
                    .put("name", "Blue Channel Perfum")
                    .put("price", 100))
            .add(new JsonObject()
                    .put("id", "477c6cf7-aefe-43b1-a526-dd4c2db6a11a")
                    .put("name", "Fitness bottle")
                    .put("price", 10))
    );
  }

}
