<template>
  <div id="app">
    <Navbar v-if="shouldShowNavbar"/>
    
    <router-view/>
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue';
export default {
  name: 'App',
  components: {
    Navbar
  },
  computed: {
    shouldShowNavbar() {
      // Exibir a Navbar apenas se não estiver na rota de login
      return this.$route.path !== '/login';
    }
  }
};
</script>

<style>
/* Estilos globais para a aplicação */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
nav {
  padding: 30px;
}
nav a {
  font-weight: bold;
  color: #2c3e50;
}
nav a.router-link-exact-active {
  color: #42b983;
}
</style>