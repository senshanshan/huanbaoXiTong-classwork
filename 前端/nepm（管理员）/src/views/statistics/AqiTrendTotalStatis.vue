<template>
  <section>
    <el-breadcrumb>
      <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>AQI 空气质量指数超标趋势统计</el-breadcrumb-item>
    </el-breadcrumb>

    <el-alert class="page-title" title="AQI 空气质量指数超标趋势统计" type="success" effect="dark" :closable="false" />

    <div class="table-box">
      <el-table :data="rowsWithIndex" :cell-style="{ padding: '4px' }" empty-text="无数据" style="width: 100%" v-loading="loading">
        <el-table-column prop="index" label="序号" />
        <el-table-column prop="month" label="月份" />
        <el-table-column prop="total" label="AQI 空气指数指数超标数量" />
      </el-table>
    </div>
  </section>
</template>

<script setup>
import { computed, inject, onMounted, ref } from "vue";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const rows = ref([]);
const loading = ref(false);

const rowsWithIndex = computed(() => rows.value.map((item, index) => ({ ...item, index: index + 1 })));

const loadData = async () => {
  loading.value = true;
  try {
    const response = await axios.post("statistics/listAqiTrendTotalStatis", {});
    rows.value = response.data || [];
  } catch (error) {
    console.error(error);
    myElMessage?.("AQI 空气质量指数超标趋势统计加载失败", "error");
  } finally {
    loading.value = false;
  }
};

onMounted(loadData);
</script>

<style scoped>
.page-title {
  width: 80%;
  margin: 20px 0;
}

.table-box {
  width: 80%;
  height: 450px;
  overflow: auto;
}
</style>
