<template>
  <div id="app">
    <NavBar />
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
</script>

<style scoped>
.main-content {
  min-height: calc(100vh - 60px);
  padding: 20px;
  background-color: #f5f7fa;
}
</style>
