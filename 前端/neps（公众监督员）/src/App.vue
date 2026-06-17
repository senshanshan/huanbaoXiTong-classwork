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
*,
*::before,
*::after {
  box-sizing: border-box;
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

html,
body,
#app {
  width: 100%;
  min-height: 100%;
  font-family: "Microsoft YaHei", sans-serif;
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

.wrapper {
  width: 100%;
  min-height: 100vh;
  background-color: #cfe8e9;
}

.header-box {
  width: 100%;
}

.header-box header {
  width: 100%;
  height: 15.7vw;
  background-color: #fff;
  color: #555;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 3.6vw;
}

.header-box .top-ban {
  width: 100%;
  height: 15.7vw;
}

.header-box img {
  display: block;
  width: 100%;
}

.header-box i {
  min-width: 8vw;
  font-size: 8vw;
}

.header-box p {
  font-size: 4.6vw;
  font-weight: 600;
}

.table-form {
  width: 86vw;
  margin: 5vw auto 0;
  border-collapse: collapse;
  font-size: 4.2vw;
}

.table-form tr td {
  min-height: 12vw;
  padding: 2vw 0;
}

.table-form input,
.table-form select,
.table-form textarea {
  background-color: #cfe8e9;
  border: none;
  outline: none;
}

.table-form tr td:first-child {
  width: 28vw;
  color: #777;
  font-weight: 700;
}

.btn {
  width: 100%;
  height: 12vw;
  background-color: #70b0bc;
  margin-top: 5vw;
  border-radius: 2vw;
  color: #fff;
  font-size: 5vw;
  text-align: center;
  line-height: 12vw;
  letter-spacing: 1vw;
  font-weight: 500;
  user-select: none;
  cursor: pointer;
}
</style>
