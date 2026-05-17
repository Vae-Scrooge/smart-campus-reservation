<template>
  <div class="reservations">
    <h2 class="page-title">我的预约</h2>

    <el-table :data="reservations" stripe style="width: 100%" v-loading="loading">
      <el-table-column label="资源名称" min-width="160">
        <template #default="{ row }">{{ row.resource?.name }}</template>
      </el-table-column>
      <el-table-column label="类型" width="80">
        <template #default="{ row }">{{ row.resource?.type }}</template>
      </el-table-column>
      <el-table-column label="日期" width="120">
        <template #default="{ row }">{{ row.date }}</template>
      </el-table-column>
      <el-table-column label="时间" width="160">
        <template #default="{ row }">{{ row.startTime }} - {{ row.endTime }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'PENDING'" type="warning">待签到</el-tag>
          <el-tag v-else-if="row.status === 'CHECKED_IN'" type="success">已签到</el-tag>
          <el-tag v-else-if="row.status === 'COMPLETED'" type="info">已完成</el-tag>
          <el-tag v-else-if="row.status === 'CANCELLED'" type="danger">已取消</el-tag>
          <el-tag v-else-if="row.status === 'NO_SHOW'" type="danger">未签到</el-tag>
          <el-tag v-else>{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" type="success" @click="handleCheckIn(row.id)"
            :disabled="row.status !== 'PENDING'">
            签到
          </el-button>
          <el-button size="small" type="danger" @click="handleCancel(row.id)"
            :disabled="row.status !== 'PENDING'">
            取消
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-empty v-if="reservations.length === 0 && !loading" description="暂无预约记录" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyReservations, cancelReservation, checkIn } from '../api/reservation'

const reservations = ref([])
const loading = ref(false)

async function loadReservations() {
  loading.value = true
  try {
    const res = await getMyReservations()
    if (res.code === 200) {
      reservations.value = res.data
    }
  } finally {
    loading.value = false
  }
}

async function handleCheckIn(id) {
  try {
    const res = await checkIn(id)
    if (res.code === 200) {
      ElMessage.success('签到成功，信用分+2')
      await loadReservations()
    }
  } catch (e) {
    // ignore
  }
}

async function handleCancel(id) {
  try {
    const res = await cancelReservation(id)
    if (res.code === 200) {
      ElMessage.success('已取消')
      await loadReservations()
    }
  } catch (e) {
    // ignore
  }
}

onMounted(loadReservations)
</script>

<style scoped>
.reservations { max-width: 1200px; margin: 0 auto; }
</style>
