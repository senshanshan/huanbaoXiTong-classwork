<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <!-- Vue规范路由返回，弃用原生onclick -->
        <i class="fa fa-angle-left" @click="$router.back()"></i>
        <p>反馈任务详细信息</p>
        <i class="fa fa-bars" @click="toFeedbackList"></i>
      </header>
      <div class="top-ban"></div>
      <img src="../assets/header.png" />
    </div>

    <table class="info">
      <tbody>
      <tr>
        <td>反馈用户</td>
        <td>
          {{ aqiFeedback.supervisor?.realName || '' }}
          <br>
          {{ aqiFeedback.supervisor?.telId || '' }}
        </td>
      </tr>
      <tr>
        <td>网格</td>
        <td>
          {{ aqiFeedback.gridProvince?.provinceName || '' }}
          {{ aqiFeedback.gridCity?.cityName || '' }}
        </td>
      </tr>
      <tr>
        <td>详细地址</td>
        <td>{{ aqiFeedback.address || '' }}</td>
      </tr>
      <tr>
        <td>预估等级</td>
        <td>
          {{ aqiFeedback.aqi?.chineseExplain || '' }}
          {{ aqiFeedback.aqi?.aqiExplain || '' }}
        </td>
      </tr>
      <tr>
        <td>信息描述</td>
        <td>{{ aqiFeedback.information || '' }}</td>
      </tr>
      </tbody>
    </table>

    <div class="actual">
      <table>
        <tbody>
        <tr>
          <td>SO2浓度</td>
          <td>
            <input
              type="text"
              v-model="so2Value"
              placeholder="请输入SO2浓度"
            />
          </td>
          <td>ug/m3</td>
          <td>
            <p :style="{ backgroundColor: so2LevelObj?.color || '#ccc' }">
              {{ so2LevelObj?.chineseExplain || '-' }}
            </p>
          </td>
        </tr>
        <tr>
          <td>CO浓度</td>
          <td>
            <input
              type="text"
              v-model="coValue"
              placeholder="请输入CO浓度"
            />
          </td>
          <td>ug/m3</td>
          <td>
            <p :style="{ backgroundColor: coLevelObj?.color || '#ccc' }">
              {{ coLevelObj?.chineseExplain || '-' }}
            </p>
          </td>
        </tr>
        <tr>
          <td>PM2.5</td>
          <td>
            <input
              type="text"
              v-model="spmValue"
              placeholder="请输入PM2.5浓度"
            />
          </td>
          <td>ug/m3</td>
          <td>
            <p :style="{ backgroundColor: spmLevelObj?.color || '#ccc' }">
              {{ spmLevelObj?.chineseExplain || '-' }}
            </p>
          </td>
        </tr>
        </tbody>
      </table>

      <h3 :style="{ backgroundColor: aqiObj?.color || '#999' }">
        实测AQI等级：{{ aqiObj?.chineseExplain || '-' }}级 {{ aqiObj?.aqiExplain || '' }}
      </h3>

      <div class="btn" @click="submitData">提交实测数据</div>
    </div>

    <footer>
      <img src="../assets/bottom.png" />
    </footer>
  </div>
</template>

<script setup>
import { ref, inject, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getSessionStorage, getCurDate, getCurTime } from "../common.js";

const route = useRoute();
const router = useRouter();
const axios = inject("axios");
const gridMember = getSessionStorage("gridMember");

// 反馈详情数据
const aqiFeedback = ref({
  supervisor: {},
  gridProvince: {},
  gridCity: {},
  aqi: {},
  gridMember: {}
});

// AQI等级基础数组，初始默认占位
const aqiArr = ref([
  {
    aqiId: 1,
    chineseExplain: '一',
    color: '#02E300',
    so2Min: 0,
    so2Max: 50,
    coMin: 0,
    coMax: 4,
    spmMin: 0,
    spmMax: 35
  }
]);

// 输入值
const so2Value = ref('');
const coValue = ref('');
const spmValue = ref('');

// 修复：移除ref包裹computed
const so2LevelObj = computed(() => {
  let result = {};
  // 防御判断
  if (!Array.isArray(aqiArr.value) || aqiArr.value.length === 0 || so2Value.value === '') return result;
  const numVal = Number(so2Value.value);
  if (isNaN(numVal)) return result;

  const firstItem = aqiArr.value[0];
  const lastItem = aqiArr.value.at(-1);
  if (numVal < firstItem.so2Min) return firstItem;
  if (numVal > lastItem.so2Max) return lastItem;

  for (let i = 0; i < aqiArr.value.length; i++) {
    const item = aqiArr.value[i];
    if (numVal >= item.so2Min && numVal <= item.so2Max) {
      result = item;
    }
  }
  return result;
});

const coLevelObj = computed(() => {
  let result = {};
  if (!Array.isArray(aqiArr.value) || aqiArr.value.length === 0 || coValue.value === '') return result;
  const numVal = Number(coValue.value);
  if (isNaN(numVal)) return result;

  const firstItem = aqiArr.value[0];
  const lastItem = aqiArr.value.at(-1);
  if (numVal < firstItem.coMin) return firstItem;
  if (numVal > lastItem.coMax) return lastItem;

  for (let i = 0; i < aqiArr.value.length; i++) {
    const item = aqiArr.value[i];
    if (numVal >= item.coMin && numVal <= item.coMax) {
      result = item;
    }
  }
  return result;
});

const spmLevelObj = computed(() => {
  let result = {};
  if (!Array.isArray(aqiArr.value) || aqiArr.value.length === 0 || spmValue.value === '') return result;
  const numVal = Number(spmValue.value);
  if (isNaN(numVal)) return result;

  const firstItem = aqiArr.value[0];
  const lastItem = aqiArr.value.at(-1);
  if (numVal < firstItem.spmMin) return firstItem;
  if (numVal > lastItem.spmMax) return lastItem;

  for (let i = 0; i < aqiArr.value.length; i++) {
    const item = aqiArr.value[i];
    if (numVal >= item.spmMin && numVal <= item.spmMax) {
      result = item;
    }
  }
  return result;
});

// 总AQI等级，取最大aqiId
const aqiObj = computed(() => {
  let result = {};
  const s = so2LevelObj.value?.aqiId ?? 0;
  const c = coLevelObj.value?.aqiId ?? 0;
  const sp = spmLevelObj.value?.aqiId ?? 0;
  const maxAqi = Math.max(s, c, sp);

  if (!Array.isArray(aqiArr.value)) return result;
  for (let i = 0; i < aqiArr.value.length; i++) {
    if (aqiArr.value[i].aqiId === maxAqi) {
      result = aqiArr.value[i];
    }
  }
  return result;
});

// 初始化数据
const init = () => {
  // 获取反馈详情
  axios.post("aqiFeedback/getAqiFeedbackById", {
    afId: route.query.afId
  })
  .then((response) => {
    aqiFeedback.value = response.data || {};
  })
  .catch((error) => {
    console.log('获取反馈详情失败', error);
  });

  // 获取AQI基础配置
  axios.post("aqi/listAqiAll")
  .then((response) => {
    if (Array.isArray(response.data)) {
      aqiArr.value = response.data;
    }
  })
  .catch((error) => {
    console.log('获取AQI配置失败', error);
  });
};

// 跳转列表
const toFeedbackList = () => {
  router.push('/feedbackList');
};

// 提交数据 - 使用async/await串行控制异步
const submitData = async () => {
  // 空值校验
  if (so2Value.value === '' || coValue.value === '' || spmValue.value === '') {
    alert('aqi等级分项数据值不能为空！');
    return;
  }
  // 数字校验
  const so2Num = Number(so2Value.value);
  const coNum = Number(coValue.value);
  const spmNum = Number(spmValue.value);
  if (isNaN(so2Num) || isNaN(coNum) || isNaN(spmNum)) {
    alert('aqi等级分项数据值必须为数字！');
    return;
  }
  if (!confirm('确认要提交此数据吗？')) return;

  // 组装参数，增加gridMember空值防御
  const gmId = gridMember?.gmId ?? null;
  const param = {
    provinceId: aqiFeedback.value.provinceId,
    cityId: aqiFeedback.value.cityId,
    address: aqiFeedback.value.address,
    so2Value: so2Value.value,
    so2Level: so2LevelObj.value?.aqiId,
    coValue: coValue.value,
    coLevel: coLevelObj.value?.aqiId,
    spmValue: spmValue.value,
    spmLevel: spmLevelObj.value?.aqiId,
    aqiId: aqiObj.value?.aqiId,
    confirmDate: getCurDate(),
    confirmTime: getCurTime(),
    gmId: gmId,
    fdId: aqiFeedback.value.telId,
    information: aqiFeedback.value.information
  };

  try {
    // 第一步：保存统计数据
    const saveRes = await axios.post("statistics/saveStatistics", param);
    if (saveRes.data !== 1) {
      alert('提交实测数据失败');
      return;
    }
    // 第二步：更新反馈状态
    const updateRes = await axios.post("aqiFeedback/updateAqiFeedbackState", {
      afId: aqiFeedback.value.afId,
      state: 2
    });
    if (updateRes.data !== 1) {
      alert('更新反馈信息失败！');
      return;
    }
    // 全部成功后跳转
    router.push('/feedbackList');
  } catch (error) {
    console.log('提交请求异常', error);
    alert('网络或服务异常，请稍后重试');
  }
};

// 页面初始化
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
  object-fit: cover;
}

.info {
  width: 90%;
  margin: 0 auto;
  margin-top: 2vw;
  border-collapse: collapse;
  border: solid 1px #ddd;
  color: #666;
}

.info tr td {
  box-sizing: border-box;
  padding: 1.4vw;
  font-size: 3.6vw;
}
.info tr td:first-child {
  background-color: #f5f7fa;
  width: 22%;
}

.actual {
  width: 90%;
  margin: 0 auto;
  margin-top: 4vw;
  color: #666;
}

.actual table {
  margin-top: 1vw;
  font-size: 3.6vw;
}
.actual table tr {
  height: 8vw;
}
.actual table tr td input {
  border: none;
  outline: none;
  width: 45vw;
  height: 7vw;
  border: solid 1px #999;
  border-radius: 3.5vw;
  box-sizing: border-box;
  padding-left: 3vw;
}
.actual table tr td:last-child p {
  margin-left: 4vw;
  width: 7vw;
  height: 7vw;
  text-align: center;
  line-height: 7vw;
  background-color: #ef0000;
  border-radius: 1.4vw;
  color: #fff;
  font-size: 3.6vw;
  font-weight: 700;
}
.actual h3 {
  width: 100%;
  height: 8vw;
  text-align: center;
  line-height: 8vw;
  font-size: 4vw;
  color: #fff;
  background-color: #ef0000;
  margin: 3vw 0;
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