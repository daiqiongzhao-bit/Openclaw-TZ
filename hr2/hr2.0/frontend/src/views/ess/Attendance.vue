<template>
  <div class="attendance-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>考勤记录</span>
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="handleDateChange"
          />
        </div>
      </template>
      <el-table :data="attendanceList" style="width: 100%">
        <el-table-column prop="date" label="日期" width="180" />
        <el-table-column prop="checkIn" label="上班打卡" width="180" />
        <el-table-column prop="checkOut" label="下班打卡" width="180" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getTagType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
      </el-table>
    </el-card>

    <el-card shadow="hover" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>请假申请</span>
          <el-button type="primary" @click="showLeaveDialog" size="small">申请请假</el-button>
        </div>
      </template>
      <el-table :data="leaveList" style="width: 100%">
        <el-table-column prop="leaveType" label="请假类型" width="120" />
        <el-table-column prop="startDate" label="开始日期" width="180" />
        <el-table-column prop="endDate" label="结束日期" width="180" />
        <el-table-column prop="days" label="天数" width="80" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getTagType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="原因" />
      </el-table>
    </el-card>

    <el-dialog
      v-model="leaveDialogVisible"
      title="请假申请"
      width="500px"
    >
      <el-form :model="leaveForm" label-width="80px">
        <el-form-item label="请假类型">
          <el-select v-model="leaveForm.leaveType">
            <el-option label="事假" value="事假" />
            <el-option label="病假" value="病假" />
            <el-option label="年假" value="年假" />
            <el-option label="婚假" value="婚假" />
            <el-option label="产假" value="产假" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="leaveForm.startDate" type="date" />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="leaveForm.endDate" type="date" />
        </el-form-item>
        <el-form-item label="原因">
          <el-input v-model="leaveForm.reason" type="textarea" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="leaveDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitLeave">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';

const dateRange = ref([]);

const attendanceList = reactive([
  { date: '2024-01-01', checkIn: '09:00', checkOut: '18:00', status: '正常', remark: '' },
  { date: '2024-01-02', checkIn: '09:10', checkOut: '18:00', status: '迟到', remark: '' },
  { date: '2024-01-03', checkIn: '09:00', checkOut: '17:30', status: '早退', remark: '' },
  { date: '2024-01-04', checkIn: '09:00', checkOut: '18:00', status: '正常', remark: '' },
  { date: '2024-01-05', checkIn: '', checkOut: '', status: '缺勤', remark: '请假' }
]);

const leaveList = reactive([
  { leaveType: '年假', startDate: '2024-01-05', endDate: '2024-01-05', days: 1, status: '已批准', reason: '个人休息' },
  { leaveType: '事假', startDate: '2024-01-10', endDate: '2024-01-11', days: 2, status: '审批中', reason: '家中有事' }
]);

const leaveDialogVisible = ref(false);
const leaveForm = reactive({
  leaveType: '',
  startDate: '',
  endDate: '',
  reason: ''
});

const handleDateChange = (val: any) => {
  // 这里可以添加日期范围变化的处理逻辑
  console.log('Date range changed:', val);
};

const getTagType = (status: string) => {
  switch (status) {
    case '正常':
      return 'success';
    case '迟到':
    case '早退':
      return 'warning';
    case '缺勤':
      return 'danger';
    case '已批准':
      return 'success';
    case '审批中':
      return 'warning';
    case '已拒绝':
      return 'danger';
    default:
      return '';
  }
};

const showLeaveDialog = () => {
  leaveDialogVisible.value = true;
};

const submitLeave = () => {
  // 这里可以添加提交请假申请的API调用
  leaveList.push({
    leaveType: leaveForm.leaveType,
    startDate: leaveForm.startDate,
    endDate: leaveForm.endDate,
    days: 1,
    status: '审批中',
    reason: leaveForm.reason
  });
  leaveDialogVisible.value = false;
};
</script>

<style scoped>
.attendance-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>