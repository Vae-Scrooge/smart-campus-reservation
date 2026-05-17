import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('../views/Home.vue') },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue') },
  { path: '/resources', name: 'Resources', component: () => import('../views/Resources.vue') },
  { path: '/reservations', name: 'Reservations', component: () => import('../views/Reservations.vue') },
  { path: '/profile', name: 'Profile', component: () => import('../views/Profile.vue') },
  { path: '/admin', name: 'Admin', component: () => import('../views/Admin.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const publicPages = ['/login', '/register', '/']
  if (!token && !publicPages.includes(to.path)) {
    next('/login')
  } else {
    next()
  }
})

export default router
