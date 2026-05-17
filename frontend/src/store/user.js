import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getProfile } from '../api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const username = ref(localStorage.getItem('username') || '')
  const role = ref(localStorage.getItem('role') || '')
  const creditScore = ref(Number(localStorage.getItem('creditScore') || 100))
  const realName = ref('')
  const studentId = ref('')

  const isLoggedIn = computed(() => !!token.value)
  const isAdmin = computed(() => role.value === 'ADMIN')

  function setLogin(data) {
    token.value = data.token
    username.value = data.username
    role.value = data.role
    creditScore.value = data.creditScore || 100
    localStorage.setItem('token', data.token)
    localStorage.setItem('username', data.username)
    localStorage.setItem('role', data.role)
    localStorage.setItem('creditScore', data.creditScore || 100)
  }

  function logout() {
    token.value = ''
    username.value = ''
    role.value = ''
    creditScore.value = 100
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    localStorage.removeItem('role')
    localStorage.removeItem('creditScore')
  }

  async function fetchProfile() {
    try {
      const res = await getProfile()
      if (res.code === 200) {
        realName.value = res.data.realName || ''
        studentId.value = res.data.studentId || ''
        creditScore.value = res.data.creditScore || 100
      }
    } catch (e) {
      console.error('获取用户信息失败')
    }
  }

  return { token, username, role, creditScore, realName, studentId, isLoggedIn, isAdmin, setLogin, logout, fetchProfile }
})
