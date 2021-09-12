<template>
  <div id="app">
  
    <dictionary></dictionary>
  </div>
</template>

<script>
import dictionary from './components/dictionary.vue'

export default {
  name: 'App',
  components: {
    dictionary
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
