import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import ProductDetails from '../views/ProductDetails.vue';
import ShoppingCart from '../components/ShoppingCart.vue';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/product/:id',
        name: 'ProductDetails',
        component: ProductDetails,
    },
    {
        path: '/shoppingcart',
        name: 'ShoppingCart',
        component: ShoppingCart,
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});

export default router;