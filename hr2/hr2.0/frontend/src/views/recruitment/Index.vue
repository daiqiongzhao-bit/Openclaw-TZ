<template>
  <div class="recruitment-container">
    <div class="page-header">
      <h2>招聘管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddRequirement">
          <el-icon><Plus /></el-icon>
          新增招聘需求
        </el-button>
        <el-button type="success" @click="handleAddResume">
          <el-icon><Document /></el-icon>
          新增简历
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="招聘需求" name="requirement">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="requirementSearch.keyword"
              placeholder="请输入职位名称或部门"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="requirementSearch.status" placeholder="请选择状态" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="待审批" value="1" />
              <el-option label="已批准" value="2" />
              <el-option label="已完成" value="3" />
            </el-select>
            <el-button type="primary" @click="handleRequirementSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleRequirementReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="recruitmentRequirements" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="positionName" label="职位名称" width="150" />
            <el-table-column prop="departmentName" label="部门" width="120" />
            <el-table-column prop="recruitNumber" label="招聘人数" width="100" />
            <el-table-column prop="salaryRange" label="薪资范围" width="150" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ getStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewRequirement(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditRequirement(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteRequirement(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="requirementCurrentPage"
              v-model:page-size="requirementPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="requirementTotal"
              @size-change="handleRequirementSizeChange"
              @current-change="handleRequirementCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="简历管理" name="resume">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="resumeSearch.keyword"
              placeholder="请输入姓名或职位"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="resumeSearch.education" placeholder="请选择学历" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="本科" value="1" />
              <el-option label="硕士" value="2" />
              <el-option label="博士" value="3" />
            </el-select>
            <el-button type="primary" @click="handleResumeSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleResumeReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="resumes" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="姓名" width="100" />
            <el-table-column prop="position" label="应聘职位" width="150" />
            <el-table-column prop="education" label="学历" width="100">
              <template #default="scope">
                {{ getEducationText(scope.row.education) }}
              </template>
            </el-table-column>
            <el-table-column prop="workExperience" label="工作经验" width="120" />
            <el-table-column prop="phone" label="电话" width="150" />
            <el-table-column prop="email" label="邮箱" width="200" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getResumeStatusType(scope.row.status)">
                  {{ getResumeStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewResume(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditResume(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteResume(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="resumeCurrentPage"
              v-model:page-size="resumePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="resumeTotal"
              @size-change="handleResumeSizeChange"
              @current-change="handleResumeCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="面试记录" name="interview">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="interviewSearch.keyword"
              placeholder="请输入姓名或职位"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="interviewSearch.round" placeholder="请选择面试轮次" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="初面" value="1" />
              <el-option label="复面" value="2" />
              <el-option label="终面" value="3" />
            </el-select>
            <el-button type="primary" @click="handleInterviewSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleInterviewReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="interviewRecords" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="resumeId" label="简历ID" width="100" />
            <el-table-column prop="candidateName" label="候选人" width="120" />
            <el-table-column prop="position" label="应聘职位" width="150" />
            <el-table-column prop="round" label="面试轮次" width="100">
              <template #default="scope">
                {{ getRoundText(scope.row.round) }}
              </template>
            </el-table-column>
            <el-table-column prop="interviewer" label="面试官" width="120" />
            <el-table-column prop="interviewDate" label="面试日期" width="150" />
            <el-table-column prop="result" label="面试结果" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.result === 1 ? 'success' : 'danger'">
                  {{ scope.row.result === 1 ? '通过' : '未通过' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewInterview(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditInterview(scope.row)">
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="interviewCurrentPage"
              v-model:page-size="interviewPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="interviewTotal"
              @size-change="handleInterviewSizeChange"
              @current-change="handleInterviewCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="招聘分析" name="analysis">
        <div class="liquid-glass-card">
          <div class="stats-grid">
            <div class="stats-card">
              <h3>招聘概览</h3>
              <div class="stats-item">
                <span class="stats-label">待招聘职位</span>
                <span class="stats-value">10</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">收到简历</span>
                <span class="stats-value">120</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">面试人数</span>
                <span class="stats-value">30</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">录用人数</span>
                <span class="stats-value">5</span>
              </div>
            </div>
            <div class="stats-card">
              <h3>招聘效率</h3>
              <div class="stats-item">
                <span class="stats-label">简历通过率</span>
                <span class="stats-value">25%</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">面试通过率</span>
                <span class="stats-value">17%</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">平均招聘周期</span>
                <span class="stats-value">30天</span>
              </div>
            </div>
          </div>
          <div class="chart-container">
            <h3>招聘渠道分析</h3>
            <div id="channelChart" style="width: 100%; height: 400px;"></div>
          </div>
          <div class="chart-container">
            <h3>招聘漏斗</h3>
            <div id="funnelChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 招聘需求对话框 -->
    <el-dialog
      v-model="requirementDialogVisible"
      :title="requirementDialogTitle"
      width="600px"
    >
      <el-form :model="requirementForm" label-width="120px">
        <el-form-item label="职位名称">
          <el-input v-model="requirementForm.positionName" placeholder="请输入职位名称" />
        </el-form-item>
        <el-form-item label="所属部门">
          <el-select v-model="requirementForm.departmentId" placeholder="请选择部门">
            <el-option
              v-for="dept in departments"
              :key="dept.id"
              :label="dept.name"
              :value="dept.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="招聘人数">
          <el-input v-model="requirementForm.recruitNumber" placeholder="请输入招聘人数" type="number" />
        </el-form-item>
        <el-form-item label="薪资范围">
          <el-input v-model="requirementForm.salaryRange" placeholder="请输入薪资范围" />
        </el-form-item>
        <el-form-item label="职位描述">
          <el-input
            v-model="requirementForm.jobDescription"
            type="textarea"
            placeholder="请输入职位描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="任职要求">
          <el-input
            v-model="requirementForm.requirements"
            type="textarea"
            placeholder="请输入任职要求"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="requirementDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleRequirementSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 简历对话框 -->
    <el-dialog
      v-model="resumeDialogVisible"
      :title="resumeDialogTitle"
      width="600px"
    >
      <el-form :model="resumeForm" label-width="120px">
        <el-form-item label="姓名">
          <el-input v-model="resumeForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="应聘职位">
          <el-input v-model="resumeForm.position" placeholder="请输入应聘职位" />
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="resumeForm.education" placeholder="请选择学历">
            <el-option label="本科" value="1" />
            <el-option label="硕士" value="2" />
            <el-option label="博士" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="工作经验">
          <el-input v-model="resumeForm.workExperience" placeholder="请输入工作经验" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="resumeForm.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="resumeForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="简历来源">
          <el-input v-model="resumeForm.source" placeholder="请输入简历来源" />
        </el-form-item>
        <el-form-item label="简历附件">
          <el-upload
            class="upload-demo"
            action="#"
            :on-change="handleFileChange"
            :auto-upload="false"
          >
            <el-button type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                只能上传 PDF 文件，且不超过 5MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resumeDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleResumeSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { Plus, Search, Document } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import * as echarts from 'echarts'

// 激活的标签页
const activeTab = ref('requirement')

// 部门列表
const departments = ref([
  { id: 1, name: '技术部' },
  { id: 2, name: '人力资源部' },
  { id: 3, name: '财务部' },
  { id: 4, name: '市场部' }
])

// 招聘需求数据
const recruitmentRequirements = ref([
  {
    id: 1,
    positionName: '前端开发工程师',
    departmentId: 1,
    departmentName: '技术部',
    recruitNumber: 2,
    salaryRange: '15k-25k',
    status: 2,
    createTime: '2024-01-01 00:00:00'
  },
  {
    id: 2,
    positionName: '后端开发工程师',
    departmentId: 1,
    departmentName: '技术部',
    recruitNumber: 3,
    salaryRange: '18k-28k',
    status: 2,
    createTime: '2024-01-02 00:00:00'
  },
  {
    id: 3,
    positionName: 'HR专员',
    departmentId: 2,
    departmentName: '人力资源部',
    recruitNumber: 1,
    salaryRange: '8k-12k',
    status: 1,
    createTime: '2024-01-03 00:00:00'
  }
])

// 简历数据
const resumes = ref([
  {
    id: 1,
    name: '张三',
    position: '前端开发工程师',
    education: 1,
    workExperience: '3年',
    phone: '13800138001',
    email: 'zhangsan@example.com',
    status: 2
  },
  {
    id: 2,
    name: '李四',
    position: '后端开发工程师',
    education: 2,
    workExperience: '5年',
    phone: '13800138002',
    email: 'lisi@example.com',
    status: 3
  },
  {
    id: 3,
    name: '王五',
    position: 'HR专员',
    education: 1,
    workExperience: '2年',
    phone: '13800138003',
    email: 'wangwu@example.com',
    status: 1
  }
])

// 面试记录数据
const interviewRecords = ref([
  {
    id: 1,
    resumeId: 1,
    candidateName: '张三',
    position: '前端开发工程师',
    round: 1,
    interviewer: '赵经理',
    interviewDate: '2024-01-10 10:00:00',
    result: 1
  },
  {
    id: 2,
    resumeId: 2,
    candidateName: '李四',
    position: '后端开发工程师',
    round: 2,
    interviewer: '钱总监',
    interviewDate: '2024-01-11 14:00:00',
    result: 1
  },
  {
    id: 3,
    resumeId: 3,
    candidateName: '王五',
    position: 'HR专员',
    round: 1,
    interviewer: '孙经理',
    interviewDate: '2024-01-12 09:00:00',
    result: 0
  }
])

// 搜索条件
const requirementSearch = ref({ keyword: '', status: '0' })
const resumeSearch = ref({ keyword: '', education: '0' })
const interviewSearch = ref({ keyword: '', round: '0' })

// 分页参数
const requirementCurrentPage = ref(1)
const requirementPageSize = ref(10)
const requirementTotal = ref(3)

const resumeCurrentPage = ref(1)
const resumePageSize = ref(10)
const resumeTotal = ref(3)

const interviewCurrentPage = ref(1)
const interviewPageSize = ref(10)
const interviewTotal = ref(3)

// 对话框状态
const requirementDialogVisible = ref(false)
const requirementDialogTitle = ref('新增招聘需求')

const resumeDialogVisible = ref(false)
const resumeDialogTitle = ref('新增简历')

// 表单数据
const requirementForm = ref({
  id: 0,
  positionName: '',
  departmentId: 0,
  recruitNumber: 0,
  salaryRange: '',
  jobDescription: '',
  requirements: ''
})

const resumeForm = ref({
  id: 0,
  name: '',
  position: '',
  education: 1,
  workExperience: '',
  phone: '',
  email: '',
  source: ''
})

// 图表实例
let channelChart: echarts.ECharts | null = null
let funnelChart: echarts.ECharts | null = null

// 获取状态类型
const getStatusType = (status: number) => {
  switch (status) {
    case 1: return 'info'
    case 2: return 'warning'
    case 3: return 'success'
    default: return 'info'
  }
}

// 获取状态文本
const getStatusText = (status: number) => {
  switch (status) {
    case 1: return '待审批'
    case 2: return '已批准'
    case 3: return '已完成'
    default: return '未知'
  }
}

// 获取学历文本
const getEducationText = (education: number) => {
  switch (education) {
    case 1: return '本科'
    case 2: return '硕士'
    case 3: return '博士'
    default: return '未知'
  }
}

// 获取简历状态类型
const getResumeStatusType = (status: number) => {
  switch (status) {
    case 1: return 'info'
    case 2: return 'warning'
    case 3: return 'success'
    case 4: return 'danger'
    default: return 'info'
  }
}

// 获取简历状态文本
const getResumeStatusText = (status: number) => {
  switch (status) {
    case 1: return '待筛选'
    case 2: return '已筛选'
    case 3: return '面试中'
    case 4: return '已拒绝'
    default: return '未知'
  }
}

// 获取面试轮次文本
const getRoundText = (round: number) => {
  switch (round) {
    case 1: return '初面'
    case 2: return '复面'
    case 3: return '终面'
    default: return '未知'
  }
}

// 处理文件上传
const handleFileChange = (file: any) => {
  console.log('文件上传:', file)
}

// 处理新增招聘需求
const handleAddRequirement = () => {
  requirementDialogTitle.value = '新增招聘需求'
  requirementForm.value = {
    id: 0,
    positionName: '',
    departmentId: 0,
    recruitNumber: 0,
    salaryRange: '',
    jobDescription: '',
    requirements: ''
  }
  requirementDialogVisible.value = true
}

// 处理编辑招聘需求
const handleEditRequirement = (row: any) => {
  requirementDialogTitle.value = '编辑招聘需求'
  requirementForm.value = { ...row }
  requirementDialogVisible.value = true
}

// 处理删除招聘需求
const handleDeleteRequirement = (id: number) => {
  ElMessageBox.confirm('确定要删除这个招聘需求吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除招聘需求的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看招聘需求
const handleViewRequirement = (row: any) => {
  // 这里添加查看招聘需求的逻辑
  console.log('查看招聘需求:', row)
}

// 处理招聘需求提交
const handleRequirementSubmit = () => {
  // 这里添加提交招聘需求的逻辑
  requirementDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增简历
const handleAddResume = () => {
  resumeDialogTitle.value = '新增简历'
  resumeForm.value = {
    id: 0,
    name: '',
    position: '',
    education: 1,
    workExperience: '',
    phone: '',
    email: '',
    source: ''
  }
  resumeDialogVisible.value = true
}

// 处理编辑简历
const handleEditResume = (row: any) => {
  resumeDialogTitle.value = '编辑简历'
  resumeForm.value = { ...row }
  resumeDialogVisible.value = true
}

// 处理删除简历
const handleDeleteResume = (id: number) => {
  ElMessageBox.confirm('确定要删除这个简历吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除简历的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看简历
const handleViewResume = (row: any) => {
  // 这里添加查看简历的逻辑
  console.log('查看简历:', row)
}

// 处理简历提交
const handleResumeSubmit = () => {
  // 这里添加提交简历的逻辑
  resumeDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理查看面试记录
const handleViewInterview = (row: any) => {
  // 这里添加查看面试记录的逻辑
  console.log('查看面试记录:', row)
}

// 处理编辑面试记录
const handleEditInterview = (row: any) => {
  // 这里添加编辑面试记录的逻辑
  console.log('编辑面试记录:', row)
}

// 处理搜索
const handleRequirementSearch = () => console.log('招聘需求搜索条件:', requirementSearch.value)
const handleResumeSearch = () => console.log('简历搜索条件:', resumeSearch.value)
const handleInterviewSearch = () => console.log('面试记录搜索条件:', interviewSearch.value)

// 处理重置
const handleRequirementReset = () => requirementSearch.value = { keyword: '', status: '0' }
const handleResumeReset = () => resumeSearch.value = { keyword: '', education: '0' }
const handleInterviewReset = () => interviewSearch.value = { keyword: '', round: '0' }

// 处理分页
const handleRequirementSizeChange = (size: number) => { requirementPageSize.value = size }
const handleRequirementCurrentChange = (current: number) => { requirementCurrentPage.value = current }
const handleResumeSizeChange = (size: number) => { resumePageSize.value = size }
const handleResumeCurrentChange = (current: number) => { resumeCurrentPage.value = current }
const handleInterviewSizeChange = (size: number) => { interviewPageSize.value = size }
const handleInterviewCurrentChange = (current: number) => { interviewCurrentPage.value = current }

// 初始化图表
const initCharts = () => {
  // 招聘渠道分析图表
  const channelChartDom = document.getElementById('channelChart')
  if (channelChartDom) {
    channelChart = echarts.init(channelChartDom)
    const channelOption = {
      title: {
        text: '招聘渠道分析'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '招聘渠道',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 40, name: '招聘网站' },
            { value: 25, name: '内部推荐' },
            { value: 15, name: '校园招聘' },
            { value: 10, name: '社交媒体' },
            { value: 10, name: '其他' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
    channelChart.setOption(channelOption)
  }

  // 招聘漏斗图表
  const funnelChartDom = document.getElementById('funnelChart')
  if (funnelChartDom) {
    funnelChart = echarts.init(funnelChartDom)
    const funnelOption = {
      title: {
        text: '招聘漏斗'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c}人 ({d}%)'
      },
      legend: {
        data: ['简历筛选', '初试', '复试', '终试', '录用']
      },
      series: [
        {
          name: '招聘漏斗',
          type: 'funnel',
          left: '10%',
          width: '80%',
          data: [
            { value: 120, name: '简历筛选' },
            { value: 60, name: '初试' },
            { value: 30, name: '复试' },
            { value: 15, name: '终试' },
            { value: 5, name: '录用' }
          ]
        }
      ]
    }
    funnelChart.setOption(funnelOption)
  }
}

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
  initCharts()
})

// 组件卸载时执行
onUnmounted(() => {
  if (channelChart) {
    channelChart.dispose()
  }
  if (funnelChart) {
    funnelChart.dispose()
  }
})
</script>

<style scoped>
.recruitment-container {
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

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stats-card {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
}

.stats-card h3 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.stats-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e0e0e0;
}

.stats-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.stats-label {
  font-size: 14px;
  color: #666;
}

.stats-value {
  font-size: 18px;
  font-weight: 600;
  color: #10D9A3;
}

.chart-container {
  margin-top: 30px;
}

.chart-container h3 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}
</style>