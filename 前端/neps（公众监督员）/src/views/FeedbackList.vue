<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-angle-left" @click="goBack"></i>
        <p>历史反馈信息列表</p>
        <i class="fa fa-th" @click="toSelectGrid"></i>
      </header>
      <div class="top-ban"></div>
      <img src="../assets/header.png" alt="顶部背景图" />
    </div>

    <ul class="feedback-list" v-if="aqiFeedbackArr.length">
      <li
        v-for="aqiFeedback in aqiFeedbackArr"
        :key="aqiFeedback.afId"
        class="feedback-item"
      >
        <div class="item-header">
          <i :style="{ backgroundColor: aqiFeedback.aqi?.color }" class="aqi-tag">
            {{ aqiFeedback.aqi?.chineseExplain || "暂无数据" }}
          </i>
          <span class="state">{{ stateText(aqiFeedback.state) }}</span>
        </div>
        <p class="time">{{ aqiFeedback.afDate || "" }} {{ aqiFeedback.afTime || "" }}</p>
        <p>
          {{ aqiFeedback.gridProvince?.provinceName || "未知省份" }}
          {{ aqiFeedback.gridCity?.cityName || "未知城市" }}
          {{ aqiFeedback.address || "" }}
        </p>
        <p>{{ aqiFeedback.information || "暂无反馈描述" }}</p>
      </li>
    </ul>

    <div class="empty" v-else>暂无反馈记录</div>
  </div>
</template>

<script setup>
import { inject, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { getSessionStorage } from "../common.js";

const router = useRouter();
const axios = inject("axios");
const supervisor = getSessionStorage("supervisor");

const aqiFeedbackArr = ref([]);

const goBack = () => {
  router.back();
};

const toSelectGrid = () => {
  router.push("/selectGrid");
};

const stateText = (state) => {
  const map = {
    0: "待指派",
    1: "已指派",
    2: "已处理",
  };
  return map[state] || "未知状态";
};

const init = async () => {
  if (!supervisor?.telId) {
    alert("登录已失效，请重新登录");
    router.replace("/login");
    return;
  }

  try {
    const response = await axios.post("aqiFeedback/listAqiFeedbackByTelId", {
      telId: supervisor.telId,
    });
    aqiFeedbackArr.value = response.data || [];
  } catch (error) {
    console.error("获取反馈列表失败", error);
    alert("加载反馈列表失败，请稍后重试");
  }
};

onMounted(init);
</script>

<style scoped>
.wrapper {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.feedback-list {
  padding: 4vw;
}

.feedback-item {
  background-color: #fff;
  border-radius: 2vw;
  padding: 4vw;
  margin-bottom: 3vw;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.item-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2vw;
}

.aqi-tag {
  display: inline-block;
  padding: 1vw 2vw;
  border-radius: 1vw;
  color: #fff;
  font-size: 3.2vw;
  font-style: normal;
}

.state {
  font-size: 3.3vw;
  color: #2d727e;
}

.time {
  color: #888;
  font-size: 3.2vw;
}

.feedback-item p {
  margin-top: 1.4vw;
  color: #333;
  font-size: 3.6vw;
  line-height: 1.5;
}

.empty {
  width: 90%;
  margin: 8vw auto 0;
  padding: 8vw 0;
  color: #777;
  text-align: center;
  background-color: #fff;
  border-radius: 2vw;
}
</style>
