import * as angular from "angular";
import {AppController} from "./controllers/app.controller";


angular.module("app", [])
    .controller("appCtrl", ['$scope', AppController])
