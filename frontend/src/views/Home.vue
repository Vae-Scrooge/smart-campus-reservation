<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="welcome-card">
          <h1 class="welcome-title">欢迎使用智能校园预约系统</h1>
          <p class="welcome-desc">
            高效、智能、公平的校园资源管理平台。自习室、实验室、会议室，一键预约。
          </p>
          <el-button type="primary" size="large" @click="$router.push('/resources')">
            <el-icon style="margin-right: 6px"><Search /></el-icon>
            立即预约
          </el-button>
          <el-button size="large" @click="$router.push('/reservations')" v-if="userStore.isLoggedIn">
            <el-icon style="margin-right: 6px"><Calendar /></el-icon>
            我的预约
          </el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="stat-row">
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-value">{{ resourceCount }}</div>
          <div class="stat-label">可用资源</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-value">{{ userStore.isLoggedIn ? userStore.creditScore : '--' }}</div>
          <div class="stat-label">我的信用分</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-value">100%</div>
          <div class="stat-label">数据安全保障</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header><b><el-icon><Timer /></el-icon> 创新点一</b></template>
          <p>Redis双机制应对高并发——日常轻量锁 + 高峰期红锁，保障系统稳定。</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header><b><el-icon><Refresh /></el-icon> 创新点二</b></template>
          <p>智能释放 + 信用分机制——No-show自动释放资源，用激励引导自律。</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header><b><el-icon><DataAnalysis /></el-icon> 创新点三</b></template>
          <p>机器学习预测需求——提前预判资源热度，个性化推荐。</p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import { getResources } from '../api/resource'

const userStore = useUserStore()
const resourceCount = ref(0)

onMounted(async () => {
  try {
    const res = await getResources()
    if (res.code === 200) {
      resourceCount.value = res.data.length
    }
  } catch (e) {
    // ignore
  }
})
</script>

<style scoped>
.home { max-width: 1200px; margin: 0 auto; }
.welcome-card { text-align: center; padding: 40px 20px; margin-bottom: 20px; }
.welcome-title { font-size: 28px; color: #1a365d; margin-bottom: 12px; }
.welcome-desc { font-size: 16px; color: #666; margin-bottom: 24px; }
.stat-row { margin-bottom: 20px; }
.stat-card { text-align: center; padding: 20px; }
.stat-value { font-size: 32px; font-weight: 700; color: #409eff; }
.stat-label { font-size: 14px; color: #909399; margin-top: 8px; }
</style>
