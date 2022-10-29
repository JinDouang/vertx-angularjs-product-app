import http, {AxiosResponse} from 'axios'
import {Product, ProductResponse} from "../models/product.model";

export class ProductService {

    listProducts(order: string): Promise<Array<Product>> {
        const paramOrder: string = order ? `?order=${order}` : '';
        return http.get(`/api/products${paramOrder}`).then((data: AxiosResponse<Array<ProductResponse>>) =>
            data.data.map((productResponse: ProductResponse) => new Product().build(productResponse)));
    }

    getProduct(productId: string): Promise<Product> {
        return http.get(`/api/products/${productId}`)
            .then((data: AxiosResponse<ProductResponse>) => new Product().build(data.data));
    }

    createProduct(name: string, price: number): Promise<AxiosResponse> {
        return http.post(`/api/products`, {'name': name, 'price': price});
    }

    updateProduct(productId: string, name: string, price: number): Promise<AxiosResponse> {
        return http.put(`/api/products/${productId}`, {'name': name, 'price': price});
    }

    deleteProduct(productId: string): Promise<AxiosResponse> {
        return http.delete(`/api/products/${productId}`);
    }
}