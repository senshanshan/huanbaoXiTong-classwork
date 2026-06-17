<template>
  <router-view />
</template>

<script setup>
import axios from "axios";
import { provide } from "vue";

axios.defaults.baseURL = "http://localhost:8080/nepm/";
provide("axios", axios);
</script>

<style>
/********************** CSS 全局重置 *********************/
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

#app {
  width: 100%;
  min-height: 100%;
  font-family: "Microsoft YaHei", sans-serif;
}

img {
  max-width: 100%;
}

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

ul,
ol {
  list-style: none;
}

a {
  text-decoration: none;
}
</style>
