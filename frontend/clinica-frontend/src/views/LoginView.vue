<template>
  <div class="login-container">
    <div class="login-box">
      <img src="../assets/logo.png" alt="Logo Clínica Pé Perfeito" class="logo">
      <h1>Entrar</h1>
      <p class="subtitle">Faça seu login para acessar a área administrativa.</p>

      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="username">Email:</label>
          <input type="email" id="username" v-model="username" required>
        </div>
        <div class="form-group">
          <label for="password">Senha:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
        <button type="submit" class="login-button">Entrar</button>
      </form>
    </div>
  </div>
  <ErrorDialog 
  :isVisible="showErrorDialog" 
  :message="dialogMessage"
  @close= "showErrorDialog = false"
  />
</template>

<script>
import axios from 'axios';
import router from '../router';
import ErrorDialog from '../components/ErrorDialog.vue';
export default {
  name: 'LoginView',
  components: {
    ErrorDialog
  },
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '',
      showErrorDialog: false,
      dialogMessage: ''
    };
  },
  methods: {
    async handleLogin() {
      this.errorMessage = '';
      this.showErrorDialog = false;
      this.dialogMessage = '';
      try{
        const LOGIN_URL = 'http://localhost:8080/login'; // URL do backend

        // const auth = {
        //   username: this.username,
        //   password: this.password
        // }
        const params = new URLSearchParams();
        params.append('username', this.username);
        params.append('password', this.password);

        const response = await axios.post(LOGIN_URL, params, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        });

        if (response.status === 200 || response.status === 302) {
          console.log('Login bem-sucedido!', response); // A resposta pode ser o HTML de uma página pós-login
          // Redireciona para o dashboard ou página inicial após o login.
          // Agora o navegador terá o cookie de sessão do Spring Security.
          router.push({ name: 'Index' }); 
        } else {
          this.dialogMessage = 'Erro desconhecido no login.';
          this.showErrorDialog = true;
        }
        
      } catch (error) {
        console.error('Erro ao fazer login', error);
        if(error.response && error.response.status === 401) {
          this.errorMessage = 'Credenciais de login inválidas.';
          this.dialogMessage = 'ERRO 401';
          console.log("Mensagem de erro definida") // colocado para depuração
        }
        if(error.response && error.response.status === 403) {
          this.errorMessage = 'Acesso negado. Verifique suas credenciais.';
          this.dialogMessage = 'ERRO 403';
        }
        if(error.response && error.response.status === 404) {
          this.errorMessage = 'Usuário ou página não encontrada usuario;.';
          this.dialogMessage = 'ERRO 404';
        }
        if(error.response && error.response.status === 500) {
          this.errorMessage = 'Erro interno do servidor. Tente novamente mais tarde.';
          this.dialogMessage = 'ERRO 500';
        } else {
          this.errorMessage = 'Erro ao conectar com o servidor. Tente novamente mais tarde.';
          this.dialogMessage = 'Erro de conexão';
        }
        this.showErrorDialog = true;
      }
    }
  }
};
</script>

<style scoped>
/* Cores baseadas na logo: */

:root {
  --primary-orange: #FF6600; /* Laranja da pegada */
  --dark-orange: #CC5200;    /* Laranja do "Clínica Podologia" */
  --white: #FFFFFF;
  --dark-text: #2C3E50;      /* Cor de texto escura, como no App.vue */
  --light-grey: #f4f4f4;
  --border-grey: #ccc;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 60px); /* Ajusta para preencher a tela, menos o nav */
  background-color: var(--light-grey); /* Fundo suave */
  padding: 20px;
}

.login-box {
  background-color: var(--white);
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 100%;
  max-width: 400px;
}

.logo {
  max-width: 75%;
  /* margin-bottom: 25px; */
}

h1 {
  color: var(--dark-text);
  font-size: 2em;
  margin: 0px;
}

.subtitle {
  color: #666;
  font-size: 0.9em;
  margin-bottom: 30px;
}

.login-form .form-group {
  margin-bottom: 20px;
  text-align: left;
}

.login-form label {
  display: block;
  font-weight: bold;
  margin-bottom: 8px;
  color: var(--dark-text);
}

.login-form input[type="email"],
.login-form input[type="password"] {
  width: calc(100% - 20px); /* Ajusta padding */
  padding: 10px;
  border: 1px solid var(--border-grey);
  border-radius: 4px;
  font-size: 1em;
}

.login-button {
  width: 100%;
  padding: 12px;
  background-color: var(--primary-orange);
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 1.1em;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button :hover {
  background-color: var(--dark-orange);
}
</style>