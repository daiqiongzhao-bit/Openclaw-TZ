<template>
  <div class="tenant-management">
    <div class="liquid-glass-card">
      <div class="card-header">
        <h2 class="section-title">租户列表</h2>
        <div class="header-actions">
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            添加租户
          </el-button>
        </div>
      </div>
      
      <div class="search-bar">
        <el-input
          v-model="searchForm.tenantName"
          placeholder="请输入租户名称"
          style="width: 200px"
          clearable
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-input
          v-model="searchForm.tenantCode"
          placeholder="请输入租户编码"
          style="width: 200px; margin-left: 10px"
          clearable
        >
          <template #prefix>
            <el-icon><Key /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" style="margin-left: 10px" @click="handleSearch">
          搜索
        </el-button>
      </div>

      <el-table :data="tenantList" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="tenantName" label="租户名称" width="180" />
        <el-table-column prop="tenantCode" label="租户编码" width="150" />
        <el-table-column prop="tenantDomain" label="租户域名" width="200" />
        <el-table-column prop="contactName" label="联系人" width="120" />
        <el-table-column prop="contactPhone" label="联系电话" width="150" />
        <el-table-column prop="maxUsers" label="最大用户数" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="expireTime" label="到期时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button 
              :type="scope.row.status === 1 ? 'warning' : 'success'" 
              size="small" 
              @click="handleStatusChange(scope.row)"
              style="margin-left: 5px"
            >
              {{ scope.row.status === 1 ? '禁用' : '启用' }}
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)" style="margin-left: 5px">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>

    <!-- 添加/编辑租户对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加租户' : '编辑租户'"
      width="600px"
    >
      <el-form :model="formData" :rules="rules" ref="formRef">
        <el-form-item label="租户名称" prop="tenantName">
          <el-input v-model="formData.tenantName" placeholder="请输入租户名称" />
        </el-form-item>
        <el-form-item label="租户编码" prop="tenantCode">
          <el-input v-model="formData.tenantCode" placeholder="请输入租户编码" />
        </el-form-item>
        <el-form-item label="租户域名" prop="tenantDomain">
          <el-input v-model="formData.tenantDomain" placeholder="请输入租户域名" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactName">
          <el-input v-model="formData.contactName" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="formData.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="联系邮箱" prop="contactEmail">
          <el-input v-model="formData.contactEmail" placeholder="请输入联系邮箱" />
        </el-form-item>
        <el-form-item label="最大用户数" prop="maxUsers">
          <el-input-number v-model="formData.maxUsers" :min="1" placeholder="请输入最大用户数" />
        </el-form-item>
        <el-form-item label="到期时间" prop="expireTime">
          <el-date-picker
            v-model="formData.expireTime"
            type="datetime"
            placeholder="请选择到期时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="formData.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { Plus, Search, Key } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'

// 数据
const tenantList = ref([
  {
    id: 1,
    tenantName: '默认租户',
    tenantCode: 'default',
    tenantDomain: 'default.example.com',
    contactName: '管理员',
    contactPhone: '13800138000',
    contactEmail: 'admin@example.com',
    status: 1,
    maxUsers: 1000,
    expireTime: '2027-12-31 23:59:59',
    createTime: '2026-03-21 10:00:00'
  },
  {
    id: 2,
    tenantName: '测试租户',
    tenantCode: 'test',
    tenantDomain: 'test.example.com',
    contactName: '测试管理员',
    contactPhone: '13800138001',
    contactEmail: 'test@example.com',
    status: 1,
    maxUsers: 100,
    expireTime: '2026-12-31 23:59:59',
    createTime: '2026-03-21 10:00:00'
  }
])

const total = ref(2)
const currentPage = ref(1)
const pageSize = ref(10)

// 搜索表单
const searchForm = reactive({
  tenantName: '',
  tenantCode: ''
})

// 对话框
const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref<FormInstance>()

const formData = reactive({
  id: '',
  tenantName: '',
  tenantCode: '',
  tenantDomain: '',
  contactName: '',
  contactPhone: '',
  contactEmail: '',
  status: 1,
  maxUsers: 100,
  expireTime: ''
})

// 表单验证规则
const rules = reactive<FormRules>({
  tenantName: [
    { required: true, message: '请输入租户名称', trigger: 'blur' }
  ],
  tenantCode: [
    { required: true, message: '请输入租户编码', trigger: 'blur' }
  ],
  contactName: [
    { required: true, message: '请输入联系人', trigger: 'blur' }
  ],
  contactPhone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  maxUsers: [
    { required: true, message: '请输入最大用户数', trigger: 'blur' }
  ],
  expireTime: [
    { required: true, message: '请选择到期时间', trigger: 'blur' }
  ]
})

// 生命周期
onMounted(() => {
  // 模拟获取租户列表
  console.log('获取租户列表')
})

// 方法
const handleSearch = () => {
  console.log('搜索租户', searchForm)
  // 模拟搜索
}

const handleAdd = () => {
  dialogType.value = 'add'
  Object.assign(formData, {
    id: '',
    tenantName: '',
    tenantCode: '',
    tenantDomain: '',
    contactName: '',
    contactPhone: '',
    contactEmail: '',
    status: 1,
    maxUsers: 100,
    expireTime: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogType.value = 'edit'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = (id: number) => {
  console.log('删除租户', id)
  // 模拟删除
}

const handleStatusChange = (row: any) => {
  console.log('修改租户状态', row.id, row.status === 1 ? '禁用' : '启用')
  // 模拟状态修改
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      console.log('提交表单', formData)
      // 模拟提交
      dialogVisible.value = false
    }
  })
}

const handleSizeChange = (size: number) => {
  pageSize.value = size
  console.log('每页条数改变', size)
}

const handleCurrentChange = (current: number) => {
  currentPage.value = current
  console.log('当前页码改变', current)
}
</script>

<style scoped>
.tenant-management {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #1D2129;
  margin: 0;
}

.search-bar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>