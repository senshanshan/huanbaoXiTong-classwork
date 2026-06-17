<template>
  <main class="login-page">
    <header class="hero">
      <img src="../../assets/original_1781331094167_d7801370a9bc7177aa2eb71f0e068436.png" alt="" />
    </header>

    <h1>东软环保公众监督平台</h1>
    <div class="title">
      <span></span>
      <p>管理员端</p>
      <span></span>
    </div>

    <section class="login-panel">
      <el-form ref="formRef" :model="adminsForm" :rules="rules" label-position="top" @keyup.enter="login">
        <el-form-item label="登录编码" prop="adminCode">
          <el-input v-model.trim="adminsForm.adminCode" placeholder="请输入登录编码" clearable />
        </el-form-item>
        <el-form-item label="登录密码" prop="password">
          <el-input v-model="adminsForm.password" show-password placeholder="请输入登录密码" />
        </el-form-item>
        <el-button class="login-button" type="primary" :loading="loading" @click="login">登录</el-button>
      </el-form>
    </section>
  </main>
</template>

<script setup>
import { inject, reactive, ref } from "vue";
import { useRouter } from "vue-router";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const router = useRouter();
const formRef = ref();
const loading = ref(false);

const adminsForm = reactive({
  adminCode: "",
  password: ""
});

const rules = {
  adminCode: [{ required: true, message: "登录编码不能为空", trigger: "blur" }],
  password: [{ required: true, message: "登录密码不能为空", trigger: "blur" }]
};

const login = async () => {
  const valid = await formRef.value?.validate().catch(() => false);
  if (!valid) return;

  loading.value = true;
  try {
    const response = await axios.post("admins/getAdminsByCodeByPass", adminsForm);
    if (response.data) {
      sessionStorage.setItem("admins", JSON.stringify(response.data));
      myElMessage?.("登录成功", "success");
      router.push("/index");
    } else {
      myElMessage?.("登录编码或密码错误", "warning");
    }
  } catch (error) {
    console.error("管理员登录失败：", error);
    myElMessage?.("登录请求失败，请确认后端服务已启动", "error");
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-page {
  width: 100%;
  min-height: 100vh;
  padding: 28px 0 42px;
  overflow-x: hidden;
  background: #cfe8e9;
}

.hero {
  width: min(86vw, 620px);
  height: clamp(190px, 38vw, 320px);
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px 10px 0 0;
  background:
    linear-gradient(180deg, rgba(211, 237, 231, 0.86), rgba(207, 232, 233, 0.98)),
    #d8eee9;
  overflow: hidden;
}

.hero img {
  display: block;
  width: min(72vw, 520px);
  max-height: 76%;
  object-fit: contain;
}

h1 {
  width: min(86vw, 620px);
  margin: 26px auto 0;
  text-align: center;
  color: #36a852;
  font-size: clamp(30px, 6vw, 46px);
  font-weight: 700;
  line-height: 1.2;
}

.title {
  width: min(70vw, 420px);
  margin: 18px auto 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 18px;
  color: #36a852;
  font-size: clamp(18px, 3.8vw, 24px);
}

.title span {
  height: 1px;
  flex: 1;
  background: #48b960;
}

.login-panel {
  width: min(86vw, 520px);
  margin: 0 auto;
  padding: 30px 36px 38px;
  background: #fff;
  border: 1px solid #d9e1df;
  border-radius: 12px;
  box-shadow: 0 14px 36px rgba(31, 58, 52, 0.1);
}

.login-button {
  width: 100%;
  margin-top: 4px;
}

@media (max-width: 640px) {
  .login-page {
    padding-top: 18px;
  }

  .hero {
    width: 100%;
    border-radius: 0;
  }

  .login-panel {
    padding: 24px 22px 30px;
  }
}
</style>
