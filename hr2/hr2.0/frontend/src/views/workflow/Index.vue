<template>
  <div class="workflow-container">
    <div class="page-header">
      <h2>流程审批中心</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddTemplate">
          <el-icon><Document /></el-icon>
          新增流程模板
        </el-button>
        <el-button type="success" @click="handleStartProcess">
          <el-icon><Start /></el-icon>
          发起流程
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="待办任务" name="todo">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="todoSearch.keyword"
              placeholder="请输入任务名称或发起人"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="todoSearch.processType" placeholder="请选择流程类型" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="请假申请" value="1" />
              <el-option label="报销申请" value="2" />
              <el-option label="加班申请" value="3" />
              <el-option label="调岗申请" value="4" />
              <el-option label="证书申请" value="5" />
            </el-select>
            <el-button type="primary" @click="handleTodoSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleTodoReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="todoTasks" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="taskName" label="任务名称" />
            <el-table-column prop="processName" label="流程名称" width="150" />
            <el-table-column prop="applicant" label="发起人" width="120" />
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column prop="deadline" label="截止时间" width="180" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleProcessTask(scope.row)">
                  处理
                </el-button>
                <el-button size="small" type="info" @click="handleViewTask(scope.row)">
                  查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="todoCurrentPage"
              v-model:page-size="todoPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="todoTotal"
              @size-change="handleTodoSizeChange"
              @current-change="handleTodoCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="已办任务" name="done">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="doneSearch.keyword"
              placeholder="请输入任务名称或发起人"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-date-picker
              v-model="doneSearch.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width: 300px; margin-left: 10px"
            />
            <el-button type="primary" @click="handleDoneSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleDoneReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="doneTasks" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="taskName" label="任务名称" />
            <el-table-column prop="processName" label="流程名称" width="150" />
            <el-table-column prop="applicant" label="发起人" width="120" />
            <el-table-column prop="handleTime" label="处理时间" width="180" />
            <el-table-column prop="result" label="处理结果" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.result === 'approved' ? 'success' : 'danger'">
                  {{ scope.row.result === 'approved' ? '通过' : '拒绝' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template #default="scope">
                <el-button size="small" type="info" @click="handleViewTask(scope.row)">
                  查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="doneCurrentPage"
              v-model:page-size="donePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="doneTotal"
              @size-change="handleDoneSizeChange"
              @current-change="handleDoneCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="流程模板" name="template">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="templateSearch.keyword"
              placeholder="请输入模板名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleTemplateSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleTemplateReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="templates" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="模板名称" />
            <el-table-column prop="type" label="流程类型" width="120">
              <template #default="scope">
                {{ getProcessTypeText(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column prop="version" label="版本" width="100" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewTemplate(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditTemplate(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteTemplate(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="templateCurrentPage"
              v-model:page-size="templatePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="templateTotal"
              @size-change="handleTemplateSizeChange"
              @current-change="handleTemplateCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="流程实例" name="instance">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="instanceSearch.keyword"
              placeholder="请输入流程名称或发起人"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="instanceSearch.status" placeholder="请选择状态" style="width: 120px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="运行中" value="1" />
              <el-option label="已完成" value="2" />
              <el-option label="已终止" value="3" />
            </el-select>
            <el-button type="primary" @click="handleInstanceSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleInstanceReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="instances" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="processName" label="流程名称" />
            <el-table-column prop="applicant" label="发起人" width="120" />
            <el-table-column prop="startTime" label="开始时间" width="180" />
            <el-table-column prop="endTime" label="结束时间" width="180" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getInstanceStatusType(scope.row.status)">
                  {{ getInstanceStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewInstance(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="danger" @click="handleTerminateInstance(scope.row.id)"
                  v-if="scope.row.status === 1">
                  终止
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="instanceCurrentPage"
              v-model:page-size="instancePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="instanceTotal"
              @size-change="handleInstanceSizeChange"
              @current-change="handleInstanceCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 流程模板对话框 -->
    <el-dialog
      v-model="templateDialogVisible"
      :title="templateDialogTitle"
      width="600px"
    >
      <el-form :model="templateForm" label-width="120px">
        <el-form-item label="模板名称">
          <el-input v-model="templateForm.name" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="流程类型">
          <el-select v-model="templateForm.type" placeholder="请选择流程类型">
            <el-option label="请假申请" value="1" />
            <el-option label="报销申请" value="2" />
            <el-option label="加班申请" value="3" />
            <el-option label="调岗申请" value="4" />
            <el-option label="证书申请" value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="版本">
          <el-input v-model="templateForm.version" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="templateForm.description"
            type="textarea"
            placeholder="请输入模板描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="templateForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="templateDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleTemplateSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 发起流程对话框 -->
    <el-dialog
      v-model="startProcessDialogVisible"
      title="发起流程"
      width="600px"
    >
      <el-form :model="startProcessForm" label-width="120px">
        <el-form-item label="流程模板">
          <el-select v-model="startProcessForm.templateId" placeholder="请选择流程模板">
            <el-option
              v-for="template in templates"
              :key="template.id"
              :label="template.name"
              :value="template.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="流程名称">
          <el-input v-model="startProcessForm.processName" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input
            v-model="startProcessForm.reason"
            type="textarea"
            placeholder="请输入申请原因"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="附件">
          <el-upload
            class="upload-demo"
            action="#"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :auto-upload="false"
          >
            <el-button type="primary">
              <el-icon><Upload /></el-icon>
              点击上传
            </el-button>
            <template #tip>
              <div class="el-upload__tip">
                只能上传jpg/png文件，且不超过500kb
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="startProcessDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleStartProcessSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 任务处理对话框 -->
    <el-dialog
      v-model="taskDialogVisible"
      title="处理任务"
      width="600px"
    >
      <el-form :model="taskForm" label-width="120px">
        <el-form-item label="任务名称">
          <el-input v-model="taskForm.taskName" disabled />
        </el-form-item>
        <el-form-item label="流程名称">
          <el-input v-model="taskForm.processName" disabled />
        </el-form-item>
        <el-form-item label="发起人">
          <el-input v-model="taskForm.applicant" disabled />
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input
            v-model="taskForm.reason"
            type="textarea"
            disabled
            rows="3"
          />
        </el-form-item>
        <el-form-item label="处理意见">
          <el-radio-group v-model="taskForm.result">
            <el-radio label="approved">通过</el-radio>
            <el-radio label="rejected">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="taskForm.comment"
            type="textarea"
            placeholder="请输入处理备注"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="taskDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleTaskSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Document, Start, Search, Upload } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 激活的标签页
const activeTab = ref('todo')

// 待办任务数据
const todoTasks = ref([
  {
    id: 1,
    taskName: '请假申请审批',
    processName: '请假流程',
    applicant: '张三',
    createTime: '2024-01-01 10:00:00',
    deadline: '2024-01-02 10:00:00'
  },
  {
    id: 2,
    taskName: '报销申请审批',
    processName: '报销流程',
    applicant: '李四',
    createTime: '2024-01-01 11:00:00',
    deadline: '2024-01-02 11:00:00'
  }
])

// 已办任务数据
const doneTasks = ref([
  {
    id: 1,
    taskName: '加班申请审批',
    processName: '加班流程',
    applicant: '王五',
    handleTime: '2024-01-01 09:00:00',
    result: 'approved'
  },
  {
    id: 2,
    taskName: '调岗申请审批',
    processName: '调岗流程',
    applicant: '赵六',
    handleTime: '2024-01-01 08:00:00',
    result: 'rejected'
  }
])

// 流程模板数据
const templates = ref([
  {
    id: 1,
    name: '请假流程模板',
    type: 1,
    version: '1.0',
    status: 1
  },
  {
    id: 2,
    name: '报销流程模板',
    type: 2,
    version: '1.0',
    status: 1
  },
  {
    id: 3,
    name: '加班流程模板',
    type: 3,
    version: '1.0',
    status: 1
  }
])

// 流程实例数据
const instances = ref([
  {
    id: 1,
    processName: '请假流程',
    applicant: '张三',
    startTime: '2024-01-01 10:00:00',
    endTime: '',
    status: 1
  },
  {
    id: 2,
    processName: '报销流程',
    applicant: '李四',
    startTime: '2024-01-01 11:00:00',
    endTime: '',
    status: 1
  },
  {
    id: 3,
    processName: '加班流程',
    applicant: '王五',
    startTime: '2024-01-01 09:00:00',
    endTime: '2024-01-01 09:30:00',
    status: 2
  }
])

// 搜索条件
const todoSearch = ref({ keyword: '', processType: '0' })
const doneSearch = ref({ keyword: '', dateRange: [] })
const templateSearch = ref({ keyword: '' })
const instanceSearch = ref({ keyword: '', status: '0' })

// 分页参数
const todoCurrentPage = ref(1)
const todoPageSize = ref(10)
const todoTotal = ref(2)

const doneCurrentPage = ref(1)
const donePageSize = ref(10)
const doneTotal = ref(2)

const templateCurrentPage = ref(1)
const templatePageSize = ref(10)
const templateTotal = ref(3)

const instanceCurrentPage = ref(1)
const instancePageSize = ref(10)
const instanceTotal = ref(3)

// 对话框状态
const templateDialogVisible = ref(false)
const templateDialogTitle = ref('新增流程模板')

const startProcessDialogVisible = ref(false)
const taskDialogVisible = ref(false)

// 表单数据
const templateForm = ref({
  id: 0,
  name: '',
  type: 1,
  version: '1.0',
  description: '',
  status: 1
})

const startProcessForm = ref({
  templateId: 0,
  processName: '',
  reason: ''
})

const taskForm = ref({
  taskName: '',
  processName: '',
  applicant: '',
  reason: '',
  result: 'approved',
  comment: ''
})

// 文件列表
const fileList = ref([])

// 获取流程类型文本
const getProcessTypeText = (type: number) => {
  switch (type) {
    case 1: return '请假申请'
    case 2: return '报销申请'
    case 3: return '加班申请'
    case 4: return '调岗申请'
    case 5: return '证书申请'
    default: return '未知'
  }
}

// 获取流程实例状态类型
const getInstanceStatusType = (status: number) => {
  switch (status) {
    case 1: return 'warning'
    case 2: return 'success'
    case 3: return 'danger'
    default: return 'info'
  }
}

// 获取流程实例状态文本
const getInstanceStatusText = (status: number) => {
  switch (status) {
    case 1: return '运行中'
    case 2: return '已完成'
    case 3: return '已终止'
    default: return '未知'
  }
}

// 处理新增流程模板
const handleAddTemplate = () => {
  templateDialogTitle.value = '新增流程模板'
  templateForm.value = {
    id: 0,
    name: '',
    type: 1,
    version: '1.0',
    description: '',
    status: 1
  }
  templateDialogVisible.value = true
}

// 处理编辑流程模板
const handleEditTemplate = (row: any) => {
  templateDialogTitle.value = '编辑流程模板'
  templateForm.value = { ...row }
  templateDialogVisible.value = true
}

// 处理删除流程模板
const handleDeleteTemplate = (id: number) => {
  ElMessageBox.confirm('确定要删除这个流程模板吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除流程模板的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看流程模板
const handleViewTemplate = (row: any) => {
  // 这里添加查看流程模板的逻辑
  console.log('查看流程模板:', row)
}

// 处理流程模板提交
const handleTemplateSubmit = () => {
  // 这里添加提交流程模板的逻辑
  templateDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理发起流程
const handleStartProcess = () => {
  startProcessForm.value = {
    templateId: 0,
    processName: '',
    reason: ''
  }
  fileList.value = []
  startProcessDialogVisible.value = true
}

// 处理发起流程提交
const handleStartProcessSubmit = () => {
  // 这里添加提交发起流程的逻辑
  startProcessDialogVisible.value = false
  ElMessage.success('流程发起成功')
}

// 处理任务
const handleProcessTask = (row: any) => {
  taskForm.value = {
    taskName: row.taskName,
    processName: row.processName,
    applicant: row.applicant,
    reason: '',
    result: 'approved',
    comment: ''
  }
  taskDialogVisible.value = true
}

// 处理任务提交
const handleTaskSubmit = () => {
  // 这里添加提交任务处理的逻辑
  taskDialogVisible.value = false
  ElMessage.success('任务处理成功')
}

// 处理查看任务
const handleViewTask = (row: any) => {
  // 这里添加查看任务的逻辑
  console.log('查看任务:', row)
}

// 处理查看流程实例
const handleViewInstance = (row: any) => {
  // 这里添加查看流程实例的逻辑
  console.log('查看流程实例:', row)
}

// 处理终止流程实例
const handleTerminateInstance = (id: number) => {
  ElMessageBox.confirm('确定要终止这个流程实例吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加终止流程实例的逻辑
    ElMessage.success('流程终止成功')
  }).catch(() => {
    // 取消终止
  })
}

// 处理文件预览
const handlePreview = (file: any) => {
  console.log('预览文件:', file)
}

// 处理文件移除
const handleRemove = (file: any, fileList: any) => {
  console.log('移除文件:', file, fileList)
}

// 处理搜索
const handleTodoSearch = () => console.log('待办任务搜索条件:', todoSearch.value)
const handleDoneSearch = () => console.log('已办任务搜索条件:', doneSearch.value)
const handleTemplateSearch = () => console.log('流程模板搜索条件:', templateSearch.value)
const handleInstanceSearch = () => console.log('流程实例搜索条件:', instanceSearch.value)

// 处理重置
const handleTodoReset = () => todoSearch.value = { keyword: '', processType: '0' }
const handleDoneReset = () => doneSearch.value = { keyword: '', dateRange: [] }
const handleTemplateReset = () => templateSearch.value = { keyword: '' }
const handleInstanceReset = () => instanceSearch.value = { keyword: '', status: '0' }

// 处理分页
const handleTodoSizeChange = (size: number) => { todoPageSize.value = size }
const handleTodoCurrentChange = (current: number) => { todoCurrentPage.value = current }
const handleDoneSizeChange = (size: number) => { donePageSize.value = size }
const handleDoneCurrentChange = (current: number) => { doneCurrentPage.value = current }
const handleTemplateSizeChange = (size: number) => { templatePageSize.value = size }
const handleTemplateCurrentChange = (current: number) => { templateCurrentPage.value = current }
const handleInstanceSizeChange = (size: number) => { instancePageSize.value = size }
const handleInstanceCurrentChange = (current: number) => { instanceCurrentPage.value = current }

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
})
</script>

<style scoped>
.workflow-container {
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