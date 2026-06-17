<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-angle-left" @click="goBack"></i>
        <p>选择网格</p>
        <i class="fa fa-list-ul" @click="toFeedbackList"></i>
      </header>
      <div class="top-ban"></div>
      <img src="../assets/header.png" alt="顶部背景图" />
    </div>

    <table class="table-form">
      <tbody>
      <tr>
        <td>所在省份</td>
        <td>
          <select v-model="gridForm.provinceId" @change="onProvinceChange">
            <option value="">请选择省份</option>
            <option
              v-for="province in provinceArr"
              :key="province.provinceId"
              :value="province.provinceId"
            >
              {{ province.provinceName }}
            </option>
          </select>
        </td>
      </tr>
      <tr>
        <td>所在城市</td>
        <td>
          <select v-model="gridForm.cityId">
            <option value="">请选择城市</option>
            <option
              v-for="city in filteredCityArr"
              :key="city.cityId"
              :value="city.cityId"
            >
              {{ city.cityName }}
            </option>
          </select>
        </td>
      </tr>
      <tr>
        <td>详细地址</td>
        <td>
          <textarea
            v-model="gridForm.address"
            placeholder="请填写污染发生位置或附近标志物"
          ></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <div class="btn" @click="nextStep">下一步</div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { computed, inject, onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";

const axios = inject("axios");
const router = useRouter();

const provinceArr = ref([]);
const cityArr = ref([]);
const gridForm = reactive({
  provinceId: "",
  cityId: "",
  address: "",
});

const filteredCityArr = computed(() => {
  if (!gridForm.provinceId) {
    return [];
  }
  return cityArr.value.filter(
    (city) => String(city.provinceId) === String(gridForm.provinceId)
  );
});

const goBack = () => {
  router.back();
};

const toFeedbackList = () => {
  router.push("/feedbackList");
};

const onProvinceChange = () => {
  gridForm.cityId = "";
};

const init = async () => {
  try {
    const [provinceRes, cityRes] = await Promise.all([
      axios.post("gridProvince/list"),
      axios.post("gridCity/list"),
    ]);
    provinceArr.value = provinceRes.data || [];
    cityArr.value = cityRes.data || [];
  } catch (error) {
    console.error("加载网格数据失败", error);
    alert("加载网格数据失败，请稍后重试");
  }
};

const nextStep = () => {
  if (!gridForm.provinceId) {
    alert("请选择省份");
    return;
  }
  if (!gridForm.cityId) {
    alert("请选择城市");
    return;
  }
  if (!gridForm.address.trim()) {
    alert("请填写详细地址");
    return;
  }

  router.push({
    path: "/selectAqi",
    query: {
      provinceId: gridForm.provinceId,
      cityId: gridForm.cityId,
      address: gridForm.address.trim(),
    },
  });
};

onMounted(init);
</script>

<style scoped>
.wrapper {
  min-height: 100vh;
  background-color: #cfe8e9;
}

.table-form select,
.table-form textarea {
  width: 100%;
  font-size: 4vw;
  color: #333;
}

.table-form select {
  height: 9vw;
}

.table-form textarea {
  min-height: 22vw;
  padding: 2vw;
  border-radius: 1.4vw;
  resize: vertical;
  line-height: 1.5;
}
</style>
