import * as angular from "angular";
import {AppController} from "./controllers/app.controller";
import {ProductService} from "./services/product.service";
import {ListProductComponent} from "./directives/list-product/list-product.component";
import {CreateProductComponent} from "./directives/create-product/create-product.component";

angular.module("app", [])
    .service('productService', ProductService)
    .controller("appCtrl", ['$scope', 'productService', AppController])
    .component('listProduct', new ListProductComponent())
    .component('createProduct', new CreateProductComponent())
    // .component('createProduct', CounterComponent)
    // .component('modalProduct', CounterComponent)
