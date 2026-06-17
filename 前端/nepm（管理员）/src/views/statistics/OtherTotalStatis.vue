<template>
  <section>
    <el-breadcrumb>
      <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>其它数据统计</el-breadcrumb-item>
    </el-breadcrumb>

    <el-descriptions class="margin-top" title="其他数据统计" :column="1" border v-loading="loading">
      <el-descriptions-item label="空气质量检测总数量">{{ aqiCount }}</el-descriptions-item>
      <el-descriptions-item label="空气质量检测良好数量">{{ aqiGoodCount }}</el-descriptions-item>
      <el-descriptions-item label="省网格覆盖率">{{ formatPercent(provinceCoverage) }}</el-descriptions-item>
      <el-descriptions-item label="大城市网格覆盖率">{{ formatPercent(cityCoverage) }}</el-descriptions-item>
    </el-descriptions>
  </section>
</template>

<script setup>
import { inject, onMounted, ref } from "vue";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const loading = ref(false);
const aqiCount = ref(0);
const aqiGoodCount = ref(0);
const provinceCoverage = ref(0);
const cityCoverage = ref(0);

const formatPercent = (value) => {
  if (value === null || value === undefined || value === "") return "0%";
  return String(value).includes("%") ? value : `${value}%`;
};

const loadData = async () => {
  loading.value = true;
  try {
    const [count, good, province, city] = await Promise.all([
      axios.post("statistics/getAqiCount", {}),
      axios.post("statistics/getAqiGoodCount", {}),
      axios.post("statistics/getProvinceCoverage", {}),
      axios.post("statistics/getCityCoverage", {})
    ]);
    aqiCount.value = count.data ?? 0;
    aqiGoodCount.value = good.data ?? 0;
    provinceCoverage.value = province.data ?? 0;
    cityCoverage.value = city.data ?? 0;
  } catch (error) {
    console.error(error);
    myElMessage?.("其它数据统计加载失败", "error");
  } finally {
    loading.value = false;
  }
};

onMounted(loadData);
</script>

<style scoped>
.margin-top {
  width: 50%;
  margin-top: 20px;
}

@media (max-width: 900px) {
  .margin-top {
    width: 100%;
  }
}
</style>
