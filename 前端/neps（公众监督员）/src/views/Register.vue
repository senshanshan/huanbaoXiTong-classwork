<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-angle-left" @click="$router.back()"></i>
        <p>注册</p>
        <i></i>
      </header>
      <div class="top-ban"></div>
      <img src="../assets/header.png" alt="顶部横幅" />
    </div>

    <table class="table-form">
      <tbody>
      <tr>
        <td>手机号码</td>
        <td>
          <input
            type="text"
            v-model="supervisorForm.telId"
            @blur="isExist"
            placeholder="请输入手机号码"
          />
        </td>
      </tr>
      <tr>
        <td>真实姓名</td>
        <td>
          <input
            type="text"
            v-model="supervisorForm.realName"
            placeholder="真实姓名便于我们联系您"
          />
        </td>
      </tr>
      <tr>
        <td>出生日期</td>
        <td>
          <input type="date" v-model="supervisorForm.birthday" />
        </td>
      </tr>
      <tr>
        <td>性别</td>
        <td>
          <input type="radio" v-model="supervisorForm.sex" value="1" id="man" />
          <label for="man">男</label>
          <input
            type="radio"
            v-model="supervisorForm.sex"
            value="0"
            id="woman"
          />
          <label for="woman">女</label>
        </td>
      </tr>
      <tr>
        <td>密码</td>
        <td>
          <input
            type="password"
            v-model="supervisorForm.password"
            placeholder="请输入密码"
          />
        </td>
      </tr>
      <tr>
        <td>确认密码</td>
        <td>
          <input
            type="password"
            v-model="supervisorForm.confirmPassword"
            placeholder="请再次输入密码"
          />
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <div class="btn" @click="register">注册</div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { inject, reactive } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const axios = inject("axios");

const supervisorForm = reactive({
  telId: "",
  realName: "",
  birthday: "",
  sex: "1",
  password: "",
  confirmPassword: "",
});

const isExist = async () => {
  if (!/^1[3-9]\d{9}$/.test(supervisorForm.telId)) {
    return;
  }

  try {
    const response = await axios.post("supervisor/getSupervisorById", {
      telId: supervisorForm.telId,
    });
    if (response.data) {
      alert("此手机号码已被注册");
      supervisorForm.telId = "";
    }
  } catch (error) {
    console.error("查重接口异常", error);
  }
};

const register = async () => {
  if (!supervisorForm.telId) {
    alert("电话号码不能为空");
    return;
  }
  if (!/^1[3-9]\d{9}$/.test(supervisorForm.telId)) {
    alert("请输入合法的11位手机号");
    return;
  }
  if (!supervisorForm.realName.trim()) {
    alert("真实姓名不能为空");
    return;
  }
  if (!supervisorForm.birthday) {
    alert("出生日期不能为空");
    return;
  }
  if (!supervisorForm.password) {
    alert("密码不能为空");
    return;
  }
  if (supervisorForm.password !== supervisorForm.confirmPassword) {
    alert("两次密码输入不一致");
    return;
  }

  try {
    const { confirmPassword, ...param } = supervisorForm;
    const response = await axios.post("supervisor/saveSupervisor", param);
    if (response.data === 1) {
      alert("注册成功，请登录");
      router.push("/login");
    } else {
      alert("注册失败，请稍后重试");
    }
  } catch (error) {
    console.error("注册接口异常", error);
    alert("网络异常，注册失败");
  }
};
</script>

<style scoped>
.table-form input[type="text"],
.table-form input[type="password"],
.table-form input[type="date"] {
  width: 100%;
  height: 9vw;
  font-size: 4vw;
  color: #333;
}

.table-form label {
  margin: 0 5vw 0 1.5vw;
  color: #333;
}
</style>
