<template>
  <div class="profile">
    <h2 class="page-title">个人信息</h2>
    <el-card style="max-width: 600px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户名">{{ userStore.username }}</el-descriptions-item>
        <el-descriptions-item label="真实姓名">{{ userStore.realName || '未设置' }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ userStore.studentId || '未设置' }}</el-descriptions-item>
        <el-descriptions-item label="角色">
          <el-tag :type="userStore.isAdmin ? 'danger' : 'primary'">
            {{ userStore.isAdmin ? '管理员' : '普通用户' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="信用分">
          <el-progress :percentage="userStore.creditScore" :status="creditStatus" />
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const creditStatus = computed(() => {
  if (userStore.creditScore >= 80) return 'success'
  if (userStore.creditScore >= 60) return 'warning'
  return 'exception'
})

onMounted(() => {
  userStore.fetchProfile()
})
</script>

<style scoped>
.profile { max-width: 1200px; margin: 0 auto; }
</style>
