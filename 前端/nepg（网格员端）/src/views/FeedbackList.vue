<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-angle-left" @click="goBack">退出</i>
        <p>反馈任务列表</p>
        <i></i>
      </header>
      <div class="top-ban"></div>
      <img src="../assets/header.png" alt="header" />
    </div>

    <ul>
      <li v-for="aqiFeedback in aqiFeedbackArr" :key="aqiFeedback.afId">
        <div class="left">
          <div
            class="icon"
            :style="{ backgroundColor: aqiFeedback.aqi.color }"
          >
            {{ aqiFeedback.aqi.chineseExplain }}
          </div>
          <div class="info">
            <p>
              {{ aqiFeedback.gridProvince.provinceName }}
              {{ aqiFeedback.gridCity.cityName }}
              {{ aqiFeedback.afDate }}
            </p>
            <p>{{ aqiFeedback.address }}</p>
          </div>
        </div>
        <div class="right" @click="toFeedbackInfo(aqiFeedback.afId)">
          <p>去检测</p>
          <i class="fa fa-hand-o-right"></i>
        </div>
      </li>
    </ul>

    <div class="no-data" v-if="aqiFeedbackArrSize === 0">
      No Data
    </div>

    <footer>
      <img src="../assets/bottom.png" alt="footer" />
    </footer>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';

// 从App.vue注入的axios实例
const axios = inject('axios');
const router = useRouter();

// 响应式数据
const aqiFeedbackArr = ref([]);
const aqiFeedbackArrSize = ref(0);

// 获取当前登录的网格员信息
const gridMember = JSON.parse(sessionStorage.getItem('gridMember'));

// 页面加载时获取反馈列表
onMounted(() => {
  axios
    .post("aqiFeedback/listAqiFeedbackByGmId", {
      gmId: gridMember.gmId,
      state: 1
    })
    .then((response) => {
      aqiFeedbackArr.value = response.data;
      aqiFeedbackArrSize.value = response.data.length;
    })
    .catch((error) => {
      console.error("获取反馈列表失败:", error);
    });
});

// 跳转到反馈详情页
const toFeedbackInfo = (afId) => {
  router.push({
    path: '/feedbackInfo',
    query: { afId: afId }
  });
};

// 返回上一页（修正了原代码的history.go(-1)问题，改用router.back）
const goBack = () => {
  router.back();
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

/*************** 头部共通样式 **************/
.header-box {
  width: 100%;
}
.header-box header {
  width: 100%;
  height: 15.7vw;
  background-color: #FFF;
  color: #555;

  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0 3.6vw;

  position: fixed;
  left: 0;
  top: 0;
  z-index: 10;
}
.header-box .top-ban {
  width: 100%;
  height: 15.7vw;
}
.header-box img {
  width: 100%;
  display: block;
  height: 200px;
  object-fit: cover;
}
.header-box .fa {
  font-size: 8vw;
}

ul {
  width: 100%;
  list-style: none;
  padding: 0;
  margin: 0;
}
ul li {
  width: 100%;
  height: 18vw;
  max-height: 80px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  padding: 3vw;
  border-bottom: dashed 1px #999;
  color: #666;
}
ul li .left {
  flex: 4;
  display: flex;
  align-items: center;
}
ul li .right {
  flex: 1;
  display: flex;
  align-items: center;
  font-size: 4vw;
  box-sizing: border-box;
  padding: 1vw 2vw;
  border: solid 1px #c6e2ff;
  background-color: #ecf5ff;
  border-radius: 1vw;
  color: #409eff;
  cursor: pointer;
}
ul li .left .icon {
  width: 8vw;
  height: 8vw;
  max-width: 40px;
  max-height: 40px;
  background-color: #fe0000;
  border-radius: 1vw;
  margin-right: 3vw;

  text-align: center;
  line-height: 8vw;

  font-size: 4vw;
  color: #fff;
  font-weight: 700;
}
ul li .left .info p:first-child {
  font-size: 4vw;
  font-weight: 700;
  margin-bottom: 1vw;
}
ul li .left .info p:last-child {
  font-size: 3.4vw;
}
ul li .right p {
  margin-right: 1vw;
}

.no-data {
  text-align: center;
  font-size: 5vw;
  color: #999;
  padding: 10vw 0;
}

/*************** 底部共通样式 **************/
footer {
  width: 100%;
  position: absolute;
  left: 0;
  bottom: 0;
}
footer img {
  width: 100%;
  display: block;
  height: 100px;
}
</style>