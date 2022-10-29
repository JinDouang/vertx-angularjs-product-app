import { IController } from 'angular';

export class AppController implements IController {

	public myFirstVar: string;

	$onInit(): void {
		this.myFirstVar = "This is my first value";
	}
}
