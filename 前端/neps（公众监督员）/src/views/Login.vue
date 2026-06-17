<template>
  <div class="wrapper">
    <header>
      <img src="../assets/bgtop.png" alt="顶部背景图" />
    </header>

    <h1>东软环保公众监督系统</h1>
    <div class="title">
      <p></p>
      <p>公众监督员端</p>
      <p></p>
    </div>

    <section>
      <div class="input-box">
        <i class="fa fa-user-o"></i>
        <input
          type="text"
          v-model="supervisorForm.telId"
          placeholder="输入手机号"
        />
      </div>
      <div class="input-box">
        <i class="fa fa-lock"></i>
        <input
          type="password"
          v-model="supervisorForm.password"
          placeholder="输入登录密码"
        />
      </div>
      <div class="reg-box">
        <p @click="toRegister">注册</p>
        <p>忘记密码?</p>
      </div>
      <div class="button-box" @click="login">登录</div>
    </section>
  </div>
</template>

<script setup>
import { inject, reactive } from "vue";
import { useRouter } from "vue-router";
import { setSessionStorage } from "../common.js";

const axios = inject("axios");
const router = useRouter();

const supervisorForm = reactive({
  telId: "",
  password: "",
});

const toRegister = () => {
  router.push("/register");
};

const login = async () => {
  if (!supervisorForm.telId) {
    alert("请输入手机号");
    return;
  }
  if (!/^1[3-9]\d{9}$/.test(supervisorForm.telId)) {
    alert("请输入合法的11位手机号");
    return;
  }
  if (!supervisorForm.password) {
    alert("请输入密码");
    return;
  }

  try {
    const res = await axios.post("supervisor/getSupervisorByIdByPass", {
      telId: supervisorForm.telId,
      password: supervisorForm.password,
    });
    if (res.data) {
      alert("登录成功");
      setSessionStorage("supervisor", res.data);
      router.push("/selectGrid");
    } else {
      alert("账号或密码错误");
    }
  } catch (err) {
    console.error("登录请求异常", err);
    alert("网络异常，请稍后重试");
  }
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  min-height: 100vh;
  background-color: #cfe8e9;
  overflow-x: hidden;
}

header {
  width: 100%;
}

header img {
  display: block;
  width: 100%;
}

h1 {
  text-align: center;
  color: #46a34f;
  font-size: 7vw;
  font-weight: 700;
  margin: 2vw 0;
}

.title {
  width: 70%;
  margin: 3vw auto 6vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #46a34f;
  font-size: 4vw;
}

.title p:first-child,
.title p:last-child {
  width: 20vw;
  height: 1px;
  background-color: #68c56f;
}

section {
  width: 86vw;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 2.4vw;
  padding: 1vw 6vw 5vw;
}

section .input-box {
  width: 100%;
  height: 12vw;
  border: solid 1px #ccc;
  border-radius: 2vw;
  margin-top: 5vw;
  display: flex;
  align-items: center;
}

section .input-box i {
  margin: 0 3.6vw;
  font-size: 5.4vw;
  color: #ccc;
}

section .input-box input {
  border: none;
  outline: none;
  flex: 1;
  min-width: 0;
  font-size: 4vw;
  color: #333;
}

section .reg-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 5vw;
  margin-bottom: 2vw;
}

section .reg-box p {
  font-size: 3.3vw;
  color: #2d727e;
  user-select: none;
  cursor: pointer;
}

section .button-box {
  width: 100%;
  height: 13vw;
  border-radius: 2vw;
  background-color: #70b0bc;
  color: #fff;
  text-align: center;
  line-height: 13vw;
  font-size: 4.6vw;
  letter-spacing: 1vw;
  user-select: none;
  cursor: pointer;
  margin-top: 2vw;
}
</style>
