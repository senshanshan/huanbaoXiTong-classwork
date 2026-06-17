<template>
  <section class="dashboard-page">
    <el-breadcrumb>
      <el-breadcrumb-item>仪表盘</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="dashboard-title">
      <div>
        <h2>统计数据仪表盘</h2>
        <p>汇总省份分项、AQI 分布、AQI 趋势和网格覆盖数据</p>
      </div>
      <el-button type="primary" :loading="loading" @click="loadData">刷新数据</el-button>
    </div>

    <el-alert v-if="errorText" class="dashboard-alert" :title="errorText" type="error" show-icon :closable="false" />

    <div class="metric-grid" v-loading="loading">
      <div class="metric-item">
        <span>空气质量检测总数量</span>
        <strong>{{ otherStats.aqiCount }}</strong>
      </div>
      <div class="metric-item">
        <span>空气质量检测良好数量</span>
        <strong>{{ otherStats.aqiGoodCount }}</strong>
      </div>
      <div class="metric-item">
        <span>省网格覆盖率</span>
        <strong>{{ formatPercent(otherStats.provinceCoverage) }}</strong>
      </div>
      <div class="metric-item">
        <span>大城市网格覆盖率</span>
        <strong>{{ formatPercent(otherStats.cityCoverage) }}</strong>
      </div>
    </div>

    <div class="dashboard-grid" v-loading="loading">
      <section class="panel panel-wide">
        <div class="panel-heading">
          <h3>省份污染物超标累计</h3>
          <span>SO2 / CO / SPM2.5</span>
        </div>

        <div class="bar-chart" v-if="provinceRows.length">
          <div v-for="item in provinceRows" :key="item.provinceId" class="bar-row">
            <div class="bar-label">{{ item.provinceName }}</div>
            <div class="bar-track">
              <i class="bar so2" :style="{ width: getBarWidth(item.so2Total) }"></i>
              <i class="bar co" :style="{ width: getBarWidth(item.coTotal) }"></i>
              <i class="bar spm" :style="{ width: getBarWidth(item.spmTotal) }"></i>
            </div>
            <div class="bar-total">{{ item.aqiTotal }}</div>
          </div>
        </div>
        <el-empty v-else description="暂无省份统计数据" />

        <div class="legend">
          <span><i class="so2"></i>SO2</span>
          <span><i class="co"></i>CO</span>
          <span><i class="spm"></i>SPM2.5</span>
          <span class="legend-note">右侧数字为 AQI 超标累计</span>
        </div>
      </section>

      <section class="panel">
        <div class="panel-heading">
          <h3>AQI 指数分布</h3>
          <span>按等级统计</span>
        </div>

        <div class="pie-layout" v-if="aqiRows.length">
          <svg class="pie-chart" viewBox="0 0 120 120" role="img" aria-label="AQI 指数分布饼图">
            <circle cx="60" cy="60" r="45" fill="none" stroke="#edf2f7" stroke-width="22" />
            <circle
              v-for="segment in pieSegments"
              :key="segment.aqiId"
              cx="60"
              cy="60"
              r="45"
              fill="none"
              :stroke="segment.color"
              stroke-width="22"
              :stroke-dasharray="`${segment.length} ${circumference - segment.length}`"
              :stroke-dashoffset="segment.offset"
              stroke-linecap="butt"
              transform="rotate(-90 60 60)"
            />
            <text x="60" y="56" text-anchor="middle" class="pie-number">{{ distributionTotal }}</text>
            <text x="60" y="72" text-anchor="middle" class="pie-label">总数</text>
          </svg>

          <div class="pie-list">
            <div v-for="item in aqiRows" :key="item.aqiId" class="pie-list-item">
              <span><i :style="{ backgroundColor: getAqiColor(item.aqiId) }"></i>{{ getAqiName(item) }}</span>
              <strong>{{ item.total }}</strong>
            </div>
          </div>
        </div>
        <el-empty v-else description="暂无 AQI 分布数据" />
      </section>

      <section class="panel">
        <div class="panel-heading">
          <h3>AQI 超标趋势</h3>
          <span>近 6 个月</span>
        </div>

        <svg v-if="trendRows.length" class="trend-chart" viewBox="0 0 360 190" role="img" aria-label="AQI 超标趋势图">
          <polyline class="trend-grid" points="32,28 340,28" />
          <polyline class="trend-grid" points="32,78 340,78" />
          <polyline class="trend-grid" points="32,128 340,128" />
          <polyline class="trend-line" :points="trendPolyline" />
          <g v-for="point in trendPoints" :key="point.month">
            <circle class="trend-dot" :cx="point.x" :cy="point.y" r="4" />
            <text class="trend-value" :x="point.x" :y="point.y - 10" text-anchor="middle">{{ point.total }}</text>
            <text class="trend-month" :x="point.x" y="170" text-anchor="middle">{{ point.month.slice(5) }}</text>
          </g>
        </svg>
        <el-empty v-else description="暂无 AQI 趋势数据" />
      </section>
    </div>

    <section class="table-panel" v-loading="loading">
      <div class="panel-heading">
        <h3>省份分项统计表</h3>
        <span>用于核对图表数据来源</span>
      </div>
      <el-table :data="provinceRows" empty-text="暂无数据" height="320">
        <el-table-column prop="provinceName" label="省份" min-width="110" />
        <el-table-column prop="so2Total" label="SO2 超标累计" min-width="130" />
        <el-table-column prop="coTotal" label="CO 超标累计" min-width="120" />
        <el-table-column prop="spmTotal" label="SPM2.5 超标累计" min-width="150" />
        <el-table-column prop="aqiTotal" label="AQI 超标累计" min-width="130" />
      </el-table>
    </section>
  </section>
</template>

<script setup>
import { computed, inject, onMounted, ref } from "vue";

const axios = inject("axios");
const myElMessage = inject("myElMessage");

const loading = ref(false);
const errorText = ref("");
const provinceRows = ref([]);
const aqiRows = ref([]);
const trendRows = ref([]);
const otherStats = ref({
  aqiCount: 0,
  aqiGoodCount: 0,
  provinceCoverage: "0%",
  cityCoverage: "0%"
});

const aqiColors = ["#22c55e", "#facc15", "#fb923c", "#ef4444", "#a855f7", "#7f1d1d"];
const circumference = 2 * Math.PI * 45;

const maxPollutionTotal = computed(() => {
  const values = provinceRows.value.flatMap((item) => [item.so2Total, item.coTotal, item.spmTotal].map(Number));
  return Math.max(...values, 1);
});

const distributionTotal = computed(() => aqiRows.value.reduce((sum, item) => sum + Number(item.total || 0), 0));

const pieSegments = computed(() => {
  let usedLength = 0;
  return aqiRows.value.map((item) => {
    const length = distributionTotal.value ? (Number(item.total || 0) / distributionTotal.value) * circumference : 0;
    const segment = {
      aqiId: item.aqiId,
      color: getAqiColor(item.aqiId),
      length,
      offset: -usedLength
    };
    usedLength += length;
    return segment;
  });
});

const trendMax = computed(() => Math.max(...trendRows.value.map((item) => Number(item.total || 0)), 1));

const trendPoints = computed(() => {
  const count = trendRows.value.length;
  if (!count) return [];
  const chartWidth = 308;
  return trendRows.value.map((item, index) => {
    const x = count === 1 ? 186 : 32 + (chartWidth / (count - 1)) * index;
    const y = 148 - (Number(item.total || 0) / trendMax.value) * 112;
    return {
      month: item.month,
      total: item.total,
      x,
      y
    };
  });
});

const trendPolyline = computed(() => trendPoints.value.map((point) => `${point.x},${point.y}`).join(" "));

const formatPercent = (value) => {
  if (value === null || value === undefined || value === "") return "0%";
  return String(value).includes("%") ? value : `${value}%`;
};

const getAqiColor = (aqiId) => aqiColors[(Number(aqiId) || 1) - 1] || "#64748b";

const getAqiName = (item) => `等级 ${item.aqiId}`;

const getBarWidth = (value) => `${Math.max((Number(value || 0) / maxPollutionTotal.value) * 100, 2)}%`;

const loadData = async () => {
  loading.value = true;
  errorText.value = "";
  try {
    const [province, distribute, trend, count, good, provinceCoverage, cityCoverage] = await Promise.all([
      axios.post("statistics/listProvinceItemTotalStatis", {}),
      axios.post("statistics/listAqiDistributeTotalStatis", {}),
      axios.post("statistics/listAqiTrendTotalStatis", {}),
      axios.post("statistics/getAqiCount", {}),
      axios.post("statistics/getAqiGoodCount", {}),
      axios.post("statistics/getProvinceCoverage", {}),
      axios.post("statistics/getCityCoverage", {})
    ]);

    provinceRows.value = province.data || [];
    aqiRows.value = distribute.data || [];
    trendRows.value = trend.data || [];
    otherStats.value = {
      aqiCount: count.data ?? 0,
      aqiGoodCount: good.data ?? 0,
      provinceCoverage: provinceCoverage.data ?? "0%",
      cityCoverage: cityCoverage.data ?? "0%"
    };
  } catch (error) {
    console.error(error);
    errorText.value = "仪表盘数据加载失败";
    myElMessage?.("仪表盘数据加载失败", "error");
  } finally {
    loading.value = false;
  }
};

onMounted(loadData);
</script>

<style scoped>
.dashboard-page {
  color: #1f2937;
}

.dashboard-title {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 16px;
  margin: 22px 0 18px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e5e7eb;
}

.dashboard-title h2 {
  font-size: 26px;
  font-weight: 700;
  line-height: 1.3;
}

.dashboard-title p {
  margin-top: 8px;
  color: #6b7280;
  font-size: 14px;
}

.dashboard-alert {
  margin-bottom: 16px;
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 14px;
  margin-bottom: 18px;
}

.metric-item {
  min-height: 96px;
  padding: 18px 20px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
}

.metric-item span {
  display: block;
  color: #64748b;
  font-size: 14px;
  line-height: 1.4;
}

.metric-item strong {
  display: block;
  margin-top: 12px;
  color: #0f172a;
  font-size: 30px;
  line-height: 1;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: minmax(520px, 1.4fr) minmax(280px, 0.8fr);
  gap: 18px;
}

.panel,
.table-panel {
  padding: 18px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
}

.panel-wide {
  grid-row: span 2;
}

.panel-heading {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
}

.panel-heading h3 {
  font-size: 18px;
  font-weight: 700;
}

.panel-heading span {
  color: #64748b;
  font-size: 13px;
  white-space: nowrap;
}

.bar-chart {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.bar-row {
  display: grid;
  grid-template-columns: 86px minmax(180px, 1fr) 46px;
  align-items: center;
  gap: 12px;
  min-height: 34px;
}

.bar-label,
.bar-total {
  color: #475569;
  font-size: 13px;
}

.bar-total {
  text-align: right;
}

.bar-track {
  display: grid;
  gap: 4px;
}

.bar {
  display: block;
  height: 8px;
  min-width: 8px;
  border-radius: 999px;
  transition: width 0.25s ease;
}

.so2 {
  background: #2563eb;
}

.co {
  background: #14b8a6;
}

.spm {
  background: #f97316;
}

.legend {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 18px;
  color: #64748b;
  font-size: 13px;
}

.legend span {
  display: inline-flex;
  align-items: center;
  gap: 6px;
}

.legend i {
  width: 9px;
  height: 9px;
  border-radius: 50%;
}

.legend-note {
  color: #94a3b8;
}

.pie-layout {
  display: grid;
  grid-template-columns: 142px 1fr;
  align-items: center;
  gap: 18px;
}

.pie-chart {
  width: 142px;
  height: 142px;
  overflow: visible;
}

.pie-number {
  fill: #0f172a;
  font-size: 16px;
  font-weight: 700;
}

.pie-label {
  fill: #64748b;
  font-size: 9px;
}

.pie-list {
  display: grid;
  gap: 10px;
}

.pie-list-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  color: #475569;
  font-size: 13px;
}

.pie-list-item span {
  display: inline-flex;
  align-items: center;
  min-width: 0;
  gap: 8px;
}

.pie-list-item i {
  width: 10px;
  height: 10px;
  flex: 0 0 auto;
  border-radius: 50%;
}

.pie-list-item strong {
  color: #0f172a;
}

.trend-chart {
  width: 100%;
  min-height: 210px;
}

.trend-grid {
  fill: none;
  stroke: #e5e7eb;
  stroke-width: 1;
}

.trend-line {
  fill: none;
  stroke: #2563eb;
  stroke-width: 3;
  stroke-linecap: round;
  stroke-linejoin: round;
}

.trend-dot {
  fill: #fff;
  stroke: #2563eb;
  stroke-width: 3;
}

.trend-value,
.trend-month {
  fill: #64748b;
  font-size: 11px;
}

.table-panel {
  margin-top: 18px;
}

@media (max-width: 1100px) {
  .metric-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .dashboard-grid {
    grid-template-columns: 1fr;
  }

  .panel-wide {
    grid-row: auto;
  }
}

@media (max-width: 680px) {
  .dashboard-title {
    align-items: stretch;
    flex-direction: column;
  }

  .metric-grid {
    grid-template-columns: 1fr;
  }

  .bar-row {
    grid-template-columns: 72px minmax(120px, 1fr) 40px;
  }

  .pie-layout {
    grid-template-columns: 1fr;
    justify-items: center;
  }
}
</style>
