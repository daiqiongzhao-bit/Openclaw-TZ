<template>
  <div class="organization-container">
    <div class="page-header">
      <h2>组织架构管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddDepartment">
          <el-icon><Plus /></el-icon>
          新增部门
        </el-button>
      </div>
    </div>

    <div class="liquid-glass-card">
      <el-tree
        :data="departmentTree"
        :props="defaultProps"
        node-key="id"
        default-expand-all
        @node-click="handleNodeClick"
      >
        <template #default="{ node, data }">
          <div class="tree-node">
            <span>{{ data.name }}</span>
            <div class="node-actions">
              <el-button size="small" type="text" @click.stop="handleEditDepartment(data)">
                编辑
              </el-button>
              <el-button size="small" type="text" @click.stop="handleAddSubDepartment(data)">
                新增子部门
              </el-button>
              <el-button size="small" type="text" @click.stop="handleDeleteDepartment(data.id)">
                删除
              </el-button>
            </div>
          </div>
        </template>
      </el-tree>
    </div>

    <!-- 部门表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
    >
      <el-form :model="departmentForm" label-width="100px">
        <el-form-item label="部门名称">
          <el-input v-model="departmentForm.name" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="上级部门">
          <el-select v-model="departmentForm.parentId" placeholder="请选择上级部门">
            <el-option
              v-for="dept in departmentOptions"
              :key="dept.id"
              :label="dept.name"
              :value="dept.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="部门负责人">
          <el-select v-model="departmentForm.leaderId" placeholder="请选择部门负责人">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="部门描述">
          <el-input
            v-model="departmentForm.description"
            type="textarea"
            placeholder="请输入部门描述"
            rows="3"
          />
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
import { ref, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 部门树数据
const departmentTree = ref([
  {
    id: 1,
    name: '总公司',
    parentId: 0,
    leaderId: 1,
    description: '公司总部',
    children: [
      {
        id: 2,
        name: '技术部',
        parentId: 1,
        leaderId: 2,
        description: '负责技术开发',
        children: [
          {
            id: 3,
            name: '前端开发组',
            parentId: 2,
            leaderId: 3,
            description: '负责前端开发'
          },
          {
            id: 4,
            name: '后端开发组',
            parentId: 2,
            leaderId: 4,
            description: '负责后端开发'
          }
        ]
      },
      {
        id: 5,
        name: '人力资源部',
        parentId: 1,
        leaderId: 5,
        description: '负责人力资源管理'
      },
      {
        id: 6,
        name: '财务部',
        parentId: 1,
        leaderId: 6,
        description: '负责财务管理'
      }
    ]
  }
])

// 部门选择选项
const departmentOptions = ref([
  { id: 0, name: '无' },
  { id: 1, name: '总公司' },
  { id: 2, name: '技术部' },
  { id: 5, name: '人力资源部' },
  { id: 6, name: '财务部' }
])

// 员工列表
const employees = ref([
  { id: 1, name: '张三' },
  { id: 2, name: '李四' },
  { id: 3, name: '王五' },
  { id: 4, name: '赵六' },
  { id: 5, name: '钱七' },
  { id: 6, name: '孙八' }
])

// 树节点配置
const defaultProps = {
  children: 'children',
  label: 'name'
}

// 对话框状态
const dialogVisible = ref(false)
const dialogTitle = ref('新增部门')

// 部门表单
const departmentForm = ref({
  id: 0,
  name: '',
  parentId: 0,
  leaderId: 0,
  description: ''
})

// 处理节点点击
const handleNodeClick = (data: any) => {
  console.log('点击节点:', data)
}

// 处理新增部门
const handleAddDepartment = () => {
  dialogTitle.value = '新增部门'
  departmentForm.value = {
    id: 0,
    name: '',
    parentId: 0,
    leaderId: 0,
    description: ''
  }
  dialogVisible.value = true
}

// 处理新增子部门
const handleAddSubDepartment = (data: any) => {
  dialogTitle.value = '新增子部门'
  departmentForm.value = {
    id: 0,
    name: '',
    parentId: data.id,
    leaderId: 0,
    description: ''
  }
  dialogVisible.value = true
}

// 处理编辑部门
const handleEditDepartment = (data: any) => {
  dialogTitle.value = '编辑部门'
  departmentForm.value = { ...data }
  dialogVisible.value = true
}

// 处理删除部门
const handleDeleteDepartment = (id: number) => {
  ElMessageBox.confirm('确定要删除这个部门吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除部门的逻辑
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

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取部门数据的逻辑
})
</script>

<style scoped>
.organization-container {
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

.tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.node-actions {
  display: flex;
  gap: 10px;
  opacity: 0;
  transition: opacity 0.3s;
}

.tree-node:hover .node-actions {
  opacity: 1;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>