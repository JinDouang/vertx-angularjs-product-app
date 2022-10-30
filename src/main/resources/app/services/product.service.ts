import {Product, ProductResponse} from "../models/product.model";

export class ProductService {

    listProducts(order?: string): Promise<Array<Product>> {
        const paramOrder: string = order ? `?order=${order}` : '';
        return fetch(`/api/products${paramOrder}`)
            .then((response: Response) => response.json() as Promise<Array<ProductResponse>>)
            .then((data: Array<ProductResponse>) => data.map((product: ProductResponse) => new Product().build(product)));
    }

    getProduct(productId: string): Promise<Product> {
        return fetch(`/api/products/${productId}`)
            .then((response: Response) => response.json() as Promise<ProductResponse>)
            .then((data: ProductResponse) => new Product().build(data));
    }

    createProduct(name: string, price: number): Promise<any> {
        const body:{'name': string, 'price': number} = {'name': name, 'price': price};
        return fetch(`/api/products`, {method: 'POST', body: JSON.stringify(body)})
            .then((response: Response) => response.json());
    }

    updateProduct(productId: string, name: string, price: number): Promise<any> {
        const body:{'name': string, 'price': number} = {'name': name, 'price': price};

        return fetch(`/api/products/${productId}`, {method: 'PUT', body: JSON.stringify(body)})
            .then((response: Response) => response.json());
    }

    deleteProduct(productId: string): Promise<any> {
        return fetch(`/api/products/${productId}`, {method: 'DELETE'})
            .then((response: Response) => response.json());
    }
}