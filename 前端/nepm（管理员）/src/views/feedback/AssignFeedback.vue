<template>
  <section class="page" v-loading="loading">
    <el-breadcrumb>
      <el-breadcrumb-item>公众监督数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>指派网格员</el-breadcrumb-item>
    </el-breadcrumb>

    <el-descriptions class="detail" title="指派公众监督数据" :column="1" border>
      <template #extra>
        <el-button type="primary" @click="router.back()">返回</el-button>
      </template>
      <el-descriptions-item label="公众监督反馈信息编号">{{ detail.afId || "-" }}</el-descriptions-item>
      <el-descriptions-item label="反馈信息所在地址">
        <el-tag size="small">{{ detail.gridProvince?.provinceName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.gridCity?.cityName || "-" }}</el-tag>
        <el-tag size="small">{{ detail.address || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="反馈信息描述">{{ detail.information || "-" }}</el-descriptions-item>
      <el-descriptions-item label="预估等级">
        <el-tag size="small">{{ detail.aqi?.chineseExplain || detail.estimatedGrade || "-" }}</el-tag>
        <el-tag size="small">{{ detail.aqi?.aqiExplain || "-" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="反馈日期时间">
        <el-tag size="small">{{ detail.afDate || "-" }}</el-tag>
        <el-tag size="small">{{ detail.afTime || "-" }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>

    <el-card class="assign-card" shadow="never">
      <el-form :inline="true" size="small">
        <el-form-item label="是否异地指派">
          <el-switch v-model="isRemote" @change="switchRemote" />
        </el-form-item>
      </el-form>

      <el-form v-if="!isRemote" :inline="true" size="small">
        <el-form-item label="指派给">
          <el-select v-model="form.gmId" placeholder="请选择网格员" style="width: 220px">
            <el-option v-for="member in localMembers" :key="member.gmId" :label="memberText(member)" :value="member.gmId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-popconfirm title="确定要指派给该网格员吗？" confirm-button-text="确定" cancel-button-text="取消" @confirm="submit">
            <template #reference>
              <el-button type="primary" :loading="submitting">本地指派</el-button>
            </template>
          </el-popconfirm>
        </el-form-item>
      </el-form>

      <el-form v-else :inline="true" size="small">
        <el-form-item label="省区域">
          <el-select v-model="remote.provinceId" placeholder="请选择省" style="width: 140px" @change="changeRemoteProvince">
            <el-option v-for="province in provinces" :key="province.provinceId" :label="province.provinceName" :value="province.provinceId" />
          </el-select>
        </el-form-item>
        <el-form-item label="市区域">
          <el-select v-model="remote.cityId" placeholder="请选择市" style="width: 140px" @change="loadRemoteMembers">
            <el-option v-for="city in remoteCities" :key="city.cityId" :label="city.cityName" :value="city.cityId" />
          </el-select>
        </el-form-item>
        <el-form-item label="异地指派给">
          <el-select v-model="form.gmId" placeholder="请选择网格员" style="width: 220px">
            <el-option v-for="member in remoteMembers" :key="member.gmId" :label="memberText(member)" :value="member.gmId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-popconfirm title="确定要异地指派给该网格员吗？" confirm-button-text="确定" cancel-button-text="取消" @confirm="submit">
            <template #reference>
              <el-button type="primary" :loading="submitting">异地指派</el-button>
            </template>
          </el-popconfirm>
        </el-form-item>
      </el-form>
    </el-card>
  </section>
</template>

<script setup>
import { computed, inject, onMounted, reactive, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const route = useRoute();
const router = useRouter();

const detail = ref({});
const provinces = ref([]);
const cities = ref([]);
const localMembers = ref([]);
const remoteMembers = ref([]);
const loading = ref(false);
const submitting = ref(false);
const isRemote = ref(false);
const form = reactive({ gmId: null });
const remote = reactive({ provinceId: null, cityId: null });

const remoteCities = computed(() => cities.value.filter((city) => city.provinceId === remote.provinceId));

const pad = (num) => String(num).padStart(2, "0");
const nowDateTime = () => {
  const date = new Date();
  return {
    assignDate: `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`,
    assignTime: `${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
  };
};

const memberText = (member) => `${member.gmName || "-"}（${member.gmCode || member.tel || "无编号"}）`;

const loadRegions = async () => {
  const [provinceRes, cityRes] = await Promise.all([axios.post("gridProvince/list"), axios.post("gridCity/list")]);
  provinces.value = provinceRes.data || [];
  cities.value = cityRes.data || [];
};

const loadMembers = async (provinceId, cityId) => {
  if (!provinceId || !cityId) return [];
  const res = await axios.post("gridMember/ListGridMemberByProvinceId", {
    provinceId,
    cityId,
    state: 1
  });
  return res.data || [];
};

const loadDetail = async () => {
  const res = await axios.post("aqiFeedback/getAqiFeedbackById", { afId: Number(route.query.afId) });
  detail.value = res.data || {};
  form.gmId = detail.value.gmId || null;
  remote.provinceId = detail.value.provinceId || null;
  remote.cityId = detail.value.cityId || null;
  localMembers.value = await loadMembers(detail.value.provinceId, detail.value.cityId);
};

const changeRemoteProvince = () => {
  remote.cityId = null;
  form.gmId = null;
  remoteMembers.value = [];
};

const loadRemoteMembers = async () => {
  form.gmId = null;
  remoteMembers.value = await loadMembers(remote.provinceId, remote.cityId);
};

const switchRemote = async () => {
  form.gmId = null;
  if (isRemote.value) {
    remote.provinceId = detail.value.provinceId || null;
    remote.cityId = detail.value.cityId || null;
    remoteMembers.value = await loadMembers(remote.provinceId, remote.cityId);
  }
};

const submit = async () => {
  if (!form.gmId) {
    myElMessage?.("请选择网格员", "warning");
    return;
  }

  submitting.value = true;
  try {
    const res = await axios.post("aqiFeedback/updateAqiFeedbackAssign", {
      afId: detail.value.afId,
      gmId: form.gmId,
      state: 1,
      ...nowDateTime()
    });
    if (Number(res.data) > 0) {
      myElMessage?.("指派成功", "success");
      router.push("/index/feedbackList");
    } else {
      myElMessage?.("指派失败", "error");
    }
  } catch (error) {
    console.error(error);
    myElMessage?.("指派请求失败", "error");
  } finally {
    submitting.value = false;
  }
};

onMounted(async () => {
  loading.value = true;
  try {
    await loadRegions();
    await loadDetail();
  } catch (error) {
    console.error(error);
    myElMessage?.("指派数据加载失败", "error");
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.page {
  max-width: 980px;
}

.detail,
.assign-card {
  margin-top: 18px;
}

.detail :deep(.el-tag) {
  margin-right: 8px;
}

@media (max-width: 768px) {
  .assign-card :deep(.el-form-item) {
    display: block;
    margin-right: 0;
  }
}
</style>
