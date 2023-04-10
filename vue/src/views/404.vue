<template>
  <div style="text-align: center">
    <h1 style="font-size: 50px">404</h1>
    <h2>没有找到网页</h2>
    <h2>小猫路由官提醒你不要乱输地址</h2>
    <div>
      <img :src="cat" alt="小猫拒绝" />
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFound",
  data(){
    return {
      cat: require('../resources/xiaomao.gif')
    }
  }
}

</script>
<style>

</style>