<template>
  <div @click="playMp3" >
    <img  src="../assets/mp3.jpeg"/>
     <audio ref="audioElm">
        <source   v-bind:src="srcURL" type="audio/mpeg">
    </audio>

  </div>
</template>
<script>
  export default {
    name:'player',
    props: ['parentMessage'],
    methods:{
      playMp3(){
          this.$refs.audioElm.play();
      }
    },
    data(){
      return{
        srcURL:"/audio/"+this.parentMessage+".mp3"
      }
    }

  }
</script>
<style>
  img{
    width:1rem;
    height:1rem;
    padding-top: 0.2rem;


  }
  img:hover{
    opacity: 0.5
  }
</style>