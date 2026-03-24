<template>
  <div class="profile-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
          <el-button type="primary" @click="editProfile" size="small">编辑</el-button>
        </div>
      </template>
      <div class="profile-content">
        <div class="profile-item">
          <span class="label">姓名：</span>
          <span class="value">{{ profile.name }}</span>
        </div>
        <div class="profile-item">
          <span class="label">工号：</span>
          <span class="value">{{ profile.employeeId }}</span>
        </div>
        <div class="profile-item">
          <span class="label">性别：</span>
          <span class="value">{{ profile.gender }}</span>
        </div>
        <div class="profile-item">
          <span class="label">出生日期：</span>
          <span class="value">{{ profile.birthday }}</span>
        </div>
        <div class="profile-item">
          <span class="label">手机号码：</span>
          <span class="value">{{ profile.phone }}</span>
        </div>
        <div class="profile-item">
          <span class="label">邮箱：</span>
          <span class="value">{{ profile.email }}</span>
        </div>
        <div class="profile-item">
          <span class="label">部门：</span>
          <span class="value">{{ profile.department }}</span>
        </div>
        <div class="profile-item">
          <span class="label">职位：</span>
          <span class="value">{{ profile.position }}</span>
        </div>
        <div class="profile-item">
          <span class="label">职级：</span>
          <span class="value">{{ profile.grade }}</span>
        </div>
        <div class="profile-item">
          <span class="label">入职日期：</span>
          <span class="value">{{ profile.entryDate }}</span>
        </div>
        <div class="profile-item">
          <span class="label">状态：</span>
          <span class="value">{{ profile.status }}</span>
        </div>
      </div>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      title="编辑个人信息"
      width="500px"
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveProfile">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';

const dialogVisible = ref(false);

const profile = reactive({
  name: '张三',
  employeeId: 'EMP001',
  gender: '男',
  birthday: '1990-01-01',
  phone: '13800138000',
  email: 'zhangsan@example.com',
  department: '技术部',
  position: '开发工程师',
  grade: 'P3',
  entryDate: '2020-01-01',
  status: '在职'
});

const form = reactive({
  name: profile.name,
  phone: profile.phone,
  email: profile.email,
  gender: profile.gender
});

const editProfile = () => {
  Object.assign(form, profile);
  dialogVisible.value = true;
};

const saveProfile = () => {
  Object.assign(profile, form);
  dialogVisible.value = false;
  // 这里可以添加API调用，保存个人信息
};
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.profile-content {
  margin-top: 20px;
}

.profile-item {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.label {
  width: 100px;
  font-weight: bold;
}

.value {
  flex: 1;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>