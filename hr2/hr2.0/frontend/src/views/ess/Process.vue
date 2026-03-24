<template>
  <div class="process-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>我的待办</span>
        </div>
      </template>
      <el-table :data="todoList" style="width: 100%">
        <el-table-column prop="processName" label="流程名称" width="200" />
        <el-table-column prop="applyUser" label="申请人" width="150" />
        <el-table-column prop="applyDate" label="申请日期" width="180" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag type="warning">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button type="primary" size="small" @click="approveProcess(scope.row)">审批</el-button>
            <el-button type="danger" size="small" @click="rejectProcess(scope.row)" style="margin-left: 10px">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card shadow="hover" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>我的申请</span>
        </div>
      </template>
      <el-table :data="applyList" style="width: 100%">
        <el-table-column prop="processName" label="流程名称" width="200" />
        <el-table-column prop="applyDate" label="申请日期" width="180" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewProcess(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue';

const todoList = reactive([
  { processName: '请假申请', applyUser: '李四', applyDate: '2024-01-10', status: '待审批' },
  { processName: '加班申请', applyUser: '王五', applyDate: '2024-01-09', status: '待审批' },
  { processName: '报销申请', applyUser: '赵六', applyDate: '2024-01-08', status: '待审批' }
]);

const applyList = reactive([
  { processName: '请假申请', applyDate: '2024-01-05', status: '已批准' },
  { processName: '报销申请', applyDate: '2024-01-01', status: '已拒绝' },
  { processName: '加班申请', applyDate: '2023-12-30', status: '审批中' }
]);

const getStatusType = (status: string) => {
  switch (status) {
    case '已批准':
      return 'success';
    case '已拒绝':
      return 'danger';
    case '审批中':
      return 'warning';
    default:
      return '';
  }
};

const approveProcess = (row: any) => {
  // 这里可以添加审批流程的逻辑
  console.log('Approve process:', row);
};

const rejectProcess = (row: any) => {
  // 这里可以添加拒绝流程的逻辑
  console.log('Reject process:', row);
};

const viewProcess = (row: any) => {
  // 这里可以添加查看流程详情的逻辑
  console.log('View process:', row);
};
</script>

<style scoped>
.process-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>