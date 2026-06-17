<template>
  <div class="wrapper">
    <header>
      <img src="../assets/header.png" alt="header" />
    </header>
    <h1>东软环保公众监督平台-网格员端</h1>
    <div class="login">
      <div class="login-input">
        <i class="fa fa-user-o"></i>
        <input
          type="text"
          v-model="gridMemberForm.gmCode"
          placeholder="请输入登录编码"
        />
      </div>
      <div class="login-input">
        <i class="fa fa-key"></i>
        <input
          type="password"
          v-model="gridMemberForm.password"
          placeholder="请输入登录密码"
        />
      </div>
      <div class="btn" @click="login">登录</div>
    </div>
    <footer>
      <img src="../assets/bottom.png" alt="footer" />
    </footer>
  </div>
</template>

<script setup>
import { inject, ref } from "vue";
import { useRouter } from "vue-router";

const axios = inject("axios");
const router = useRouter();

const gridMemberForm = ref({
  gmCode: "",
  password: ""
});

const login = () => {
  if (!gridMemberForm.value.gmCode) {
    alert("请输入登录编码");
    return;
  }
  if (!gridMemberForm.value.password) {
    alert("请输入登录密码");
    return;
  }

  axios
    .post("gridMember/getGridMemberByCodeByPass", gridMemberForm.value)
    .then((response) => {
      if (response.data) {
        sessionStorage.setItem("gridMember", JSON.stringify(response.data));
        router.push("/feedbackList");
      } else {
        alert("登录编码或密码错误，请重新输入");
      }
    })
    .catch((error) => {
      console.error("登录请求出错:", error);
      alert("网络错误，请稍后重试");
    });
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  min-height: 100vh;
  position: relative;
  padding-bottom: 100px;
  box-sizing: border-box;
}

header {
  width: 100%;
}

header img {
  display: block;
  width: 100%;
  height: 200px;
  object-fit: cover;
}

h1 {
  width: 78vw;
  max-width: 375px;
  margin: 0 auto;
  text-align: center;
  font-size: clamp(16px, 5.4vw, 24px);
  color: #6ab9f2;
  box-sizing: border-box;
  padding: 6vw 0 1vw;
  border-bottom: solid 2px #6ab9f2;
}

.login {
  width: 80vw;
  max-width: 400px;
  margin: 5vw auto 0;
}

.login .login-input {
  width: 100%;
  height: 12vw;
  max-height: 52px;
  background-image: linear-gradient(90deg, #f1f1f1, #e1e1e1);
  border-radius: 6vw;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  padding-left: 6vw;
  margin-top: 8vw;
}

.login .login-input .fa {
  color: #6ab9f2;
  font-size: clamp(16px, 5vw, 24px);
  margin-right: 2vw;
}

.login .login-input input {
  border: none;
  outline: none;
  background: transparent;
  width: 100%;
  font-size: clamp(14px, 4vw, 18px);
}

.login .btn {
  width: 100%;
  height: 12vw;
  max-height: 52px;
  margin-top: 8vw;
  background-image: linear-gradient(90deg, #67c9ef, #7394f1);
  border-radius: 6vw;
  font-size: clamp(16px, 5vw, 22px);
  color: #fff;
  text-align: center;
  line-height: 12vw;
  font-weight: 500;
  letter-spacing: 1vw;
  user-select: none;
  cursor: pointer;
}

footer {
  width: 100%;
  position: absolute;
  left: 0;
  bottom: 0;
}

footer img {
  display: block;
  width: 100%;
  height: 100px;
  object-fit: cover;
}
</style>
