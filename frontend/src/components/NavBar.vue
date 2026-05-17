<template>
  <el-menu mode="horizontal" :ellipsis="false" class="nav-bar">
    <el-menu-item index="0" @click="$router.push('/')">
      <el-icon><School /></el-icon>
      智能校园预约系统
    </el-menu-item>

    <div class="flex-grow" />

    <el-menu-item index="1" @click="$router.push('/resources')">
      <el-icon><List /></el-icon>
      资源预约
    </el-menu-item>

    <el-menu-item v-if="userStore.isLoggedIn" index="2" @click="$router.push('/reservations')">
      <el-icon><Calendar /></el-icon>
      我的预约
    </el-menu-item>

    <el-menu-item v-if="userStore.isAdmin" index="3" @click="$router.push('/admin')">
      <el-icon><Setting /></el-icon>
      管理后台
    </el-menu-item>

    <el-sub-menu v-if="userStore.isLoggedIn" index="4">
      <template #title>
        <el-icon><User /></el-icon>
        {{ userStore.username }}
        <el-tag size="small" type="warning" style="margin-left: 6px">
          信用分: {{ userStore.creditScore }}
        </el-tag>
      </template>
      <el-menu-item index="4-1" @click="$router.push('/profile')">
        <el-icon><InfoFilled /></el-icon>个人信息
      </el-menu-item>
      <el-menu-item index="4-2" @click="handleLogout">
        <el-icon><SwitchButton /></el-icon>退出登录
      </el-menu-item>
    </el-sub-menu>

    <template v-else>
      <el-menu-item index="5" @click="$router.push('/login')">登录</el-menu-item>
      <el-menu-item index="6" @click="$router.push('/register')">注册</el-menu-item>
    </template>
  </el-menu>
</template>

<script setup>
import { useUserStore } from '../store/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

function handleLogout() {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.nav-bar {
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}
.flex-grow {
  flex-grow: 1;
}
</style>
