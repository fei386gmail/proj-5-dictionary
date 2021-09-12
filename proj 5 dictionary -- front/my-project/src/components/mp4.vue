<template>
  <div @click="playMp4" >
    <img name="mp4"  src="../assets/mp4.jpeg"/>
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
      playMp4(){
          this.$refs.audioElm.play();
      }
    },
    data(){
      return{
        srcURL:"/audio2/"+this.parentMessage+".mp3"
      }
    }

  }
</script>
<style>
  img[name="mp4"]
  {
    width:1rem;
    height:0.8rem;
    padding-top: 0rem;
  }
  img:hover{
    opacity: 0.5
  }
</style>