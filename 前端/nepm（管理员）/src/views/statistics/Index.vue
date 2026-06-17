<template>
  <el-container class="layout-container">
    <el-header class="layout-header">
      <div class="header-title">
        <el-icon class="brand-icon"><Phone /></el-icon>
        <h1>东软环保公众监督平台管理系统</h1>
      </div>
      <div class="admin-info">系统管理员：{{ admins?.adminName || admins?.adminCode || "admin" }}</div>
    </el-header>

    <el-container class="layout-body">
      <div v-if="!isCollapse && isMobile" class="sidebar-overlay" @click="toggleSidebar"></div>

      <el-aside :class="['layout-sidebar', { collapsed: isCollapse }]">
        <el-menu
          :collapse="isCollapse"
          :default-active="$route.path"
          active-text-color="#ffd04b"
          background-color="#79bbff"
          router
          text-color="#fff"
        >
          <el-sub-menu index="feedback">
            <template #title>
              <el-icon><ChatDotRound /></el-icon>
              <span>公众监督数据管理</span>
            </template>
            <el-menu-item index="/index/feedbackList" @click="onMenuItemClick">公众监督数据列表</el-menu-item>
            <el-menu-item index="/index/statisticsList" @click="onMenuItemClick">确认 AQI 数据列表</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="statistics">
            <template #title>
              <el-icon><DocumentCopy /></el-icon>
              <span>统计数据管理</span>
            </template>
            <el-menu-item index="/index/provinceItemTotalStatis" @click="onMenuItemClick">省分组检查统计</el-menu-item>
            <el-menu-item index="/index/aqiDistributeTotalStatis" @click="onMenuItemClick">AQI 指数分布统计</el-menu-item>
            <el-menu-item index="/index/aqiTrendTotalStatis" @click="onMenuItemClick">AQI 指数趋势统计</el-menu-item>
            <el-menu-item index="/index/otherTotalStatis" @click="onMenuItemClick">其它数据统计</el-menu-item>
          </el-sub-menu>
        </el-menu>

        <button class="collapse-handle" type="button" @click="toggleSidebar">
          <el-icon>
            <ArrowRight v-if="isCollapse" />
            <ArrowLeft v-else />
          </el-icon>
        </button>
      </el-aside>

      <el-main class="layout-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ArrowLeft, ArrowRight, ChatDotRound, DocumentCopy, Phone } from "@element-plus/icons-vue";
import { getSessionStorage } from "../../common.js";
import { onMounted, onUnmounted, ref } from "vue";

const admins = getSessionStorage("admins");
const isCollapse = ref(false);
const isMobile = ref(false);

const checkMobile = () => {
  isMobile.value = window.innerWidth < 768;
  isCollapse.value = isMobile.value;
};

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value;
};

const onMenuItemClick = () => {
  if (isMobile.value) isCollapse.value = true;
};

onMounted(() => {
  checkMobile();
  window.addEventListener("resize", checkMobile);
});

onUnmounted(() => {
  window.removeEventListener("resize", checkMobile);
});
</script>

<style scoped>
.layout-container {
  width: 100%;
  height: 100vh;
}

.layout-header {
  height: 56px !important;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 28px;
  color: #fff;
  background: #409eff;
}

.header-title {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 0;
}

.brand-icon {
  font-size: 34px;
  transform: rotate(-15deg);
  flex: 0 0 auto;
}

.header-title h1 {
  font-size: 28px;
  font-weight: 500;
  line-height: 1;
  white-space: nowrap;
}

.admin-info {
  font-size: 18px;
  font-weight: 600;
  white-space: nowrap;
}

.layout-body {
  height: calc(100vh - 56px);
  overflow: hidden;
  position: relative;
}

.layout-sidebar {
  width: 260px !important;
  position: relative;
  background: #79bbff;
  transition: width 0.2s;
  overflow: visible;
}

.layout-sidebar.collapsed {
  width: 58px !important;
}

.layout-sidebar :deep(.el-menu) {
  border-right: 0;
}

.layout-sidebar :deep(.el-menu-item),
.layout-sidebar :deep(.el-sub-menu__title) {
  font-size: 18px;
  height: 64px;
  line-height: 64px;
}

.layout-sidebar :deep(.el-sub-menu .el-menu-item) {
  padding-left: 58px !important;
}

.collapse-handle {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 40px;
  height: 52px;
  border: 0;
  border-radius: 0 26px 26px 0;
  color: #dbeeff;
  background: #3f6f96;
  cursor: pointer;
  z-index: 20;
}

.layout-main {
  padding: 28px;
  overflow: auto;
  background: #fff;
}

.sidebar-overlay {
  display: none;
}

@media (max-width: 767px) {
  .layout-header {
    padding: 0 12px;
  }

  .header-title h1 {
    font-size: 17px;
  }

  .brand-icon,
  .admin-info {
    font-size: 14px;
  }

  .layout-sidebar.collapsed {
    width: 0 !important;
  }

  .layout-sidebar:not(.collapsed) {
    position: fixed;
    top: 56px;
    left: 0;
    bottom: 0;
    z-index: 200;
  }

  .sidebar-overlay {
    display: block;
    position: fixed;
    inset: 56px 0 0;
    background: rgba(0, 0, 0, 0.35);
    z-index: 100;
  }
}
</style>
