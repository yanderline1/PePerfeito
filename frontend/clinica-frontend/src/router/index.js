import { createRouter, createWebHistory } from "vue-router";

import IndexView from "../views/IndexView.vue";

const routes = [
    {
        path: '/',
        name: 'Index', // Landing page inicial
        component: IndexView // Usando o IndexView renomeado
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/HomeView.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: ()=> import('../views/LoginView.vue')
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const isAuthenticated = localStorage.getItem('jwt_token'); // Verifica se há um token armazenado

  if (requiresAuth && !isAuthenticated) {
    // Se a rota exige autenticação e não há token, redireciona para o login
    next({ name: 'Login' });
  } else if (to.name === 'Login' && isAuthenticated) {
    // Se o usuário já tem um token e tenta ir para a página de login, redireciona para a home
    next({ name: 'Home' }); // Ou o nome da sua rota principal autenticada (ex: 'Dashboard')
  } else if (to.name === 'Index' && isAuthenticated) {
    // Se o usuário já tem um token e tenta ir para a página Splash, redireciona para a home
    next({ name: 'Home' }); // Para evitar que usuários logados vejam a splash novamente
  }
  else {
    // Permite a navegação
    next();
  }
});

export default router;