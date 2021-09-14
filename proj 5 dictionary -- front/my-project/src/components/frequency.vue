<template>
  <div  >
    <span name="fre" v-if="parentMessage==1"  >!!!</span>
    <span name="fre" v-if="parentMessage==2"  >!!</span>
    <span name="fre" v-if="parentMessage==3"  >!</span>


  </div>
</template>
<script>
  export default {
    name:'frequency',
    props: ['parentMessage'],
    methods:{
       
    },
    data(){
       return{

       }
    }

  }
</script>
<style>
  
</style>