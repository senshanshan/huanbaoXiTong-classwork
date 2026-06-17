import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import "font-awesome/css/font-awesome.min.css";

router.beforeEach(function (to, from, next) {
  const gridMember = sessionStorage.getItem("gridMember");
  if (to.path === "/feedbackList" || to.path === "/feedbackInfo") {
    if (gridMember == null) {
      next("/login");
      return;
    }
  }
  next();
});

createApp(App).use(router).mount("#app");
