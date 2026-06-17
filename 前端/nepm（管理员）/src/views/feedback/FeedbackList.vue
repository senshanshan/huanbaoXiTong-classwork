<template>
  <section class="page">
    <el-breadcrumb>
      <el-breadcrumb-item>公众监督数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>公众监督数据列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="query-card" shadow="never">
      <el-form :inline="true" :model="query" size="small">
        <el-form-item label="省区域">
          <el-select
            v-model="query.provinceId"
            clearable
            placeholder="全部"
            style="width: 130px"
            @change="changeProvince"
          >
            <el-option
              v-for="province in provinces"
              :key="province.provinceId"
              :label="province.provinceName"
              :value="province.provinceId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="市区域">
          <el-select v-model="query.cityId" clearable placeholder="全部" style="width: 130px">
            <el-option
              v-for="city in filteredCities"
              :key="city.cityId"
              :label="city.cityName"
              :value="city.cityId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="预估等级">
          <el-select v-model="query.estimatedGrade" clearable placeholder="全部" style="width: 120px">
            <el-option
              v-for="item in aqiOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="反馈日期">
          <el-date-picker
            v-model="query.afDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.state" clearable placeholder="全部" style="width: 120px">
            <el-option label="未指派" :value="0" />
            <el-option label="已指派" :value="1" />
            <el-option label="已确认" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="resetQuery">清空</el-button>
          <el-button type="primary" @click="queryData">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-table
      :data="rows"
      border
      stripe
      v-loading="loading"
      :cell-style="{ padding: '6px' }"
      empty-text="无数据"
    >
      <el-table-column prop="afId" label="编号" width="80" />
      <el-table-column prop="gridProvince.provinceName" label="所在省" min-width="110" />
      <el-table-column prop="gridCity.cityName" label="所在市" min-width="110" />
      <el-table-column prop="address" label="详细地址" min-width="180" show-overflow-tooltip />
      <el-table-column label="预估等级" min-width="170">
        <template #default="{ row }">
          <span class="aqi-text" :style="{ color: row.aqi?.color || '#303133' }">
            {{ row.aqi?.aqiExplain || "-" }}（{{ row.aqi?.chineseExplain || row.estimatedGrade || "-" }}）
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="afDate" label="反馈日期" width="110" />
      <el-table-column prop="afTime" label="反馈时间" width="110" />
      <el-table-column prop="supervisor.realName" label="反馈者" width="110" />
      <el-table-column label="状态" width="95">
        <template #default="{ row }">
          <el-tag :type="stateType(row.state)" size="small">{{ stateText(row.state) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="130" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" size="small" @click="goInfo(row.afId)">详情</el-button>
          <el-button link type="success" size="small" :disabled="row.state === 2" @click="goAssign(row.afId)">
            指派
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        v-model:current-page="page.pageNum"
        background
        small
        layout="prev, pager, next, total"
        :page-size="page.maxPageNum"
        :total="page.totalRow"
        @current-change="loadData"
      />
    </div>
  </section>
</template>

<script setup>
import { computed, inject, onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";

const axios = inject("axios");
const myElMessage = inject("myElMessage");
const router = useRouter();

const loading = ref(false);
const rows = ref([]);
const provinces = ref([]);
const cities = ref([]);
const query = reactive({
  provinceId: null,
  cityId: null,
  estimatedGrade: null,
  afDate: "",
  state: null,
});
const page = reactive({ pageNum: 1, maxPageNum: 8, totalRow: 0 });

const aqiOptions = [
  { label: "一级", value: 1 },
  { label: "二级", value: 2 },
  { label: "三级", value: 3 },
  { label: "四级", value: 4 },
  { label: "五级", value: 5 },
  { label: "六级", value: 6 },
];

const filteredCities = computed(() => {
  if (!query.provinceId) return cities.value;
  return cities.value.filter((city) => city.provinceId === query.provinceId);
});

const loadRegions = async () => {
  try {
    const [provinceRes, cityRes] = await Promise.all([
      axios.post("gridProvince/list"),
      axios.post("gridCity/list"),
    ]);
    provinces.value = provinceRes.data || [];
    cities.value = cityRes.data || [];
  } catch (error) {
    console.error(error);
    myElMessage?.("区域数据加载失败", "error");
  }
};

const changeProvince = () => {
  query.cityId = null;
};

const buildParams = () => ({
  provinceId: query.provinceId,
  cityId: query.cityId,
  estimatedGrade: query.estimatedGrade,
  afDate: query.afDate || null,
  state: query.state,
  pageNum: page.pageNum,
  maxPageNum: page.maxPageNum,
});

const loadData = async () => {
  loading.value = true;
  try {
    const res = await axios.post("aqiFeedback/listAqiFeedbackPage", buildParams());
    rows.value = res.data?.list || [];
    page.totalRow = res.data?.totalRow || 0;
  } catch (error) {
    console.error(error);
    myElMessage?.("公众监督数据加载失败", "error");
  } finally {
    loading.value = false;
  }
};

const queryData = () => {
  page.pageNum = 1;
  loadData();
};

const resetQuery = () => {
  query.provinceId = null;
  query.cityId = null;
  query.estimatedGrade = null;
  query.afDate = "";
  query.state = null;
  queryData();
};

const stateText = (state) => {
  if (state === 2) return "已确认";
  if (state === 1) return "已指派";
  return "未指派";
};

const stateType = (state) => {
  if (state === 2) return "success";
  if (state === 1) return "warning";
  return "info";
};

const goInfo = (afId) => router.push({ path: "/index/feedbackInfo", query: { afId } });
const goAssign = (afId) => router.push({ path: "/index/assignFeedback", query: { afId } });

onMounted(async () => {
  await loadRegions();
  loadData();
});
</script>

<style scoped>
.page {
  width: 100%;
}

.query-card {
  margin: 14px 0;
}

.aqi-text {
  font-weight: 700;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 18px;
}

@media (max-width: 768px) {
  .query-card :deep(.el-form-item) {
    display: block;
    margin-right: 0;
  }
}
</style>
