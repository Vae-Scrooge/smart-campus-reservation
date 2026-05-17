<template>
  <div class="admin">
    <h2 class="page-title">管理后台</h2>

    <el-alert title="此页面仅管理员可见" type="warning" :closable="false" style="margin-bottom: 20px" />

    <el-tabs v-model="activeTab">
      <el-tab-pane label="资源管理" name="resources">
        <el-button type="primary" style="margin-bottom: 16px" @click="showAddDialog = true">
          新增资源
        </el-button>
        <el-table :data="allResources" stripe>
          <el-table-column prop="id" label="ID" width="60" />
          <el-table-column prop="name" label="名称" min-width="140" />
          <el-table-column prop="type" label="类型" width="80" />
          <el-table-column prop="location" label="位置" min-width="160" />
          <el-table-column prop="capacity" label="容量" width="60" />
          <el-table-column prop="status" label="状态" width="80">
            <template #default="{ row }">
              <el-tag :type="row.status === 'AVAILABLE' ? 'success' : 'danger'" size="small">
                {{ row.status === 'AVAILABLE' ? '可用' : '维护' }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="预约记录" name="reservations">
        <el-table :data="allReservations" stripe>
          <el-table-column prop="id" label="ID" width="60" />
          <el-table-column label="用户" width="100">
            <template #default="{ row }">{{ row.user?.username }}</template>
          </el-table-column>
          <el-table-column label="资源" min-width="140">
            <template #default="{ row }">{{ row.resource?.name }}</template>
          </el-table-column>
          <el-table-column prop="date" label="日期" width="120" />
          <el-table-column label="时间" width="140">
            <template #default="{ row }">{{ row.startTime }} - {{ row.endTime }}</template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template #default="{ row }">
              <el-tag size="small">{{
                row.status === 'PENDING' ? '待签到' :
                row.status === 'CHECKED_IN' ? '已签到' :
                row.status === 'COMPLETED' ? '已完成' :
                row.status === 'CANCELLED' ? '已取消' : '未签到'
              }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <!-- 新增资源对话框 -->
    <el-dialog v-model="showAddDialog" title="新增资源" width="450px">
      <el-form :model="newResource" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="newResource.name" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="newResource.type">
            <el-option label="自习室" value="自习室" />
            <el-option label="实验室" value="实验室" />
            <el-option label="会议室" value="会议室" />
          </el-select>
        </el-form-item>
        <el-form-item label="位置">
          <el-input v-model="newResource.location" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="newResource.capacity" :min="1" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="newResource.description" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="addResource" :loading="adding">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getResources } from '../api/resource'
import { getMyReservations } from '../api/reservation'

const activeTab = ref('resources')
const allResources = ref([])
const allReservations = ref([])
const showAddDialog = ref(false)
const adding = ref(false)

const newResource = ref({
  name: '',
  type: '自习室',
  location: '',
  capacity: 1,
  description: '',
})

async function loadData() {
  try {
    const [resR, resV] = await Promise.all([getResources(), getMyReservations()])
    if (resR.code === 200) allResources.value = resR.data
    if (resV.code === 200) allReservations.value = resV.data
  } catch (e) {
    // ignore
  }
}

async function addResource() {
  adding.value = true
  try {
    // 实际项目中这里应该调用新增API
    ElMessage.success('新增成功（演示模式）')
    showAddDialog.value = false
  } finally {
    adding.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.admin { max-width: 1200px; margin: 0 auto; }
</style>
