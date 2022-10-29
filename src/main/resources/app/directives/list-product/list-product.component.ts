import {IControllerConstructor, Injectable} from "angular";

interface IListProductComponentProps {
    textBinding: string;
    dataBinding: number;
    functionBinding: () => any;
}

interface IListProductComponentController extends IListProductComponentProps {
    add(): void;
}

class ListProductComponentController implements IListProductComponentController {

    public textBinding: string;
    public dataBinding: number;
    public functionBinding:() => any;

    constructor() {
        this.textBinding = '';
        this.dataBinding = 0;
    }

    add(): void {
        this.functionBinding();
    }

}

export class ListProductComponent implements ng.IComponentOptions {

    public bindings: {[boundProperty: string]: string};
    public controller: string | Injectable<IControllerConstructor>;
    public templateUrl: string | Injectable<(...args: any[]) => string>;
    public controllerAs?: string;

    constructor() {
        this.bindings = {
            textBinding: '@',
            dataBinding: '<',
            functionBinding: '&'
        };
        this.controller = ListProductComponentController;
        this.templateUrl = './directives/list-product/list-product.html';
        this.controllerAs = 'vm';
    }
}