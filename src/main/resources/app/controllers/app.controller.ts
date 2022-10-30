import { IController } from 'angular';
import {Product} from "../models/product.model";
import {ProductService} from "../services/product.service";

export class AppController implements IController {

	public myFirstVar: string;
	public products: Array<Product>;

	constructor(private scope: ng.IScope,
				private productService: ProductService) {
		this.products = [];
	}

	async $onInit(): Promise<void> {
		await this.listProducts();
		this.scope.$apply();
	}

	async listProducts(): Promise<void> {
		this.products = await this.productService.listProducts()
			.catch((_: Response) => []);
	}
}
