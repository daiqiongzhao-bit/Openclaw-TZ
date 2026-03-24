<template>
  <div class="home-container">
    <!-- 侧边导航栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <h2 class="sidebar-title">HR管理系统</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        router
        :unique-opened="true"
      >
        <el-sub-menu index="organization">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span>组织人事管理</span>
          </template>
          <el-menu-item index="/home/organization">组织架构</el-menu-item>
          <el-menu-item index="/home/employee">员工档案</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/home/attendance">
          <el-icon><Timer /></el-icon>
          <span>考勤与假期管理</span>
        </el-menu-item>
        <el-menu-item index="/home/salary">
          <el-icon><Money /></el-icon>
          <span>薪酬福利管理</span>
        </el-menu-item>
        <el-menu-item index="/home/performance">
          <el-icon><Star /></el-icon>
          <span>绩效管理</span>
        </el-menu-item>
        <el-menu-item index="/home/recruitment">
          <el-icon><UserFilled /></el-icon>
          <span>招聘管理</span>
        </el-menu-item>
        <el-menu-item index="/home/training">
          <el-icon><Reading /></el-icon>
          <span>培训管理</span>
        </el-menu-item>
        <el-menu-item index="/home/relationship">
          <el-icon><ChatLineRound /></el-icon>
          <span>员工关系管理</span>
        </el-menu-item>
        <el-menu-item index="/home/workflow">
          <el-icon><Operation /></el-icon>
          <span>流程审批中心</span>
        </el-menu-item>
        <el-menu-item index="/home/bi">
          <el-icon><DataAnalysis /></el-icon>
          <span>人力数据分析BI</span>
        </el-menu-item>
        <el-menu-item index="/home/system">
          <el-icon><Setting /></el-icon>
          <span>系统管理</span>
        </el-menu-item>
      </el-menu>
    </aside>

    <!-- 主内容区域 -->
    <main class="main-content">
      <!-- 顶部导航栏 -->
      <header class="main-header">
        <div class="header-left">
          <el-button type="text" @click="toggleSidebar">
            <el-icon><Menu /></el-icon>
          </el-button>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar size="small">HR</el-avatar>
              <span class="user-name">HR管理员</span>
              <el-icon class="el-icon--right"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item>设置</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <!-- 内容区域 -->
      <div class="content-wrapper">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { 
  OfficeBuilding, 
  Timer, 
  Money, 
  Star, 
  UserFilled, 
  Reading, 
  ChatLineRound, 
  Operation, 
  DataAnalysis, 
  Setting, 
  Menu, 
  ArrowDown 
} from '@element-plus/icons-vue'

const route = useRoute()
const isSidebarCollapsed = ref(false)

const activeMenu = computed(() => {
  const path = route.path
  return path.split('/')[2] || 'organization'
})

const toggleSidebar = () => {
  isSidebarCollapsed.value = !isSidebarCollapsed.value
}
</script>

<style scoped>
.home-container {
  display: flex;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.sidebar {
  width: 240px;
  height: 100%;
  background-color: #fff;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.06);
  transition: width 0.3s ease;
  overflow-y: auto;
}

.sidebar.collapsed {
  width: 64px;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #f0f0f0;
}

.sidebar-title {
  font-size: 18px;
  font-weight: 600;
  color: #10D9A3;
  margin: 0;
}

.sidebar-menu {
  border-right: none;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #F9FAFC;
  overflow: hidden;
}

.main-header {
  height: 60px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-name {
  margin: 0 8px;
  font-size: 14px;
}

.content-wrapper {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

/* 液态玻璃效果 */
.liquid-glass-card {
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  border: 1px solid rgba(255, 255, 255, 0.4);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  border-radius: 14px;
  transition: all 0.3s ease;
  padding: 20px;
}

.liquid-glass-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.09);
  transform: translateY(-2px);
}
</style>