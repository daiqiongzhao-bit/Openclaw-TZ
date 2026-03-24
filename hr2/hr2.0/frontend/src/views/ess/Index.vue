<template>
  <div class="ess-container">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          class="ess-menu"
          router
        >
          <el-menu-item index="/ess/profile">
            <el-icon><User /></el-icon>
            <span>个人中心</span>
          </el-menu-item>
          <el-menu-item index="/ess/attendance">
            <el-icon><Timer /></el-icon>
            <span>考勤假期</span>
          </el-menu-item>
          <el-menu-item index="/ess/salary">
            <el-icon><Money /></el-icon>
            <span>薪酬福利</span>
          </el-menu-item>
          <el-menu-item index="/ess/performance">
            <el-icon><Star /></el-icon>
            <span>绩效培训</span>
          </el-menu-item>
          <el-menu-item index="/ess/process">
            <el-icon><Document /></el-icon>
            <span>流程待办</span>
          </el-menu-item>
          <el-menu-item index="/ess/certificate">
            <el-icon><Ticket /></el-icon>
            <span>证明申请</span>
          </el-menu-item>
          <el-menu-item index="/ess/message">
            <el-icon><ChatDotRound /></el-icon>
            <span>消息通知</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { User, Timer, Money, Star, Document, Ticket, ChatDotRound } from '@element-plus/icons-vue';

const route = useRoute();
const activeMenu = computed(() => route.path);
</script>

<style scoped>
.ess-container {
  height: 100vh;
}

.ess-menu {
  height: 100vh;
  border-right: none;
}
</style>