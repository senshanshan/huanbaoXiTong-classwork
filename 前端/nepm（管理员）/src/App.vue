<template>
  <router-view />
</template>

<script setup>
import axios from "axios";
import { provide } from "vue";
import { ElMessage } from "element-plus";

axios.defaults.baseURL = "http://localhost:8080/nepm/";

const myElMessage = (message, type) => {
  ElMessage({
    message: message,
    type: type,
    showClose: true
  });
};

provide("axios", axios);
provide("myElMessage", myElMessage);
</script>

<style>
*,
*::before,
*::after {
  box-sizing: border-box;
}

html,
body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

html,
body,
div,
span,
h1,
h2,
h3,
h4,
h5,
h6,
ul,
ol,
li,
p {
  margin: 0;
  padding: 0;
}

#app {
  width: 100%;
  min-height: 100%;
  font-family: "Microsoft YaHei", Arial, sans-serif;
}

img {
  max-width: 100%;
}

ul,
ol {
  list-style: none;
}
a {
  text-decoration: none;
}
</style>
