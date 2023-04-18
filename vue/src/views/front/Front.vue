<template>
  <div>
    <!--    头部-->
    <div style="display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #ccc">
      <div style="width: 200px; text-align: center">(logo)欢迎来到xx系统</div>
      <div style="flex: 1">
        <!--        导航菜单-->
        <ul style="list-style: none; background-color: rgb(64,224,208)">
          <li class="item">菜单1</li>
          <li class="item">菜单2</li>
          <li class="item">菜单3</li>
        </ul>
      </div>
      <div style="width: 200px">昵称</div>
    </div>

    <router-view/>
  </div>
</template>
<script>
export default {
  name: "Front",
  data() {
    return {}
  },
  created() {
  },
  methods: {}
}
</script>
<style>
.item {
  display: inline-block;
  width: 100px;
}
</style>