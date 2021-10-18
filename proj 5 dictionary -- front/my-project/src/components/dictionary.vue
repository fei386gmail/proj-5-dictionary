<template>
  <div id="main" >
    <div id="search">
      <input name="word" v-model="wordInput" v-on:keyup.enter="getData()" >
      <button @click="getData()">search</button><br>

      <detailWord  ref="child"  :parentMessage="selectedWord"  ></detailWord>
      
    </div>
    
 
    <table>
      <thead>
      
      </thead>
      <tbody>
        <tr v-for="(word) in data" v-bind:key="word.word" @dblclick="transfer(word.word)" >
          
          <td style="display:flex">
            <span   class="word"   >{{word.word}}</span>
          </td>
          <td name="icon">
              <span v-if="word.pronunciation1"><player :parentMessage="word.word"></player></span>
          </td>
          <td name="icon">
              <span v-if="word.pronunciation2"><player2 :parentMessage="word.word"></player2></span>
        
          </td>
          <td>
            <p name="tt">{{word.translation}}</p>
          </td>
          <td name="frequency">
              <span   v-if="word.frequency<4"><frequency :parentMessage="word.frequency"></frequency></span>
          </td>
         
        </tr>
      </tbody>
    </table>    

  </div>
</template>

 
 <script>
import  player from '../components/mp3'
import  player2 from '../components/mp4'
import frequency from '../components/frequency'
import detailWord from '../components/detail'

export default {
  name: 'dictionary',
  components:{player,player2,frequency,detailWord},
   data() {
        return{
          data: "",
          selectedWord:"",
          wordInput:"",
          }        
        },
  methods: {
    getData() {
      var that=this;
        console.log("getData");
        this.axios.get('/api/getData', {
            params: {
              ID: this.wordInput
            },
          })
          .then(function (response) {
            that.data=response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    transfer(word){
      this.selectedWord=word;
      this.$refs.child.show();
      console.log("transfer:"+word);
    },

  
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  div[id="main"]
  {
    display:flex;
    flex-direction:column;
  }
  div[id="search"]{
              margin-left: 2rem;
              margin-right: auto;
    }
    input{
        border: black solid 2px;
        height: 2rem;
        width: 15rem;
        margin-left: 2rem;
        margin-top: 1rem;
    }
    button{
        height: 2.3rem;
        width: 6.4rem;
        margin-left: 0.1rem;
    }
    
    
    table{
      border: gray solid 1px;
      margin-left: 2rem;
      margin-right:2rem;
      margin-top:0.1rem;
    }
    tr{
      height: 0.5rem;
      margin-left:1rem;
      padding:0;
    }
   
    td{
      height: 0.5rem;
      margin-left:0rem;
      text-align: left;
      padding:0
    }

  
    .word{
      margin-left:1rem;
      margin-top:0.1rem;
      margin-bottom:0.1rem;

    }
    p[name="tt"]{
      margin-left:1rem;
      margin-top:0.1rem;
      margin-bottom:0.1rem;
    }
 
   
    td[name="frequency"]
    {
      width:1rem;
    }
    
</style>
