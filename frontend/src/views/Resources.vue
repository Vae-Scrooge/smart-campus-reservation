<template>
  <div class="resources">
    <h2 class="page-title">资源预约</h2>

    <el-tabs v-model="activeType" @tab-change="loadResources">
      <el-tab-pane label="全部" name="all"></el-tab-pane>
      <el-tab-pane label="自习室" name="自习室"></el-tab-pane>
      <el-tab-pane label="实验室" name="实验室"></el-tab-pane>
      <el-tab-pane label="会议室" name="会议室"></el-tab-pane>
    </el-tabs>

    <el-row :gutter="16">
      <el-col v-for="item in resources" :key="item.id" :xs="24" :sm="12" :md="8" :lg="6">
        <el-card class="resource-card" shadow="hover">
          <div class="resource-type">{{ item.type }}</div>
          <h3 class="resource-name">{{ item.name }}</h3>
          <p class="resource-location">
            <el-icon><Location /></el-icon> {{ item.location }}
          </p>
          <p class="resource-info">
            <el-icon><UserFilled /></el-icon> 容量: {{ item.capacity }}人
          </p>
          <p class="resource-info" v-if="item.description">
            {{ item.description }}
          </p>
          <el-tag :type="item.status === 'AVAILABLE' ? 'success' : 'danger'" size="small">
            {{ item.status === 'AVAILABLE' ? '可预约' : '维护中' }}
          </el-tag>

          <el-button
            type="primary"
            size="small"
            style="margin-top: 12px; width: 100%"
            :disabled="item.status !== 'AVAILABLE'"
            @click="openReserveDialog(item)"
          >
            立即预约
          </el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="resources.length === 0" description="暂无可用资源" />

    <!-- 预约对话框 -->
    <el-dialog v-model="dialogVisible" title="预约资源" width="420px">
      <el-form :model="reserveForm" label-width="100px">
        <el-form-item label="资源">
          <span>{{ currentResource?.name }}</span>
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker v-model="reserveForm.date" type="date" placeholder="选择日期"
            :disabled-date="disabledDate" style="width: 100%" />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-time-picker v-model="reserveForm.startTime" placeholder="开始时间"
            format="HH:mm" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-time-picker v-model="reserveForm.endTime" placeholder="结束时间"
            format="HH:mm" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReserve" :loading="reserving">确认预约</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getResources, getResourcesByType } from '../api/resource'
import { createReservation } from '../api/reservation'

const activeType = ref('all')
const resources = ref([])
const dialogVisible = ref(false)
const currentResource = ref(null)
const reserving = ref(false)

const reserveForm = ref({
  resourceId: null,
  date: '',
  startTime: '',
  endTime: '',
})

function disabledDate(time) {
  return time.getTime() < Date.now() - 86400000
}

async function loadResources() {
  try {
    const res = activeType.value === 'all'
      ? await getResources()
      : await getResourcesByType(activeType.value)
    if (res.code === 200) {
      resources.value = res.data
    }
  } catch (e) {
    // ignore
  }
}

function openReserveDialog(resource) {
  currentResource.value = resource
  reserveForm.value = {
    resourceId: resource.id,
    date: '',
    startTime: '',
    endTime: '',
  }
  dialogVisible.value = true
}

async function handleReserve() {
  if (!reserveForm.value.date || !reserveForm.value.startTime || !reserveForm.value.endTime) {
    ElMessage.warning('请填写完整的预约信息')
    return
  }

  reserving.value = true
  try {
    const data = {
      resourceId: currentResource.value.id,
      date: reserveForm.value.date.toISOString().split('T')[0],
      startTime: reserveForm.value.startTime.toTimeString().slice(0, 8),
      endTime: reserveForm.value.endTime.toTimeString().slice(0, 8),
    }
    const res = await createReservation(data)
    if (res.code === 200) {
      ElMessage.success('预约成功')
      dialogVisible.value = false
    }
  } finally {
    reserving.value = false
  }
}

onMounted(loadResources)
</script>

<style scoped>
.resources { max-width: 1200px; margin: 0 auto; }
.resource-card { margin-bottom: 16px; }
.resource-type { font-size: 12px; color: #909399; margin-bottom: 4px; }
.resource-name { font-size: 16px; font-weight: 600; margin-bottom: 8px; color: #1a365d; }
.resource-location, .resource-info {
  font-size: 13px; color: #666; margin-bottom: 4px;
  display: flex; align-items: center; gap: 4px;
}
</style>
