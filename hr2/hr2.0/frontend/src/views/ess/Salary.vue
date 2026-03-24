<template>
  <div class="salary-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>工资条</span>
          <el-select v-model="selectedMonth" @change="handleMonthChange">
            <el-option label="2024年1月" value="2024-01" />
            <el-option label="2024年2月" value="2024-02" />
            <el-option label="2024年3月" value="2024-03" />
          </el-select>
        </div>
      </template>
      <div class="salary-details">
        <div class="salary-item">
          <span class="label">基本工资：</span>
          <span class="value">¥8000</span>
        </div>
        <div class="salary-item">
          <span class="label">绩效奖金：</span>
          <span class="value">¥2000</span>
        </div>
        <div class="salary-item">
          <span class="label">交通补贴：</span>
          <span class="value">¥500</span>
        </div>
        <div class="salary-item">
          <span class="label">餐补：</span>
          <span class="value">¥300</span>
        </div>
        <div class="salary-item">
          <span class="label">应发工资：</span>
          <span class="value total">¥10800</span>
        </div>
        <div class="salary-item">
          <span class="label">个人所得税：</span>
          <span class="value">¥300</span>
        </div>
        <div class="salary-item">
          <span class="label">社保个人部分：</span>
          <span class="value">¥800</span>
        </div>
        <div class="salary-item">
          <span class="label">公积金个人部分：</span>
          <span class="value">¥500</span>
        </div>
        <div class="salary-item">
          <span class="label">实发工资：</span>
          <span class="value total">¥9200</span>
        </div>
      </div>
    </el-card>

    <el-card shadow="hover" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>福利信息</span>
        </div>
      </template>
      <el-table :data="welfareList" style="width: 100%">
        <el-table-column prop="type" label="福利类型" width="180" />
        <el-table-column prop="name" label="福利名称" width="180" />
        <el-table-column prop="amount" label="金额" width="120" />
        <el-table-column prop="issueDate" label="发放日期" width="180" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已发放' ? 'success' : 'warning'">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';

const selectedMonth = ref('2024-01');

const welfareList = reactive([
  { type: '节日福利', name: '春节福利', amount: '¥1000', issueDate: '2024-02-01', status: '已发放' },
  { type: '生日福利', name: '生日礼金', amount: '¥500', issueDate: '2024-03-15', status: '已发放' },
  { type: '高温补贴', name: '夏季高温补贴', amount: '¥300', issueDate: '2024-06-01', status: '未发放' }
]);

const handleMonthChange = (val: string) => {
  // 这里可以添加月份变化的处理逻辑
  console.log('Selected month:', val);
};
</script>

<style scoped>
.salary-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.salary-details {
  margin-top: 20px;
}

.salary-item {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.label {
  width: 150px;
  font-weight: bold;
}

.value {
  flex: 1;
}

.total {
  font-weight: bold;
  color: #409EFF;
}
</style>