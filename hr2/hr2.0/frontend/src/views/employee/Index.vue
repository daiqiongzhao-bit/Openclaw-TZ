<template>
  <div class="employee-container">
    <div class="page-header">
      <h2>员工档案管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddEmployee">
          <el-icon><Plus /></el-icon>
          新增员工
        </el-button>
      </div>
    </div>

    <div class="liquid-glass-card">
      <div class="search-bar">
        <el-input
          v-model="searchForm.keyword"
          placeholder="请输入员工姓名或工号"
          style="width: 300px"
          prefix-icon="Search"
        />
        <el-select v-model="searchForm.departmentId" placeholder="请选择部门" style="width: 200px; margin-left: 10px">
          <el-option
            v-for="dept in departments"
            :key="dept.id"
            :label="dept.name"
            :value="dept.id"
          />
        </el-select>
        <el-button type="primary" @click="handleSearch" style="margin-left: 10px">搜索</el-button>
        <el-button @click="handleReset" style="margin-left: 10px">重置</el-button>
      </div>

      <el-table :data="employees" style="width: 100%">
        <el-table-column prop="id" label="工号" width="100" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="scope">
            {{ scope.row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="departmentName" label="部门" width="150" />
        <el-table-column prop="positionName" label="职位" width="120" />
        <el-table-column prop="phone" label="电话" width="150" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="entryDate" label="入职日期" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '在职' : '离职' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleViewEmployee(scope.row)">
              查看
            </el-button>
            <el-button size="small" type="warning" @click="handleEditEmployee(scope.row)">
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDeleteEmployee(scope.row.id)">
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

    <!-- 员工表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="700px"
    >
      <el-form :model="employeeForm" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="工号">
              <el-input v-model="employeeForm.id" placeholder="请输入工号" />
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="employeeForm.name" placeholder="请输入姓名" />
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="employeeForm.gender">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="2">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="部门">
              <el-select v-model="employeeForm.departmentId" placeholder="请选择部门">
                <el-option
                  v-for="dept in departments"
                  :key="dept.id"
                  :label="dept.name"
                  :value="dept.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="职位">
              <el-select v-model="employeeForm.positionId" placeholder="请选择职位">
                <el-option
                  v-for="pos in positions"
                  :key="pos.id"
                  :label="pos.name"
                  :value="pos.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="电话">
              <el-input v-model="employeeForm.phone" placeholder="请输入电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="employeeForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="出生日期">
              <el-date-picker
                v-model="employeeForm.birthDate"
                type="date"
                placeholder="选择日期"
                style="width: 100%"
              />
            </el-form-item>
            <el-form-item label="入职日期">
              <el-date-picker
                v-model="employeeForm.entryDate"
                type="date"
                placeholder="选择日期"
                style="width: 100%"
              />
            </el-form-item>
            <el-form-item label="状态">
              <el-radio-group v-model="employeeForm.status">
                <el-radio :label="1">在职</el-radio>
                <el-radio :label="2">离职</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="备注">
              <el-input
                v-model="employeeForm.remark"
                type="textarea"
                placeholder="请输入备注"
                rows="3"
              />
            </el-form-item>
          </el-col>
        </el-row>
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
import { ref, onMounted } from 'vue'
import { Plus, Search } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 员工列表
const employees = ref([
  {
    id: 1,
    name: '张三',
    gender: 1,
    departmentId: 2,
    departmentName: '技术部',
    positionId: 1,
    positionName: '前端开发工程师',
    phone: '13800138001',
    email: 'zhangsan@example.com',
    birthDate: '1990-01-01',
    entryDate: '2020-01-01',
    status: 1,
    remark: ''
  },
  {
    id: 2,
    name: '李四',
    gender: 1,
    departmentId: 2,
    departmentName: '技术部',
    positionId: 2,
    positionName: '后端开发工程师',
    phone: '13800138002',
    email: 'lisi@example.com',
    birthDate: '1991-01-01',
    entryDate: '2020-02-01',
    status: 1,
    remark: ''
  },
  {
    id: 3,
    name: '王五',
    gender: 2,
    departmentId: 5,
    departmentName: '人力资源部',
    positionId: 3,
    positionName: 'HR专员',
    phone: '13800138003',
    email: 'wangwu@example.com',
    birthDate: '1992-01-01',
    entryDate: '2020-03-01',
    status: 1,
    remark: ''
  }
])

// 部门列表
const departments = ref([
  { id: 0, name: '全部' },
  { id: 1, name: '总公司' },
  { id: 2, name: '技术部' },
  { id: 5, name: '人力资源部' },
  { id: 6, name: '财务部' }
])

// 职位列表
const positions = ref([
  { id: 1, name: '前端开发工程师' },
  { id: 2, name: '后端开发工程师' },
  { id: 3, name: 'HR专员' },
  { id: 4, name: '财务专员' },
  { id: 5, name: '部门经理' }
])

// 搜索表单
const searchForm = ref({
  keyword: '',
  departmentId: 0
})

// 分页参数
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(3)

// 对话框状态
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')

// 员工表单
const employeeForm = ref({
  id: 0,
  name: '',
  gender: 1,
  departmentId: 0,
  positionId: 0,
  phone: '',
  email: '',
  birthDate: '',
  entryDate: '',
  status: 1,
  remark: ''
})

// 处理搜索
const handleSearch = () => {
  // 这里添加搜索逻辑
  console.log('搜索条件:', searchForm.value)
}

// 处理重置
const handleReset = () => {
  searchForm.value = {
    keyword: '',
    departmentId: 0
  }
}

// 处理新增员工
const handleAddEmployee = () => {
  dialogTitle.value = '新增员工'
  employeeForm.value = {
    id: 0,
    name: '',
    gender: 1,
    departmentId: 0,
    positionId: 0,
    phone: '',
    email: '',
    birthDate: '',
    entryDate: '',
    status: 1,
    remark: ''
  }
  dialogVisible.value = true
}

// 处理查看员工
const handleViewEmployee = (row: any) => {
  // 这里添加查看员工详情的逻辑
  console.log('查看员工:', row)
}

// 处理编辑员工
const handleEditEmployee = (row: any) => {
  dialogTitle.value = '编辑员工'
  employeeForm.value = { ...row }
  dialogVisible.value = true
}

// 处理删除员工
const handleDeleteEmployee = (id: number) => {
  ElMessageBox.confirm('确定要删除这个员工吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除员工的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理表单提交
const handleSubmit = () => {
  // 这里添加提交表单的逻辑
  dialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理分页大小变化
const handleSizeChange = (size: number) => {
  pageSize.value = size
  // 这里添加分页逻辑
}

// 处理分页当前页变化
const handleCurrentChange = (current: number) => {
  currentPage.value = current
  // 这里添加分页逻辑
}

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取员工数据的逻辑
})
</script>

<style scoped>
.employee-container {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  font-size: 20px;
  font-weight: 600;
  margin: 0;
  color: #333;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>