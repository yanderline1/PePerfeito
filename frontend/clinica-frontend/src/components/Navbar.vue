<template>
  <nav class="navbar-right">
    <div class="navbar-trigger">
      <span class="trigger-icon">☰</span>
    </div>
    <ul class="nav-links">
      <li><router-link to="/home">Home</router-link></li>
      <li><router-link to="/clientes">Clientes</router-link></li>
      <li><router-link to="/agendamentos">Agendamentos</router-link></li>
      <li><router-link to="/horarios">Horários</router-link></li>
      <li><router-link to="/usuarios">Usuários</router-link></li>
      <li><a @click="handleLogout" href="#">Sair</a></li>
    </ul>
  </nav>
</template>

<script>
import router from '../router'; // Importa a instância do router para navegação

export default {
  name: 'NavBar',
  methods: {
    handleLogout() {
      // Remover o token JWT do localStorage
      localStorage.removeItem('jwt_token');
      // Redirecionar para a página de login
      router.push({ name: 'Login' });
    }
  }
};
</script>

<style scoped>
/* Cores baseadas na sua logo para consistência */
:root {
  --primary-orange: #FF6600;
  --dark-orange: #CC5200;
  --white: #FFFFFF;
  --dark-text: #2C3E50;
  --light-grey: #f4f4f4;
  --link-hover-color: #eee;
}

.navbar-right {
  position: fixed; /* Fixa a navbar na tela */
  top: 0;
  right: -300px; /* Inicialmente oculta a navbar fora da tela (largura da navbar - trigger) */
  width: 250px; /* Largura total da navbar */
  height: 100vh; /* Ocupa a altura total da viewport */
  background-color: #2C3E50; /* Cor de fundo escura, como na sua logo */
  box-shadow: -5px 0 15px rgba(0, 0, 0, 0.2); /* Sombra para dar profundidade */
  transition: right 0.3s ease-out; /* Suaviza a transição quando ela aparece/desaparece */
  z-index: 1000; /* Garante que fique acima de outros elementos */
  display: flex;
  flex-direction: column;
  padding-top: 20px; /* Espaço do topo */
}

/* O "trigger" é a parte visível da navbar quando ela está oculta */
.navbar-trigger {
  position: absolute;
  left: -50px; /* Parte que fica visível, como uma aba */
  top: 50%;
  transform: translateY(-50%);
  width: 50px; /* Largura do trigger */
  height: 60px; /* Altura do trigger */
  background-color: #2C3E50;
  border-radius: 8px 0 0 8px; /* Borda arredondada apenas na lateral visível */
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
}

.trigger-icon {
  color: #FFFFFF;
  font-size: 1.5em;
  font-weight: bold;
}


/* Estado hover: quando o mouse passa sobre a navbar, ela desliza para dentro */
.navbar-right:hover {
  right: 0; /* Desliza a navbar para a posição visível */
}

.nav-links {
  list-style: none; /* Remove marcadores de lista */
  padding: 0;
  margin: 0;
  flex-grow: 1; /* Faz com que ocupe o espaço restante */
  overflow-y: auto; /* Adiciona scroll se houver muitos links */
}

.nav-links li {
  margin-bottom: 10px;
}

.nav-links a {
  display: block; /* O link ocupa toda a largura do item da lista */
  color: #FFFFFF; /* Cor do texto do link */
  padding: 12px 20px; /* Preenchimento interno */
  text-decoration: none; /* Remove sublinhado */
  font-size: 1.1em;
  transition: background-color 0.3s ease; /* Suaviza a transição do hover */
}

.nav-links a:hover,
.nav-links a.router-link-exact-active { /* Estilo para o link ativo e hover */
  background-color: var(--primary-orange); /* Laranja da logo no hover/ativo */
  border-left: 5px solid #FFFFFF; /* Adiciona uma borda lateral para destaque */
}
</style>