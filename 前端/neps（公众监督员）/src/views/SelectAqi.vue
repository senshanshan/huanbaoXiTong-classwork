<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-angle-left" @click="goBack"></i>
        <p>选择AQI</p>
        <i class="fa fa-th" @click="toSelectGrid"></i>
      </header>
      <div class="top-ban"></div>
    </div>

    <table border="1" class="aqi-table">
      <tbody>
      <tr v-for="aqi in aqiArr" :key="aqi.aqiId">
        <td>{{ aqi.chineseExplain }}</td>
        <td>{{ aqi.aqiExplain }}</td>
        <td>{{ aqi.healthImpact }}</td>
      </tr>
      </tbody>
    </table>

    <div class="aqi-box">
      <h4>请预估当前空气质量指数等级：</h4>
      <ul>
        <li v-for="aqi in aqiArr" :key="aqi.aqiId">
          <label>
            <span :style="{ backgroundColor: aqi.color }">
              {{ aqi.chineseExplain }}
            </span>
            <input
              type="radio"
              v-model="aqiId"
              :value="aqi.aqiId"
              name="aqiRadio"
            />
          </label>
        </li>
      </ul>

      <h4>请填写反馈信息：</h4>
      <textarea
        placeholder="请描述污染现象、气味、烟尘来源等信息"
        v-model="information"
      ></textarea>
      <div class="btn" @click="add">提交</div>
    </div>
  </div>
</template>

<script setup>
import { inject, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getCurDate, getCurTime, getSessionStorage } from "../common.js";

const healthTips = {
  1: "空气质量令人满意，基本无空气污染。",
  2: "空气质量可接受，极少数敏感人群应减少长时间户外活动。",
  3: "易感人群症状有轻度加剧，健康人群可能出现刺激症状。",
  4: "进一步加剧易感人群症状，对健康人群心脏、呼吸系统有影响。",
  5: "心脏病和肺病患者症状显著加剧，健康人群普遍出现症状。",
  6: "健康人群运动耐受力降低，可能出现明显强烈症状。",
};

const router = useRouter();
const route = useRoute();
const axios = inject("axios");

const aqiArr = ref([]);
const aqiId = ref(0);
const information = ref("");
const supervisor = getSessionStorage("supervisor");

const goBack = () => {
  router.back();
};

const toSelectGrid = () => {
  router.push("/selectGrid");
};

const normalizeAqi = (item) => ({
  ...item,
  healthImpact:
    item.healthImpact ||
    healthTips[item.aqiId] ||
    item.aqiExplain ||
    "请结合现场情况谨慎上报。",
});

const init = async () => {
  if (!route.query.provinceId || !route.query.cityId || !route.query.address) {
    alert("请先选择所在网格");
    router.replace("/selectGrid");
    return;
  }

  try {
    const response = await axios.post("aqi/listAqiAll");
    aqiArr.value = (response.data || []).map(normalizeAqi);
    if (aqiArr.value.length > 0) {
      aqiId.value = aqiArr.value[0].aqiId;
    }
  } catch (error) {
    console.error("加载AQI数据失败", error);
    alert("数据加载失败，请稍后重试");
  }
};

const add = async () => {
  if (!supervisor?.telId) {
    alert("登录已失效，请重新登录");
    router.replace("/login");
    return;
  }
  if (!information.value.trim()) {
    alert("反馈信息不能为空");
    return;
  }
  if (!aqiId.value) {
    alert("请选择空气质量指数等级");
    return;
  }

  const param = {
    telId: supervisor.telId,
    provinceId: Number(route.query.provinceId),
    cityId: Number(route.query.cityId),
    address: route.query.address,
    information: information.value.trim(),
    estimatedGrade: Number(aqiId.value),
    afDate: getCurDate(),
    afTime: getCurTime(),
    state: 0,
  };

  try {
    const response = await axios.post("aqiFeedback/saveAqiFeedback", param);
    if (response.data === 1) {
      alert("反馈提交成功");
      router.push("/feedbackList");
    } else {
      alert("提交反馈信息失败");
    }
  } catch (error) {
    console.error("提交反馈请求异常", error);
    alert("网络异常，请稍后重试");
  }
};

onMounted(init);
</script>

<style scoped>
.wrapper {
  width: 100%;
  min-height: 100vh;
  background-color: #cfe8e9;
}

.header-box {
  width: 100%;
}

header {
  width: 100%;
  height: 12vw;
  background-color: #70b0bc;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 4vw;
}

header i {
  font-size: 6vw;
  color: #fff;
  cursor: pointer;
}

header p {
  font-size: 5vw;
  color: #fff;
  font-weight: 500;
}

.top-ban {
  width: 100%;
  height: 3vw;
  background-color: #70b0bc;
}

.aqi-table {
  width: 90%;
  margin: 6vw auto 0;
  border-collapse: collapse;
  border-color: #e4e6e9;
  font-size: 3.5vw;
  color: #444;
  font-weight: 700;
  background-color: #fff;
}

.aqi-table td {
  padding: 1.4vw;
  background-color: #f5f7fa;
  border: 1px solid #e4e6e9;
  line-height: 1.45;
  vertical-align: top;
}

.aqi-table td:first-child {
  width: 16vw;
  text-align: center;
}

.aqi-table td:nth-child(2) {
  width: 28vw;
}

.aqi-table td:last-child {
  background-color: #fff;
  font-weight: 400;
  font-size: 3.2vw;
}

.aqi-box {
  width: 90%;
  margin: 0 auto;
}

.aqi-box h4 {
  color: #444;
  margin: 5vw 0 2vw;
  font-size: 4vw;
}

.aqi-box ul {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.aqi-box li {
  width: 50%;
  margin: 2vw 0;
}

.aqi-box label {
  display: flex;
  align-items: center;
}

.aqi-box span {
  min-width: 18vw;
  padding: 1vw 2vw;
  font-size: 3.2vw;
  font-weight: 700;
  border-radius: 1vw;
  color: #fff;
  margin-right: 2vw;
  text-align: center;
}

.aqi-box input {
  transform: scale(1.2);
}

.aqi-box textarea {
  width: 100%;
  height: 24vw;
  outline: none;
  border: solid 1px #999;
  border-radius: 2vw;
  padding: 2vw;
  font-size: 3.6vw;
  line-height: 1.5;
  margin-top: 2vw;
  resize: vertical;
}

.btn {
  margin-bottom: 5vw;
}
</style>
