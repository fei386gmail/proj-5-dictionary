<template>
  <div @click="play" >
    <img name="mp5"  src="../assets/mp4.jpeg"/>
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
      play(){
          this.$refs.audioElm.play();
      }
    },
     computed:{
      srcURL(){
          return "/audioSentence2/"+this.parentMessage+".mp3"
      } 
    },
    watch: {
      parentMessage(n){
         console.info('new value ', n);
         this.$refs.audioElm.load();                     
      }
    }

  }
</script>
<style>
  img[name="mp5"]
  {
    width:1rem;
    height:0.8rem;
    padding-top: 0rem;
  
  }
  img:hover{
    opacity: 0.3
  }
</style>