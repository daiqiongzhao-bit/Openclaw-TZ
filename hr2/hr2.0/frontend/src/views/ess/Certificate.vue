<template>
  <div class="certificate-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>证明申请</span>
          <el-button type="primary" @click="showCertificateDialog" size="small">申请证明</el-button>
        </div>
      </template>
      <el-table :data="certificateList" style="width: 100%">
        <el-table-column prop="certificateType" label="证明类型" width="180" />
        <el-table-column prop="applyDate" label="申请日期" width="180" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="purpose" label="用途" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewCertificate(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      v-model="certificateDialogVisible"
      title="申请证明"
      width="500px"
    >
      <el-form :model="certificateForm" label-width="80px">
        <el-form-item label="证明类型">
          <el-select v-model="certificateForm.certificateType">
            <el-option label="在职证明" value="在职证明" />
            <el-option label="收入证明" value="收入证明" />
            <el-option label="离职证明" value="离职证明" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="用途">
          <el-input v-model="certificateForm.purpose" type="textarea" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="certificateDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitCertificate">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';

const certificateList = reactive([
  { certificateType: '在职证明', applyDate: '2024-01-05', status: '已批准', purpose: '办理签证' },
  { certificateType: '收入证明', applyDate: '2023-12-20', status: '已批准', purpose: '申请贷款' },
  { certificateType: '在职证明', applyDate: '2024-01-10', status: '审批中', purpose: '租房' }
]);

const certificateDialogVisible = ref(false);
const certificateForm = reactive({
  certificateType: '',
  purpose: ''
});

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

const showCertificateDialog = () => {
  certificateDialogVisible.value = true;
};

const submitCertificate = () => {
  // 这里可以添加提交证明申请的API调用
  certificateList.push({
    certificateType: certificateForm.certificateType,
    applyDate: new Date().toISOString().split('T')[0],
    status: '审批中',
    purpose: certificateForm.purpose
  });
  certificateDialogVisible.value = false;
};

const viewCertificate = (row: any) => {
  // 这里可以添加查看证明详情的逻辑
  console.log('View certificate:', row);
};
</script>

<style scoped>
.certificate-container {
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