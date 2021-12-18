import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/services/product.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[];
  currentCatId: number;
  searchMode: boolean;

  constructor(private productService: ProductService,
              private route: ActivatedRoute) { }

  ngOnInit(){
    this.route.paramMap.subscribe(()=>{
      this.listProducts();
    })
  }
  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if (this.searchMode) {
      this.handleSearchProducts();
    } else {
      this.handleListProducts();
    }
  }
  handleListProducts(){
    // Verify if ID Parameter is Available
    const hasCatID: boolean = this.route.snapshot.paramMap.has('id');
    if(hasCatID){
      // Retrieve Cat. ID and Cast to Number from String
      // @ts-ignore
      this.currentCatId = +this.route.snapshot.paramMap.get('id');
    }
    else{
      // If no cat id, then default = 1
      this.currentCatId = 1;
    }
    this.productService.getProductList(this.currentCatId).subscribe(
      data => {
        this.products = data;
      }
    )
  }
  private handleSearchProducts() {
    // @ts-ignore
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword');

    // search for products using keyword
    this.productService.searchProducts(theKeyword).subscribe(
      data => {
        this.products = data;
      }
    )
  }
}
