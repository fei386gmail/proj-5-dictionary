<template>
  <div>
    <img name="picture" ref="pictureRef" :src="srcURL"/>
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
      parentMessage(){
     
      }
    }
  }
</script>
<style>
  img[name="picture"]{
    width:10rem;
    height:10rem;
    margin-right:0.5rem;
    margin-top:0.5rem;
  }
  img:hover{
    
  }
</style>