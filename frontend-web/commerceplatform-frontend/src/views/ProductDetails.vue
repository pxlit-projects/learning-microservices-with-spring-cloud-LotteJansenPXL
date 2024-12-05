<template>
  <div>
    <h1>{{ product.name }}</h1>
    <p>{{ product.description }}</p>
    <p>Price: ${{ product.price }}</p>
    <button @click="addToCart">Add to Cart</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      product: {},
    };
  },
  created() {
    const productId = this.$route.params.id;
    axios
        .get(`/productcatalog/products/${productId}`)
        .then(response => {
          this.product = response.data;
        })
        .catch(error => {
          console.error('Error fetching product:', error);
        });
  },
  methods: {
    addToCart() {
      axios
          .post('/shoppingcart/add', { productId: this.product.id, quantity: 1 })
          .then(() => {
            alert('Product added to cart!');
          })
          .catch(error => {
            console.error('Error adding to cart:', error);
          });
    },
  },
};
</script>
