import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import store from "./store";
import 'element-ui/lib/theme-chalk/index.css';
import request from "@/utils/request";
// 高德地图
import VueAMap from 'vue-amap'

Vue.config.productionTip = false
Vue.use(VueAMap)
Vue.use(ElementUI, { size: "mini" });

Vue.prototype.request=request
VueAMap.initAMapApiLoader({
  key: '2f14d376a856b0b3ad429c25004f82cb',
  plugin: [
    'AMap.Autocomplete', // 输入提示插件
    'AMap.PlaceSearch', // POI搜索插件
    'AMap.Scale', // 右下角缩略图插件 比例尺
    'AMap.OverView', // 地图鹰眼插件
    'AMap.ToolBar', // 地图工具条
    'AMap.Geolocation', // 定位控件，用来获取和展示用户主机所在的经纬度位置
    'AMap.MarkerClustered',
    'AMap.Geocoder',
    'AMap.CitySearch'
  ],
  uiVersion: '1.0',
  v: '1.4.15'
})
Vue.config.productionTip = false
// 开发环境
window._AMapSecurityConfig = {
  securityJsCode: '04cbdab9bccf76cfccd04adec3c70222',
}
// 生产环境 后台配置的安全密钥地址 详见官方配置文件
// window._AMapSecurityConfig = {
// serviceHost: `xxx/_AMapService`
//}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')