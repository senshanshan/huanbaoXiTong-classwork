import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import "font-awesome/css/font-awesome.min.css";

router.beforeEach(function (to, from, next) {
  const supervisor = sessionStorage.getItem("supervisor");
  if (
    to.path === "/selectGrid" ||
    to.path === "/selectAqi" ||
    to.path === "/feedbackList"
  ) {
    if (supervisor == null) {
      next("/");
      return;
    }
  }
  next();
});

createApp(App).use(router).mount("#app");
