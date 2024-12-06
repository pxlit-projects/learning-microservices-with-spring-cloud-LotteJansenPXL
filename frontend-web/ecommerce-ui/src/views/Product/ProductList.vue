<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3"> Products </h3>
        <router-link :to="{ name: 'addProduct' }">
          <button class="btn btn-primary">Add Product</button>
        </router-link>
      </div>
      <div v-if="products.length > 0">
        <div class="row">
          <div v-for="product in products" :key="product.id" class="col-xl-4 col-md-6 flex">
            <ProductBox :product="product"></ProductBox>
          </div>
      </div>
      </div>
      <div v-else>
        <p>No products available.</p>
      </div>
    </div>
  </div>
</template>

<script>
const axios=  require('axios');
import ProductBox from "@/components/Product/ProductBox.vue";
export default {
  components: {ProductBox},
  data() {
    return {
      products:[]
    };
  },
  methods: {
  async getProducts() {
    await axios.get('http://localhost:8083/api/product').then((response) => {
      this.products = response.data;
    }).catch((error => console.log(error)));
  }
  },
  mounted() {
    this.getProducts();
  }

}
</script>



<style scoped>

</style>