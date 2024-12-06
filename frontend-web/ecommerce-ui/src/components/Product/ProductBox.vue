<template>
<div class="card w-100 h-100" >
<!--  <img class="card-img-top" alt="Card image cap" src="...">-->
  <div class="card-body">
    <h5 class="card-title"> {{product.productName}}</h5>
    <p class="card-text">{{product.description}}</p>
    <p class="card-text">{{product.price}}</p>
    <a href="#" class="btn btn-primary"></a>
  </div>
</div>
</template>

<script>
export default {
  name: "ProductBox",
  props: ["product"],
  methods: {}
}
</script>

<style scoped>

</style>