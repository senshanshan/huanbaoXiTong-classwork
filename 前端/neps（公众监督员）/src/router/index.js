import { createRouter, createWebHashHistory } from "vue-router";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import SelectGrid from "../views/SelectGrid.vue";
import SelectAqi from "../views/SelectAqi.vue";
import FeedbackList from "../views/FeedbackList.vue";

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/login",
    name: "LoginAlias",
    component: Login,
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
  {
    path: "/selectGrid", // 统一大写G，和守卫匹配
    name: "SelectGrid",
    component: SelectGrid,
  },
  {
    path: "/selectAqi",
    name: "SelectAqi",
    component: SelectAqi,
  },
  // 补充反馈列表路由
  {
    path: "/feedbackList",
    name: "FeedbackList",
    component: FeedbackList,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
