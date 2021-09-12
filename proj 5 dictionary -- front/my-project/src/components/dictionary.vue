<template>
  <div id="main" >
    <div id="search">
      <input name="word" v-model="w" v-on:keyup.enter="getData()" >
      <button @click="getData()">search</button><br>

      <div class="pop" v-if="showModal" @dblclick="showModal=false" >
          <span name="detail"  style="font-size:1.3rem" >{{detail.word}}</span>
          <span name="detail" v-if="detail.synonym.length>0" style="color:green">SYNONYM:  {{detail.synonym}}</span>
          <span name="detail" v-if="detail.antonym.length>0" style="color:brown">ANTONYM:  {{detail.antonym}}</span>
          <span name="detail" v-if="detail.collocation.length>0" style="color:blue">COLLOCATION:   {{detail.collocation}}</span>
      </div>
    </div>
    
 
    <table>
      <thead>
      
      </thead>
      <tbody>
        <tr v-for="(word) in data" v-bind:key="word" @dblclick="getDetail(word.word)" >
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
         
        </tr>
      </tbody>
    </table>    

  </div>
</template>

 
 <script>
import  player from '../components/mp3'
import  player2 from '../components/mp4'
export default {
  name: 'dictionary',
  components:{player,player2},
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
        this.data="...";
        console.log("click");
        // http://localhost:18082/api/getData
        this.axios.get('/api/getData', {
            params: {
              ID: this.w
            },
          })
          .then(function (response) {
          
            that.data=response.data;
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    getDetail(wordDetail){
      var that=this;
      this.showModal=true;
        this.detail="...";
        this.axios.get('/api/getDetail', {
            params: {
              ID: wordDetail
            },
          })
          .then(function (response) {
          
            that.detail=response.data;
            console.log(response);
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
    td{
      height: 0.5rem;
      margin-left:3rem;
      text-align: left;
      padding:0
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
    span[name="detail"]
    {
      margin-left:4rem;
      margin-right:auto;
      text-align:left;
    }
</style>
