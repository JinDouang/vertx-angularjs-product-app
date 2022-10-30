import {IControllerConstructor, Injectable} from "angular";
import {Product} from "../../models/product.model";

interface ICreateProductComponentProps {
    onCreate: () => any;
}

interface ICreateProductComponentController extends ICreateProductComponentProps {
    add(): void;
}

class ListProductComponentController implements ICreateProductComponentController {

    public onCreate:() => any;

    constructor() {
    }

    $onInit() {
    }

    add(): void {
        this.onCreate();
    }

}

export class CreateProductComponent implements ng.IComponentOptions {

    public bindings: {[boundProperty: string]: string};
    public controller: string | Injectable<IControllerConstructor>;
    public templateUrl: string | Injectable<(...args: any[]) => string>;
    public controllerAs?: string;

    constructor() {
        this.bindings = {
            onCreate: '&'
        };
        this.controller = ListProductComponentController;
        this.templateUrl = './directives/create-product/create-product.html';
        this.controllerAs = 'vm';
    }
}