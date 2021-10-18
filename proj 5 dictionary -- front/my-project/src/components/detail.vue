<template>
   <div v-show="detailWordShow" class="pop"  @dblclick="disappeal()" >
          <div name="detailDiv">
              <p   class="detailWord"   >{{detail.word}}</p>
              <p class="detailP"  v-if="detail.synonym!==''" style="color:green;">SYNONYM:  {{detail.synonym}}</p>
              <p class="detailP"  v-if="detail.antonym!==''" style="color:brown;">ANTONYM:  {{detail.antonym}}</p>
              <p class="detailP"  v-if="detail.collocation!==''" style="color:blue;">COLLOCATION:   {{detail.collocation}}</p>
              <div name="detail" v-if="detail.sentences[0]!== undefined"  >
                <div style="display:flex">
                    <p class="detailSentence gray" >   {{detail.sentences[0].sentence_english}}</p><pronunciation_sentence1 :parentMessage="detail.sentences[0].word"></pronunciation_sentence1>
                </div>
                  
                <p class="detailSentence gray"  >   {{detail.sentences[0].sentence_chinese}}</p>
              </div>
              <div name="detail"  v-if="detail.sentences[1]!== undefined" >
                  <div style="display:flex">
                    <p class="detailSentence gray" >   {{detail.sentences[1].sentence_english}}</p><pronunciation_sentence2 :parentMessage="detail.sentences[1].word"></pronunciation_sentence2>
                  </div>
                  <p class="detailSentence gray" >   {{detail.sentences[1].sentence_chinese}}</p>
              </div>   
          </div> 
          <pic style="margin-right: auto; margin-left: 2rem" v-if="detail.hasPicture" :parentMessage="detail.word"></pic>
    </div>
</template>
<script>

import pronunciation_sentence1 from '../components/pronunciation_sentence1'
import pronunciation_sentence2 from '../components/pronunciation_sentence2'
import pic from '../components/picture'

  export default{
    name:'detailWord',
    props: {
      parentMessage:String
    },    
    components:{pronunciation_sentence1,pronunciation_sentence2,pic},
    methods:{
      getDetail(){
        var that=this;
        console.log("getDetail")
        this.axios.get('/api/getDetail', {
            params: {
              ID: that.parentMessage
            },
          })
          .then(function (response) {
            that.detail=response.data;
            console.log("detail=data")
          })
          .catch(function (error) {
            console.log(error);
          });  
      },
      disappeal()
      {
        console.log("closeDetail");
        this.detailWordShow=false;
      },
      show()
      {
        this.detailWordShow=true
      }
    },
    data(){
       return{
          detail:"",
    
          detailWordShow:false
          
        }
    },
     watch:{
      parentMessage(n){
         console.info('new value ', n);    
         this.getDetail();               
      }
    }
  }
</script>
<style>
  
  .pop {
      margin-top:0.2rem;
      margin-right:1.2rem;
      background-color: azure;
      display:flex;
      flex-direction:column;
      border: gray solid 2px;
      top:10rem;
      left:3rem;
      right:3rem;
      width: calc(100% - 6.5rem);
      position: fixed;
      z-index: 2;
      padding-left:0.2rem;display:flex;flex-direction:row;
    },
    div[name="detailDiv"]{
      margin-right:1rem;
      display:flex; 
      flex-direction: column;
    }

    .detailWord{
      font-size:1.3rem;
      margin-left: 2rem;
      text-align: left;
      height:2rem;
      margin-top:0.3rem;
      margin-bottom:0.3rem;
    }
    .detailP{
      margin-left:0.8rem;
      margin-block-start: 0.1em;
      margin-block-end: 0.1em;
    }
    .detailSentence{
        margin-left:0.8rem;
        margin-block-start: 0.1em;
        margin-block-end: 0.1em;
    }
    .gray
    {
      color:gray;    
    }
 
  
</style>