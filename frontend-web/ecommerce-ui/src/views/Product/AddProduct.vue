<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3"> Add Product </h3>
      </div>
    </div>
    <div class="row">
      <div class="col-3"/>
      <div class="col-6">
        <form>
          <div class="form-group">
            <label for="name">Name</label>
            <input type="text" id="name" class="form-control" v-model="productName"/>
          </div>
          <div class="form-group">
            <label for="description"> Description</label>
            <input type="text" id="description" class="form-control" v-model="description"/>
          </div>
          <div class="form-group">
            <label for="category">Category</label>
            <input type="text" id="category" class="form-control" v-model="category"/>
          </div>
          <div class="form-group">
            <label for="price">Price</label>
            <input type="text" id="price" class="form-control" v-model="price"/>
          </div>
          <button type="button" class="btn btn-primary" @click="addProduct">Submit</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
const axios=  require('axios');
const sweetalert = require('sweetalert');
 export default {
   data() {
     return {
       productName : "",
       description: "",
       category: "",
       price: "",
       };
     },
   methods: {
     addProduct() {
       console.log(this.productName, this.description);
       const newProduct = {
         name: this.productName,
         description: this.description,
         category: this.category,
         price: this.price,
       };
       axios.post('http://localhost:8083/api/product/add', newProduct)
       .then(() => {
         sweetalert.alert("Product Added Successfully!");
       }).catch(err => {console.log(err);});
       },
     },
 }
</script>



<style scoped>

</style>