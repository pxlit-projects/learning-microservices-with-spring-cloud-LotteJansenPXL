import { Injectable } from "@angular/core";
import { Product } from "./product";


export class Category {
    id?: number;
    name: string;

    constructor(name: string, id?: number) {
        this.name = name;
        if (id !== undefined) {
            this.id = id;
        }
    }

}