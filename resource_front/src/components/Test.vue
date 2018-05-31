<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <audio 
      ref="notice"
      id="notice2"
      src="/static/mp3/song.mp3">
    </audio>
    <div style="width:100px;height:100px;border:1px solid red;"></div>
  </div>
</template>

<script>
import {getRequest} from '../utils/api'

export default {
  name: 'Test',
  data () {
    return {
      msg: ''
    }
  },
  mounted() {
    var _this = this;
      getRequest("/hello").then(resp=> {
        _this.msg = resp.data;
      });
      this.$refs.notice.play();
      console.log(this.$refs.notice)
      
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
