<template>
  <div>
    <h1>Página Inicial</h1>
    <p>Bem-vindo ao sistema da Clínica PePerfeito!</p>
    <router-link to="/login">Ir para o Login</router-link>
    <br>
    <router-link to="/dashboard">Ir para o Dashboard (Protegido)</router-link>
  </div>
</template>
<script>
export default {
  name: 'HomeView'
};
</script>
<style scoped>
/* Seus estilos aqui */
</style>