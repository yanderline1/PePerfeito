<template>
  <div class="splash-container">
    <div class="splash-box">
      <img src="../assets/logo.png" alt="Logo Clínica Pé Perfeito" class="logo-splash">
      <h1>Bem-vindo à Clínica Pé Perfeito</h1>
      <p class="tagline">Cuidando da saúde e bem-estar dos seus pés.</p>
      <button @click="goToLogin" class="enter-button">Entrar</button>
    </div>
  </div>
</template>

<script>
import router from '../router'; // Importa a instância do router para navegação

export default {
  name: 'SplashView',
  methods: {
    goToLogin() {
      // Método para navegar para a rota de login
      router.push({ name: 'Login' });
    }
  }
};
</script>

<style scoped>
/* Variáveis de cores (reutilizadas do LoginView.vue para consistência) */
:root {
  --primary-orange: #FF6600; /* Laranja da pegada */
  --dark-orange: #CC5200;    /* Laranja do "Clínica Podologia" */
  --white: #FFFFFF;
  --dark-text: #2C3E50;      /* Cor de texto escura */
  --light-grey: #f4f4f4;
  /* --border-grey: #ccc; (não usado diretamente aqui, mas pode ser útil) */
}

.splash-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh; /* Ocupa a altura total da viewport */
  background-color: #FF6600; /* Fundo suave */
  padding: 20px;
}

.splash-box {
  background-color: white;
  padding: 50px;
  border-radius: 10px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15); /* Sombra mais pronunciada */
  text-align: center;
  width: 100%;
  max-width: 500px; /* Um pouco maior que a caixa de login, para destaque */
  transform: translateY(0); /* Estado inicial para a animação */
  opacity: 1; /* Estado inicial para a animação */
  animation: fadeIn 1s ease-out; /* Animação de fade-in */
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.logo-splash {
  max-width: 80%; /* Logo um pouco maior para a apresentação */
  height: auto;
  margin-bottom: 0px;
}

h1 {
  color: var(--dark-text);
  font-size: 2.5em; /* Título maior */
  margin-bottom: 10px;
  margin-top: 0px;
}

.tagline {
  color: #555;
  font-size: 1.2em; /* Texto um pouco maior */
  margin-bottom: 20px;
}

.enter-button {
  width: 70%; /* Botão com largura definida */
  padding: 15px;
  background-color: #FF6600;
  color: #FFFFFF;
  border: none;
  border-radius: 5px;
  font-size: 1.3em;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.enter-button:hover {
  background-color: #CC5200;
  transform: translateY(-2px); /* Efeito sutil ao passar o mouse */
}
</style>