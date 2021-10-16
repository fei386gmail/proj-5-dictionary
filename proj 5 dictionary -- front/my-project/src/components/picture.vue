<template>
  <div>
    <img name="picture" ref="picture" :src="srcURL"/>
  </div>

</template>
<script>
  export default{
    name:'picture',
    props: {
      parentMessage:String
    },
    computed:{
      srcURL(){
          return "/picture/"+this.parentMessage+".png"
      } 
    },
    watch: {
      parentMessage(n){
         console.info('new value ', n);
         this.$refs.picture.load();                     
      }
    }
  }
</script>
<style>
  img[name="picture"]{
    width:10rem;
    height:10rem;
  }
</style>