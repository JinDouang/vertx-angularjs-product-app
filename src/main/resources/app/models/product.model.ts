export interface ProductResponse {
    id: string;
    name: string;
    price: number;
}

export class Product {

    private _id: string;
    private _name: string;
    private _price: number;

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get price(): number {
        return this._price;
    }

    set price(value: number) {
        this._price = value;
    }

    constructor() {

    }

    build(data: ProductResponse): Product {
        this._id = data.id;
        this._name = data.name;
        this._price = data.price;
        return this;
    }
}