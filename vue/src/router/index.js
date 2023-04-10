import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [

  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '*',
    name: '404',
    component: () => import('../views/404.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//刷新页面会重置路由
export const setRouters = () => {
  const storeMenus = localStorage.getItem("menus");
  if(storeMenus){
    //拼装动态路由
    const manageRoute = { path: '/',name: 'Manage' , component: () => import('../views/Manage.vue'), redirect: "/home", children: [] }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item =>{
      if (item.path){ //当path不为空才设置路由
        let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
        manageRoute.children.push(itemMenu)
      }
      else if(item.children.length){
        item.children.forEach(item => {
          if(item.path){
            let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })

    //获取当前路由对象名称数组
    const currentRouterNames = router.getRoutes().map(v => v.name)
    if (!currentRouterNames.includes('Manage')){
      //讲动态路由添加到现有的路由对象中
      router.addRoute(manageRoute)
    }
  }
}
//如重置再set一次路由
setRouters()
// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})

export default router