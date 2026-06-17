<template>
  <section class="page" v-loading="loading">
    <el-breadcrumb>
      <el-breadcrumb-item>公众监督数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>公众监督数据详情</el-breadcrumb-item>
    </el-breadcrumb>

    <el-descriptions class="detail" title="公众监督数据详情" :column="1" border>
      <template #extra>
        <el-button type="primary" @click="router.back()">返回</el-button>
      </template>
      <el-descriptions-item label="公众监督反馈信息编号">{{ detail.afId || "-" }}</el-descriptions-item>
      <el-descriptions-item label="反馈者信息">
        <el-tag size="small">{{ detail.supervisor?.realName || "-" }}</el-tag>
        <el-tag size="small">{{ sexText(detail.supervisor?.sex) }}</el-tag>
        <el-tag size="small">{{ detail.supervisor?.birthday || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="反馈者联系电话">{{ detail.telId || "-" }}</el-descriptions-item>
      <el-descriptions-item label="反馈信息所在地址">
        <el-tag size="small">{{ detail.gridProvince?.provinceName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.gridCity?.cityName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.address || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="反馈信息描述">{{ detail.information || "-" }}</el-descriptions-item>
      <el-descriptions-item label="预估等级">
        <el-tag size="small" :color="detail.aqi?.color" effect="dark">{{ detail.aqi?.chineseExplain || detail.estimatedGrade || "-" }}</el-tag>
        <el-tag size="small">{{ detail.aqi?.aqiExplain || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="反馈日期时间">
        <el-tag size="small">{{ detail.afDate || "-" }}</el-tag>
        <el-tag size="small">{{ detail.afTime || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="当前状态">{{ stateText(detail.state) }}</el-descriptions-item>
      <el-descriptions-item label="备注">{{ detail.remarks || "-" }}</el-descriptions-item>
    </el-descriptions>

    <el-alert
      v-if="detail.state === 1 && detail.gridMember"
      class="alert"
      :title="`已指派网格员：${detail.gridMember.gmName}（电话号码：${detail.gridMember.tel || '-'}）`"
      type="success"
      center
      show-icon
    />

    <div class="actions">
      <el-button v-if="detail.state !== 2" type="success" @click="router.push({ path: '/index/assignFeedback', query: { afId: detail.afId } })">
        指派网格员
      </el-button>
    </div>
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
    const res = await axios.post("aqiFeedback/getAqiFeedbackById", { afId: Number(route.query.afId) });
    detail.value = res.data || {};
  } catch (error) {
    console.error(error);
    myElMessage?.("详情加载失败", "error");
  } finally {
    loading.value = false;
  }
};

const sexText = (sex) => (sex === 1 ? "男" : sex === 0 ? "女" : "-");
const stateText = (state) => (state === 2 ? "已确认" : state === 1 ? "已指派" : "未指派");

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
}

.alert,
.actions {
  margin-top: 18px;
}

.actions {
  text-align: center;
}
</style>
