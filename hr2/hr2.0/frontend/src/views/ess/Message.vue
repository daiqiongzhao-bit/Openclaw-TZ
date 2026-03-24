<template>
  <div class="message-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>消息通知</span>
          <el-button type="primary" @click="markAllRead" size="small">全部已读</el-button>
        </div>
      </template>
      <el-table :data="messageList" style="width: 100%">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="内容" width="400" />
        <el-table-column prop="sendDate" label="发送时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '未读' ? 'danger' : 'success'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="markAsRead(scope.row)">
              {{ scope.row.status === '未读' ? '标记已读' : '查看' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue';

const messageList = reactive([
  { title: '系统通知', content: '您的请假申请已批准', sendDate: '2024-01-10 10:00:00', status: '未读' },
  { title: '系统通知', content: '您的报销申请已拒绝', sendDate: '2024-01-09 14:30:00', status: '未读' },
  { title: '系统通知', content: '2024年Q1绩效评估已开始', sendDate: '2024-01-01 09:00:00', status: '已读' },
  { title: '系统通知', content: '春节放假安排已发布', sendDate: '2023-12-20 16:00:00', status: '已读' }
]);

const markAsRead = (row: any) => {
  row.status = '已读';
};

const markAllRead = () => {
  messageList.forEach(item => {
    item.status = '已读';
  });
};
</script>

<style scoped>
.message-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>