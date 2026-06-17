<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-angle-left" @click="$router.back()"></i>
        <p>反馈任务详细信息</p>
        <i class="fa fa-bars" @click="toFeedbackList"></i>
      </header>
      <div class="top-ban"></div>
      <img src="../assets/header.png" alt="header" />
    </div>

    <table class="info">
      <tbody>
      <tr>
        <td>反馈用户</td>
        <td>
          {{ aqiFeedback.supervisor?.realName || "" }}
          <br>
          {{ aqiFeedback.supervisor?.telId || "" }}
        </td>
      </tr>
      <tr>
        <td>网格</td>
        <td>
          {{ aqiFeedback.gridProvince?.provinceName || "" }}
          {{ aqiFeedback.gridCity?.cityName || "" }}
        </td>
      </tr>
      <tr>
        <td>详细地址</td>
        <td>{{ aqiFeedback.address || "" }}</td>
      </tr>
      <tr>
        <td>预估等级</td>
        <td>
          {{ aqiFeedback.aqi?.chineseExplain || "" }}
          {{ aqiFeedback.aqi?.aqiExplain || "" }}
        </td>
      </tr>
      <tr>
        <td>信息描述</td>
        <td>{{ aqiFeedback.information || "" }}</td>
      </tr>
      </tbody>
    </table>

    <div class="actual">
      <table>
        <tbody>
        <tr>
          <td>SO2浓度</td>
          <td>
            <input type="text" v-model="so2Value" placeholder="请输入SO2浓度" />
          </td>
          <td>ug/m3</td>
          <td>
            <p :style="{ backgroundColor: so2LevelObj?.color || '#ccc' }">
              {{ so2LevelObj?.chineseExplain || "-" }}
            </p>
          </td>
        </tr>
        <tr>
          <td>CO浓度</td>
          <td>
            <input type="text" v-model="coValue" placeholder="请输入CO浓度" />
          </td>
          <td>mg/m3</td>
          <td>
            <p :style="{ backgroundColor: coLevelObj?.color || '#ccc' }">
              {{ coLevelObj?.chineseExplain || "-" }}
            </p>
          </td>
        </tr>
        <tr>
          <td>PM2.5</td>
          <td>
            <input type="text" v-model="spmValue" placeholder="请输入PM2.5浓度" />
          </td>
          <td>ug/m3</td>
          <td>
            <p :style="{ backgroundColor: spmLevelObj?.color || '#ccc' }">
              {{ spmLevelObj?.chineseExplain || "-" }}
            </p>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="aqi-result" :style="{ backgroundColor: aqiObj?.color || '#999' }">
        <span>实测AQI等级：{{ aqiObj?.chineseExplain || "-" }}</span>
        <small>{{ aqiObj?.aqiExplain || "" }}</small>
      </div>

      <div class="btn" @click="submitData">提交实测数据</div>
    </div>

    <footer>
      <img src="../assets/bottom.png" alt="footer" />
    </footer>
  </div>
</template>

<script setup>
import { computed, inject, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getCurDate, getCurTime, getSessionStorage } from "../common.js";

const route = useRoute();
const router = useRouter();
const axios = inject("axios");
const gridMember = getSessionStorage("gridMember");

const aqiFeedback = ref({
  supervisor: {},
  gridProvince: {},
  gridCity: {},
  aqi: {},
  gridMember: {},
});

const fallbackAqiArr = [
  { aqiId: 1, chineseExplain: "优", color: "#00e400", aqiExplain: "空气质量令人满意", so2Min: 0, so2Max: 50 },
  { aqiId: 2, chineseExplain: "良", color: "#ffff00", aqiExplain: "空气质量可接受", so2Min: 51, so2Max: 150 },
  { aqiId: 3, chineseExplain: "轻度污染", color: "#ff7e00", aqiExplain: "易感人群症状有轻度加剧", so2Min: 151, so2Max: 475 },
  { aqiId: 4, chineseExplain: "中度污染", color: "#ff0000", aqiExplain: "进一步加剧易感人群症状", so2Min: 476, so2Max: 800 },
  { aqiId: 5, chineseExplain: "重度污染", color: "#99004c", aqiExplain: "心脏病和肺病患者症状显著加剧", so2Min: 801, so2Max: 1600 },
  { aqiId: 6, chineseExplain: "严重污染", color: "#7e0023", aqiExplain: "健康人群运动耐受力降低", so2Min: 1601, so2Max: 999999 },
];

const pollutantRanges = {
  co: [
    [0, 2],
    [2.1, 4],
    [4.1, 14],
    [14.1, 24],
    [24.1, 36],
    [36.1, 999999],
  ],
  spm: [
    [0, 35],
    [36, 75],
    [76, 115],
    [116, 150],
    [151, 250],
    [251, 999999],
  ],
};

const aqiArr = ref(fallbackAqiArr);
const so2Value = ref("");
const coValue = ref("");
const spmValue = ref("");

const withLocalRanges = (items) =>
  items.map((item, index) => ({
    ...fallbackAqiArr[index],
    ...item,
    coMin: pollutantRanges.co[index][0],
    coMax: pollutantRanges.co[index][1],
    spmMin: pollutantRanges.spm[index][0],
    spmMax: pollutantRanges.spm[index][1],
  }));

const findLevel = (value, minKey, maxKey) => {
  if (value === "") return {};
  const numVal = Number(value);
  if (Number.isNaN(numVal)) return {};
  const arr = aqiArr.value;
  if (!Array.isArray(arr) || arr.length === 0) return {};
  const firstItem = arr[0];
  const lastItem = arr[arr.length - 1];
  if (numVal <= firstItem[maxKey]) return firstItem;
  if (numVal >= lastItem[minKey]) return lastItem;
  return arr.find((item) => numVal >= item[minKey] && numVal <= item[maxKey]) || lastItem;
};

const so2LevelObj = computed(() => findLevel(so2Value.value, "so2Min", "so2Max"));
const coLevelObj = computed(() => findLevel(coValue.value, "coMin", "coMax"));
const spmLevelObj = computed(() => findLevel(spmValue.value, "spmMin", "spmMax"));

const aqiObj = computed(() => {
  const maxAqi = Math.max(
    so2LevelObj.value?.aqiId ?? 0,
    coLevelObj.value?.aqiId ?? 0,
    spmLevelObj.value?.aqiId ?? 0
  );
  return aqiArr.value.find((item) => item.aqiId === maxAqi) || {};
});

const init = () => {
  axios
    .post("aqiFeedback/getAqiFeedbackById", { afId: route.query.afId })
    .then((response) => {
      aqiFeedback.value = response.data || {};
    })
    .catch((error) => {
      console.log("获取反馈详情失败", error);
    });

  axios
    .post("aqi/listAqiAll")
    .then((response) => {
      if (Array.isArray(response.data) && response.data.length > 0) {
        aqiArr.value = withLocalRanges(response.data);
      }
    })
    .catch((error) => {
      console.log("获取AQI配置失败", error);
      aqiArr.value = withLocalRanges(fallbackAqiArr);
    });
};

const toFeedbackList = () => {
  router.push("/feedbackList");
};

const submitData = async () => {
  if (so2Value.value === "" || coValue.value === "" || spmValue.value === "") {
    alert("AQI等级分项数据值不能为空！");
    return;
  }
  const so2Num = Number(so2Value.value);
  const coNum = Number(coValue.value);
  const spmNum = Number(spmValue.value);
  if (Number.isNaN(so2Num) || Number.isNaN(coNum) || Number.isNaN(spmNum)) {
    alert("AQI等级分项数据值必须为数字！");
    return;
  }
  if (!confirm("确认要提交此数据吗？")) return;

  const param = {
    provinceId: aqiFeedback.value.provinceId,
    cityId: aqiFeedback.value.cityId,
    address: aqiFeedback.value.address,
    so2Value: so2Num,
    so2Level: so2LevelObj.value?.aqiId,
    coValue: coNum,
    coLevel: coLevelObj.value?.aqiId,
    spmValue: spmNum,
    spmLevel: spmLevelObj.value?.aqiId,
    aqiId: aqiObj.value?.aqiId,
    confirmDate: getCurDate(),
    confirmTime: getCurTime(),
    gmId: gridMember?.gmId ?? null,
    fdId: aqiFeedback.value.telId,
    information: aqiFeedback.value.information,
  };

  try {
    const saveRes = await axios.post("statistics/saveStatistics", param);
    if (saveRes.data !== 1) {
      alert("提交实测数据失败");
      return;
    }
    const updateRes = await axios.post("aqiFeedback/updateAqiFeedbackState", {
      afId: aqiFeedback.value.afId,
      state: 2,
    });
    if (updateRes.data !== 1) {
      alert("更新反馈信息失败");
      return;
    }
    router.push("/feedbackList");
  } catch (error) {
    console.log("提交请求异常", error);
    alert("网络或服务异常，请稍后重试");
  }
};

init();
</script>

<style scoped>
.wrapper {
  width: 100%;
  min-height: 100vh;
  position: relative;
  padding-bottom: 100px;
  box-sizing: border-box;
}

.header-box {
  width: 100%;
}

.header-box header {
  width: 100%;
  height: 15.7vw;
  background-color: #fff;
  color: #555;
  display: flex;
  align-items: center;
  justify-content: space-between;
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
  object-fit: cover;
}

.info {
  width: 90%;
  margin: 2vw auto 0;
  border-collapse: collapse;
  border: solid 1px #ddd;
  color: #666;
}

.info td {
  padding: 1.4vw;
  font-size: 3.6vw;
  line-height: 1.35;
}

.info td:first-child {
  background-color: #f5f7fa;
  width: 22%;
}

.actual {
  width: 90%;
  margin: 4vw auto 0;
  color: #666;
}

.actual table {
  width: 100%;
  margin-top: 1vw;
  font-size: 3.6vw;
  table-layout: fixed;
}

.actual table tr {
  height: 8vw;
}

.actual table td:first-child {
  width: 18vw;
}

.actual table td:nth-child(2) {
  width: 45vw;
}

.actual table td:nth-child(3) {
  width: 16vw;
  padding-left: 1vw;
}

.actual table td:last-child {
  width: 9vw;
}

.actual input {
  width: 100%;
  height: 7vw;
  border: solid 1px #999;
  border-radius: 3.5vw;
  padding-left: 3vw;
  outline: none;
}

.actual table td:last-child p {
  width: 7vw;
  min-height: 7vw;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  line-height: 1.05;
  border-radius: 1.4vw;
  color: #fff;
  font-size: 3.2vw;
  font-weight: 700;
  padding: 0.4vw;
  word-break: break-all;
}

.aqi-result {
  width: 100%;
  min-height: 8vw;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  gap: 0.6vw 1.2vw;
  text-align: center;
  line-height: 1.25;
  font-size: 3.6vw;
  color: #fff;
  margin: 3vw 0;
  padding: 1.2vw 1.6vw;
  font-weight: 700;
}

.aqi-result small {
  max-width: 100%;
  font-size: 3.1vw;
  font-weight: 600;
}

.actual .btn {
  width: 40vw;
  height: 8vw;
  margin: 0 auto;
  text-align: center;
  line-height: 8vw;
  background-color: #409eff;
  border-radius: 2vw;
  font-size: 4vw;
  font-weight: 700;
  color: #fff;
}
</style>
