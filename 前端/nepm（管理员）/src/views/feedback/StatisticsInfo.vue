<template>
  <section class="page" v-loading="loading">
    <el-breadcrumb>
      <el-breadcrumb-item>确认 AQI 数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>确认 AQI 数据详情</el-breadcrumb-item>
    </el-breadcrumb>

    <el-descriptions class="detail" title="确认 AQI 数据详情" :column="1" border>
      <template #extra>
        <el-button type="primary" @click="router.back()">返回</el-button>
      </template>
      <el-descriptions-item label="确认 AQI 数据编号">{{ detail.id || "-" }}</el-descriptions-item>
      <el-descriptions-item label="确认信息所在地址">
        <el-tag size="small">{{ detail.gridProvince?.provinceName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.gridCity?.cityName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.address || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="确认 AQI 等级">
        <el-tag size="small" :color="detail.aqi?.color" effect="dark">{{ detail.aqi?.chineseExplain || detail.aqiId || "-" }}</el-tag>
        <el-tag size="small">{{ detail.aqi?.aqiExplain || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="污染物浓度">
        <el-tag size="small">SO2：{{ detail.so2Value ?? "-" }}（等级 {{ detail.so2Level ?? "-" }}）</el-tag>
        <el-tag size="small">CO：{{ detail.coValue ?? "-" }}（等级 {{ detail.coLevel ?? "-" }}）</el-tag>
        <el-tag size="small">SPM：{{ detail.spmValue ?? "-" }}（等级 {{ detail.spmLevel ?? "-" }}）</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="确认日期时间">
        <el-tag size="small">{{ detail.confirmDate || "-" }}</el-tag>
        <el-tag size="small">{{ detail.confirmTime || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="网格员信息">
        <el-tag size="small">{{ detail.gridMember?.gmName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.gridMember?.tel || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="公众监督员信息">
        <el-tag size="small">{{ detail.supervisor?.realName || "-" }}</el-tag>
        <el-tag size="small">{{ sexText(detail.supervisor?.sex) }}</el-tag>
        <el-tag size="small">{{ detail.supervisor?.birthday || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="公众监督员反馈信息描述">{{ detail.information || "-" }}</el-descriptions-item>
      <el-descriptions-item label="备注">{{ detail.remarks || "-" }}</el-descriptions-item>
    </el-descriptions>
  </section>
</template>

<script setup>
import { inject, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const route = useRoute();
const router = useRouter();
const detail = ref({});
const loading = ref(false);

const loadDetail = async () => {
  loading.value = true;
  try {
    const res = await axios.post("statistics/getStatisticsById", { id: Number(route.query.id) });
    detail.value = res.data || {};
  } catch (error) {
    console.error(error);
    myElMessage?.("详情加载失败", "error");
  } finally {
    loading.value = false;
  }
};

const sexText = (sex) => (sex === 1 ? "男" : sex === 0 ? "女" : "-");

onMounted(loadDetail);
</script>

<style scoped>
.page {
  max-width: 980px;
}

.detail {
  margin-top: 18px;
}

.detail :deep(.el-tag) {
  margin-right: 8px;
  margin-bottom: 4px;
}
</style>
