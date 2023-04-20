<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
  <div style="flex: 1; font-size: 20px">
    <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px"  @click="collapse"></span>

    <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
      <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
    </el-breadcrumb>

  </div>
  <el-dropdown style="width: 200px; cursor: pointer; text-align: right" >
    <div style="width: 150px">
      <img referrerpolicy="no-referrer" :src="user.avatarUrl" alt=""
           style="width: 30px; border-radius: 50%; position: relative; right: 5px; top: 10px;">
      <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="position: relative; margin-left: 5px; "></i>
    </div>

    <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
      <el-dropdown-item style="font-size: 14px; padding: 5px 0">
        <router-link to="/password">修改密码</router-link>
      </el-dropdown-item>
      <el-dropdown-item style="font-size: 14px; padding: 5px 0">
        <router-link to="/person">个人信息</router-link>
      </el-dropdown-item>
      <el-dropdown-item style="font-size: 14px; padding: 5px 0">
        <span style="text-decoration: none" @click="logout">退出</span>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
  </div>
</template>

<script>

export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user: Object
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  methods:{
    collapse(){
      this.$emit("asideCollapse")
    },
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    }
  },

  data(){
    return {

    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.ml-5{
  margin-left: 5px;
}
.mr-5{
  margin-right: 5px;
}
.pd-10{
  padding: 10px 0;
}
</style>