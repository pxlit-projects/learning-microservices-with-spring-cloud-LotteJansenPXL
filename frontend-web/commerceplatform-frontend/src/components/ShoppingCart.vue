<template>
  <div>
    <h1>Shopping Cart</h1>
    <div v-if="loading">Loading...</div>
    <div v-else>
      <div v-for="item in cartItems" :key="item.productId" class="cart-item">
        <h3>{{ item.product.name }}</h3>
        <p>Quantity: {{ item.quantity }}</p>
        <p>Total: ${{ item.product.price * item.quantity }}</p>
      </div>
      <div v-if="cartItems.length === 0">
        <p>Your cart is empty.</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      cartItems: [],
      loading: true,
    };
  },
  created() {
    axios
        .get('/shoppingcart')
        .then(response => {
          this.cartItems = response.data;
          this.loading = false;
        })
        .catch(error => {
          console.error('Error fetching cart items:', error);
          this.loading = false;
        });
  },
};
</script>

<style>
.cart-item {
  border: 1px solid #ddd;
  padding: 15px;
  margin-bottom: 10px;
}
</style>
