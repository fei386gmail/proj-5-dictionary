<template>
  <div id="main" >
    <div id="search" >
           <input name="word" v-model="wordInput" v-on:keyup.enter="getData()" >
          <button name="searchButton" @click="getData()">search<input name="recallCheck" type="checkbox" v-model="recallCheck"/></button>
          <label name="gaopin">Highly Used</label><input name="highFrequentCheck" v-model="highFrequentCheck" type="checkbox" /> 
    </div>
    
 
    <table>
      <thead>
      
      </thead>
      <tbody>
        <tr v-for="(word) in data" v-bind:key="word.word" @dblclick="transfer(word.word)" v-bind:class=" {'wordRemember':word.remember==true}"  >
          
          <td  name="tdWord">
            <span class="word"     >{{word.word}}</span>
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



    <detailWord  ref="child"  :parentMessage="selectedWord"  @getRememberStatus="showRememberStatus" ></detailWord>

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
          highFrequentCheck:false,
          recallCheck:false
          }        
        },
  methods: {
    getData() {
      var that=this;
        console.log("getData");
        this.axios.get('/api/getData', {
            params: {
              ID: this.wordInput,
              highFrequentCheck:this.highFrequentCheck,
              recallCheck:this.recallCheck

            },
          })
          .then(function (response) {
            that.data=response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    showRememberStatus(status){
        console.log("showRememberStatus:"+status);
       
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
      display:flex;
      width:auto;
    }
  div[id="title"]{
     display:flex;
      align-items: center;
      width:auto;

  }
    input{
        border: black solid 2px;
        height: 2rem;
        width: 15rem;
        margin-left:3rem;
       
    }
    button[name="searchButton"]
    {
        display: flex;
        flex-direction: row;
        font-size: 110%;
        align-items: center;
        padding-left: 1.3rem;
        padding-right: 0rem;
    }
    label[name="gaopin"]{
      align-self:center;
      margin-left:auto;
      width:2.3rem;
      font-size: 40%;
    }
    input[name="highFrequentCheck"]{
        height:1rem;
        width:1rem;
        align-self:center;
        margin-right:2.5rem;
        margin-left:0.1rem;
    }
    input[name="recallCheck"]
    {
        height: 0.5rem;
        width: 0.5rem;
        margin-left: auto;
        margin-right: 0;
        align-self: flex-end;
        margin-bottom: 0;
    }
    button{
        height: 2.3rem;
        width: 6.4rem;
        margin-left: 0.15rem;
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
      border-color:gray;
      border-style:solid;
      border-width:1px
          }
    td{
      height: 0.5rem;
      margin-left:0rem;
      text-align: left;
      padding:0
    }
    td[name="tdWord"]
    {
     
       width:10rem;
    }
    td[name="frequency"]{
      margin-right: 0.5rem;
      align-self: right;
    }
  
    .word{
      margin-left:1rem;
      margin-top:0.1rem;
      margin-bottom:0.1rem;
    }
    .wordRemember{
      margin-left:1rem;
      margin-top:0.1rem;
      margin-bottom:0.1rem;
      color:brown;
    
    
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
