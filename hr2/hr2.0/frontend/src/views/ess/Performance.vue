<template>
  <div class="performance-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>绩效评估</span>
        </div>
      </template>
      <el-table :data="performanceList" style="width: 100%">
        <el-table-column prop="cycle" label="考核周期" width="180" />
        <el-table-column prop="template" label="考核模板" width="180" />
        <el-table-column prop="score" label="得分" width="100" />
        <el-table-column prop="level" label="等级" width="100">
          <template #default="scope">
            <el-tag :type="getLevelType(scope.row.level)">{{ scope.row.level }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已完成' ? 'success' : 'warning'">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="endDate" label="结束日期" width="180" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewPerformance(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card shadow="hover" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>培训记录</span>
        </div>
      </template>
      <el-table :data="trainingList" style="width: 100%">
        <el-table-column prop="courseName" label="课程名称" width="200" />
        <el-table-column prop="trainingDate" label="培训日期" width="180" />
        <el-table-column prop="instructor" label="讲师" width="150" />
        <el-table-column prop="duration" label="时长" width="100" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已完成' ? 'success' : 'warning'">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewTraining(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue';

const performanceList = reactive([
  { cycle: '2024年Q1', template: '季度绩效评估', score: 85, level: 'A', status: '已完成', endDate: '2024-03-31' },
  { cycle: '2023年Q4', template: '季度绩效评估', score: 90, level: 'A+', status: '已完成', endDate: '2023-12-31' },
  { cycle: '2023年Q3', template: '季度绩效评估', score: 80, level: 'B', status: '已完成', endDate: '2023-09-30' }
]);

const trainingList = reactive([
  { courseName: 'Java高级开发', trainingDate: '2024-01-15', instructor: '王老师', duration: '8小时', status: '已完成' },
  { courseName: 'Spring Boot实战', trainingDate: '2024-02-10', instructor: '李老师', duration: '16小时', status: '已完成' },
  { courseName: '微服务架构', trainingDate: '2024-03-20', instructor: '张老师', duration: '12小时', status: '未完成' }
]);

const getLevelType = (level: string) => {
  switch (level) {
    case 'A+':
    case 'A':
      return 'success';
    case 'B':
      return 'warning';
    case 'C':
      return 'danger';
    default:
      return '';
  }
};

const viewPerformance = (row: any) => {
  // 这里可以添加查看绩效详情的逻辑
  console.log('View performance:', row);
};

const viewTraining = (row: any) => {
  // 这里可以添加查看培训详情的逻辑
  console.log('View training:', row);
};
</script>

<style scoped>
.performance-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>