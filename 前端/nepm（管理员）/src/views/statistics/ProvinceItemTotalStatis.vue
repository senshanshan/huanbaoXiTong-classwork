<template>
  <section>
    <el-breadcrumb>
      <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>省分组分项检查统计</el-breadcrumb-item>
    </el-breadcrumb>

    <el-alert class="page-title" title="省分组分项检查统计" type="success" effect="dark" :closable="false" />

    <div class="table-box">
      <el-table :data="rows" :cell-style="{ padding: '4px' }" empty-text="无数据" style="width: 100%" v-loading="loading">
        <el-table-column prop="provinceId" label="省编号" width="90" />
        <el-table-column prop="provinceAbbr" label="省简称" />
        <el-table-column prop="provinceName" label="省名称" />
        <el-table-column prop="so2Total" label="SO2 超标累计" />
        <el-table-column prop="coTotal" label="CO 超标累计" />
        <el-table-column prop="spmTotal" label="SPM2.5 超标累计" />
        <el-table-column prop="aqiTotal" label="AQI 超标累计" />
      </el-table>
    </div>
  </section>
</template>

<script setup>
import { inject, onMounted, ref } from "vue";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const rows = ref([]);
const loading = ref(false);

const loadData = async () => {
  loading.value = true;
  try {
    const response = await axios.post("statistics/listProvinceItemTotalStatis", {});
    rows.value = response.data || [];
  } catch (error) {
    console.error(error);
    myElMessage?.("省分组分项检查统计加载失败", "error");
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
