<template>
  <div v-if="isVisible" class="dialog-overlay">
    <div class="dialog-box">
      <div class="dialog-header">
        <h2>Erro</h2>
        <button @click="closeDialog" class="close-button">×</button>
      </div>
      <div class="dialog-content">
        <p>{{ message }}</p>
      </div>
      <div class="dialog-footer">
        <button @click="closeDialog" class="ok-button">OK</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ErrorDialog',
  props: {
    // Propriedade para controlar a visibilidade da dialog
    isVisible: {
      type: Boolean,
      default: false
    },
    // Propriedade para a mensagem a ser exibida
    message: {
      type: String,
      default: 'Ocorreu um erro inesperado.'
    }
  },
  methods: {
    closeDialog() {
      // Emite um evento para o componente pai para fechar a dialog
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* Fundo semi-transparente */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* Garante que fique acima de outros elementos */
}

.dialog-box {
  background: white;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.25);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.dialog-header h2 {
  margin: 0;
  color: #d9534f; /* Cor de erro */
  font-size: 1.5em;
}

.close-button {
  background: none;
  border: none;
  font-size: 1.8em;
  color: #888;
  cursor: pointer;
  line-height: 1;
}

.close-button:hover {
  color: #333;
}

.dialog-content p {
  font-size: 1.1em;
  color: #555;
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}

.ok-button {
  background-color: #007bff; /* Cor primária */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1em;
  transition: background-color 0.3s ease;
}

.ok-button:hover {
  background-color: #0056b3;
}
</style>