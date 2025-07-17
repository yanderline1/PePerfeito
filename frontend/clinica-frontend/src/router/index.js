import { createRouter, createWebHistory } from "vue-router";

import IndexView from "../views/IndexView.vue"; // Renomeado para consistência
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
// Não precisa importar DashboardView se foi descartado e não será usado.

const routes = [
    {
        path: '/',
        name: 'Index', // Landing page inicial
        component: IndexView // Usando o IndexView renomeado
    },
    {
        path: '/home',
        name: 'Home',
        component: HomeView
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginView
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    // Verifica se a rota para a qual estamos navegando (to) ou qualquer um de seus ancestrais
    // tem a meta propriedade 'requiresAuth' definida como true
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // <<< IMPORTANTE: Esta é a sua lógica real de verificação de autenticação
        // Você precisará de um sistema de estado global (como Pinia ou Vuex) para isso
        const isAuthenticated = false; // <<< SUBSTITUA ISSO PELA SUA VARIÁVEL DE ESTADO DE AUTENTICAÇÃO REAL

        if (!isAuthenticated) {
            next({ name: 'Login' }); // Se não estiver autenticado, redireciona para a rota de login
        } else {
            next(); // Se estiver autenticado, permite a navegação
        }
    } else {
        next(); // Se a rota não requer autenticação, permite a navegação
    }
});

export default router;