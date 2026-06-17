<template>
  <section class="page">
    <el-breadcrumb>
      <el-breadcrumb-item>确认 AQI 数据管理</el-breadcrumb-item>
      <el-breadcrumb-item>确认 AQI 数据列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="query-card" shadow="never">
      <el-form :inline="true" :model="query" size="small">
        <el-form-item label="省区域">
          <el-select v-model="query.provinceId" clearable placeholder="全部" style="width: 130px" @change="changeProvince">
            <el-option v-for="province in provinces" :key="province.provinceId" :label="province.provinceName" :value="province.provinceId" />
          </el-select>
        </el-form-item>
        <el-form-item label="市区域">
          <el-select v-model="query.cityId" clearable placeholder="全部" style="width: 130px">
            <el-option v-for="city in filteredCities" :key="city.cityId" :label="city.cityName" :value="city.cityId" />
          </el-select>
        </el-form-item>
        <el-form-item label="确认日期">
          <el-date-picker v-model="query.confirmDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" style="width: 150px" />
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="resetQuery">清空</el-button>
          <el-button type="primary" @click="queryData">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-table :data="rows" border stripe v-loading="loading" :cell-style="{ padding: '6px' }" empty-text="无数据">
      <el-table-column prop="id" label="编号" width="80" />
      <el-table-column prop="gridProvince.provinceName" label="所在省" min-width="110" />
      <el-table-column prop="gridCity.cityName" label="所在市" min-width="110" />
      <el-table-column prop="address" label="详细地址" min-width="180" show-overflow-tooltip />
      <el-table-column label="AQI 等级" min-width="150">
        <template #default="{ row }">
          <span class="aqi-text" :style="{ color: row.aqi?.color || '#303133' }">
            {{ row.aqi?.aqiExplain || "-" }}（{{ row.aqi?.chineseExplain || row.aqiId || "-" }}）
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="confirmDate" label="确认日期" width="110" />
      <el-table-column prop="confirmTime" label="确认时间" width="110" />
      <el-table-column prop="gridMember.gmName" label="网格员" width="110" />
      <el-table-column prop="supervisor.realName" label="反馈者" width="110" />
      <el-table-column label="操作" width="95" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" size="small" @click="router.push({ path: '/index/statisticsInfo', query: { id: row.id } })">详情</el-button>
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
const query = reactive({ provinceId: null, cityId: null, confirmDate: "" });
const page = reactive({ pageNum: 1, maxPageNum: 8, totalRow: 0 });

const filteredCities = computed(() => {
  if (!query.provinceId) return cities.value;
  return cities.value.filter((city) => city.provinceId === query.provinceId);
});

const loadRegions = async () => {
  try {
    const [provinceRes, cityRes] = await Promise.all([axios.post("gridProvince/list"), axios.post("gridCity/list")]);
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

const loadData = async () => {
  loading.value = true;
  try {
    const res = await axios.post("statistics/listStatisticsPage", {
      provinceId: query.provinceId,
      cityId: query.cityId,
      confirmDate: query.confirmDate || null,
      pageNum: page.pageNum,
      maxPageNum: page.maxPageNum
    });
    rows.value = res.data?.list || [];
    page.totalRow = res.data?.totalRow || 0;
  } catch (error) {
    console.error(error);
    myElMessage?.("确认 AQI 数据加载失败", "error");
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
  query.confirmDate = "";
  queryData();
};

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
</style>
