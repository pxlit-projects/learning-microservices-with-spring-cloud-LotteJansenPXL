import { Injectable } from "@angular/core";
import { Category } from "./category";


export class Product {
    id: number;
    name: string;
    description: string;
    price: number;
    category?: Category | null;

    constructor(
        id: number,
        name: string,
        description: string,
        price: number,
        category?: Category | null
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}