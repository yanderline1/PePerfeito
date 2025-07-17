<template>
  <nav class="navbar">
    <router-link to="/" class="nav-brand">Clínica Pé Perfeito</router-link>
    <div class="nav-links">
      <router-link to="/" class="nav-item">Início</router-link>
      <router-link to="/home" class="nav-item">Home</router-link>
      <router-link to="/login" class="nav-item">Login</router-link>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'NavBar'
};
</script>

<style scoped>
.navbar {
  background-color: #333; /* Fundo escuro para a navbar */
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.nav-brand {
  color: white;
  font-size: 1.5em;
  font-weight: bold;
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 20px; /* Espaço entre os itens do menu */
}

.nav-item {
  color: #a0a0a0; /* Cor padrão do texto dos links */
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s ease;
}

.nav-item:hover, .nav-item.router-link-exact-active {
  color: #42b983; /* Cor destacada ao passar o mouse ou na rota ativa */
}
</style>