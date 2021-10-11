<template>
  <div id="main" >
    <div id="search">
      <input name="word" v-model="wordInput" v-on:keyup.enter="getData()" >
      <button @click="getData()">search</button><br>

      <div style="padding-left:0.2rem" class="pop" v-if="showModal" @dblclick="showModal=false" >
          <span name="detail"  style="font-size:1.3rem" >{{detail.word}}</span>
          <span name="detail" v-if="detail.synonym!==''" style="color:green">SYNONYM:  {{detail.synonym}}</span>
          <span name="detail" v-if="detail.antonym!==''" style="color:brown">ANTONYM:  {{detail.antonym}}</span>
          <span name="detail" v-if="detail.collocation!==''" style="color:blue">COLLOCATION:   {{detail.collocation}}</span>
          <div >
            <tr name="detail" v-if="detail.sentences[0]!== undefined"  >
                <div style="display:flex">
                    <span name="detail" >   {{detail.sentences[0].sentence_english}}</span><pronunciation_sentence1 :parentMessage="detail.sentences[0].word"></pronunciation_sentence1>
                </div>
              
                <span name="chi_sentence_span"  >   {{detail.sentences[0].sentence_chinese}}</span>
            </tr>
             <tr name="detail"  v-if="detail.sentences[1]!== undefined" >
                <div style="display:flex">
                    <span name="detail" >   {{detail.sentences[1].sentence_english}}</span><pronunciation_sentence2 :parentMessage="detail.sentences[1].word"></pronunciation_sentence2>
                </div>
              
                <span name="chi_sentence_span" >   {{detail.sentences[1].sentence_chinese}}</span>
            </tr>
          </div>

      </div>
    </div>
    
 
    <table>
      <thead>
      
      </thead>
      <tbody>
        <tr v-for="(word) in data" v-bind:key="word.word" @dblclick="getDetail(word.word)" >
          
          <td style="display:flex">
            <span name="ss"   >{{word.word}}</span>
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
import pronunciation_sentence1 from '../components/pronunciation_sentence1'
import pronunciation_sentence2 from '../components/pronunciation_sentence2'

export default {
  name: 'dictionary',
  components:{player,player2,frequency,pronunciation_sentence1,pronunciation_sentence2},
   data() {
        return{
          data: "",
          detail:"",
          showModal:false,
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
    getDetail(word){
      var that=this;
      this.showModal=true;
      this.axios.get('/api/getDetail', {
            params: {
              ID: word
            },
          })
          .then(function (response) {
            that.detail=response.data;
          })
          .catch(function (error) {
            console.log(error);
          });  

    }
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
      margin-left:3rem;
      padding:0;
    }
    tr[name="detail"]
    {
      
    }
    td{
      height: 0.5rem;
      margin-left:3rem;
      text-align: left;
      padding:0
    }

    span[name="detail"]
    {
      margin-left:0rem;
      margin-right:0.1rem;
      text-align:left;
     
    }
    span[name="chi_sentence_span"]
    {
      color:gray;
      display:block;
      text-align:left;
      
    }
    p[name="ss"]{
      margin-left:1rem;
      margin-top:0.1rem;
      margin-bottom:0.1rem;

    }
      p[name="tt"]{
      margin-left:1rem;
      margin-top:0.1rem;
      margin-bottom:0.1rem;
    }

    .pop {
      margin-top:0.2rem;
      background-color: azure;
      display:flex;
      flex-direction:column;
      border: gray solid 2px;
      top:10rem;
      left:3rem;
      width: calc(100% - 6rem);
      position: fixed;
      z-index: 2;

    }
   
    td[name="frequency"]
    {
      width:1rem;
    }
</style>
