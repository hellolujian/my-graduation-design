<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
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
      })
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
