<template>
  <div>
  <div style="margin: 10px 0">
    <el-carousel height="450px" :interval="3000">
      <el-carousel-item v-for="item in imgs" :key="item">
        <img :src="item" alt="" style="width: 100%">
      </el-carousel-item>
    </el-carousel>
  </div>

  <div style="margin: 10px 0">
    <el-row :gutter="10">
      <el-col :span="6">
        <img :src="files[0].url" alt="" style="width: 100%">
      </el-col>
      <el-col :span="6">
        <img :src="files[1].url" alt="" style="width: 100%">
      </el-col>
      <el-col :span="6">
        <img :src="files[2].url" alt="" style="width: 100%">
      </el-col>
      <el-col :span="6">
        <img :src="files[3].url" alt="" style="width: 100%">
      </el-col>
    </el-row>
  </div>
  </div>
</template>
<script>
export default {
  name: "FrontHome",
  data(){
    return{
      imgs: [
        'https://img.alicdn.com/imgextra/i3/O1CN01Sf6dER1zbJ3uVQ0lE_!!6000000006732-0-tps-846-472.jpg',
        'https://gw.alicdn.com/imgextra/i4/O1CN01a71ilU1T2RCTvUXDY_!!6000000002324-0-tps-846-472.jpg',
        'https://img.alicdn.com/imgextra/i1/O1CN01YGUXOM1k5VKBPo5J3_!!6000000004632-2-tps-846-472.png'
      ],
      files: []
    }
  },
  created() {
    this.request.get("/echarts/file/front/all").then(res => {
      this.files = res.data.filter(v => v.type === 'jpg' || v.type === 'png')
    })
  },
  methods:{
  }
}
</script>