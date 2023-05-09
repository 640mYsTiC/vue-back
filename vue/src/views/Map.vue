<template>
<div>
  <div id="container" style="width: 100%; height: calc(100vh - 100px)"></div>
</div>
</template>

<script>

var content = [
  // 信息窗体的内容
  "<div style='font-size: 14px; width: 200px; height: 50px'>芝士我的窗体</div>"
];
export default {
  name: "Map",
  data() {
    return {}
  },
  created() {
  },
  mounted() {

    // 创建地图实例
    const map = new AMap.Map('container', {
      viewMode: '2D',  // 默认使用 2D 模式
      zoom:11,  //初始化地图层级
      center: [106.311011,29.621543]  //初始化地图中心点
    });
    // 创建 infoWindow 实例
    var infoWindow = new AMap.InfoWindow({
      anchor: 'top-right',
      content: content.join("<br>")  //传入 dom 对象，或者 html 字符串
    });
    //创建点击事件
    let clickHandler = function (e) {
      console.log(e.lnglat.getLng()+','+e.lnglat.getLat())
      // 打开信息窗体
      infoWindow.open(map, map.getCenter());
    }

    let marker = new AMap.Marker({
      position: new AMap.LngLat(106.303211,29.615303), // Marker经纬度,
      title: '重师和风苑'
    });
    //绑定事件
    marker.on('click', clickHandler);
    //将标记的点添加到已有实例
    map.add(marker);
    //获取用户所在城市信息
    function showCityInfo() {
      //实例化城市查询类
      var citysearch = new AMap.CitySearch();
      //自动获取用户IP，返回当前城市
      citysearch.getLocalCity(function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
          if (result && result.city && result.bounds) {
            var cityinfo = result.city;
            var citybounds = result.bounds;
            document.getElementById('info').innerHTML = '您当前所在城市：'+cityinfo;
            //地图显示当前城市
            map.setBounds(citybounds);
          }
        } else {
          document.getElementById('info').innerHTML = result.info;
        }
      });
    }
    showCityInfo()


  },
  methods(){

  }
}
</script>

<style scoped>

</style>