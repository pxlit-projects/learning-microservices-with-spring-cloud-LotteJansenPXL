<script setup>

</script>

<template>
<div>
  <h1> Product Catalog </h1>
  <div v-if="loading">Loading...</div>
  <div v-else>
    <div v-for="product in products" :key="product.id" class="product">
      <h3>{{ product.name }}</h3>
      <p>{{ product.description }}</p>
      <p>Price: ${{ product.price }}</p>
      <router-link :to="'/product/' + product.id">View Details</router-link>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      products: [],
      loading: true,
    };
  },
  created() {
    axios
        .get('/productcatalog/products')
        .then(response => {
          this.products = response.data;
          this.loading = false;
        })
        .catch(error => {
          console.error('Error fetching products:', error);
          this.loading = false;
        });
  },
};
</script>

<style>
  .product {
    border: 1px solid #ddd;
    padding: 15px;
    margin-bottom: 10px;
  }
</style>