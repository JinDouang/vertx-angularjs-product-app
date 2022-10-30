import {IControllerConstructor, Injectable} from "angular";
import {Product} from "../../models/product.model";

interface IListProductComponentProps {
    products: Array<Product>;
}

interface IListProductComponentController extends IListProductComponentProps {
}

class ListProductComponentController implements IListProductComponentController {

    public products: Array<Product>;
    public functionBinding:() => any;

    constructor() {
    }

    $onInit() {
    }

    $onChanges(changes) {
        console.log("changed with: ", this.products);
    }

}

export class ListProductComponent implements ng.IComponentOptions {

    public bindings: {[boundProperty: string]: string};
    public controller: string | Injectable<IControllerConstructor>;
    public templateUrl: string | Injectable<(...args: any[]) => string>;
    public controllerAs?: string;

    constructor() {
        this.bindings = {
            products: '<'
        };
        this.controller = ListProductComponentController;
        this.templateUrl = './directives/list-product/list-product.html';
        this.controllerAs = 'vm';
    }
}