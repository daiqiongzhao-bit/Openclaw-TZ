<template>
  <div class="performance-container">
    <div class="page-header">
      <h2>绩效管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddTemplate">
          <el-icon><Plus /></el-icon>
          新增绩效模板
        </el-button>
        <el-button type="success" @click="handleAddAppraisal">
          <el-icon><Star /></el-icon>
          新增绩效评估
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="绩效模板" name="template">
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

          <el-table :data="performanceTemplates" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="模板名称" width="150" />
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="createTime" label="创建时间" width="180" />
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

      <el-tab-pane label="绩效评估" name="appraisal">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="appraisalSearch.keyword"
              placeholder="请输入员工姓名或工号"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="appraisalSearch.period" placeholder="请选择评估周期" style="width: 150px; margin-left: 10px">
              <el-option label="2024年Q1" value="2024Q1" />
              <el-option label="2024年Q2" value="2024Q2" />
              <el-option label="2024年Q3" value="2024Q3" />
              <el-option label="2024年Q4" value="2024Q4" />
            </el-select>
            <el-button type="primary" @click="handleAppraisalSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleAppraisalReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="performanceAppraisals" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeId" label="工号" width="100" />
            <el-table-column prop="employeeName" label="姓名" width="120" />
            <el-table-column prop="period" label="评估周期" width="120" />
            <el-table-column prop="templateName" label="评估模板" width="150" />
            <el-table-column prop="score" label="得分" width="100">
              <template #default="scope">
                <span :style="{ color: getScoreColor(scope.row.score) }">
                  {{ scope.row.score }}
                </span>
              </template>
            </el-table-column>
            <el-table-column prop="level" label="等级" width="100">
              <template #default="scope">
                <el-tag :type="getLevelType(scope.row.level)">
                  {{ scope.row.level }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '已完成' : '进行中' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewAppraisal(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditAppraisal(scope.row)">
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="appraisalCurrentPage"
              v-model:page-size="appraisalPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="appraisalTotal"
              @size-change="handleAppraisalSizeChange"
              @current-change="handleAppraisalCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="绩效目标" name="goal">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="goalSearch.keyword"
              placeholder="请输入员工姓名或目标名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="goalSearch.status" placeholder="请选择状态" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="未开始" value="1" />
              <el-option label="进行中" value="2" />
              <el-option label="已完成" value="3" />
            </el-select>
            <el-button type="primary" @click="handleGoalSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleGoalReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="performanceGoals" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeId" label="工号" width="100" />
            <el-table-column prop="employeeName" label="姓名" width="120" />
            <el-table-column prop="goalName" label="目标名称" width="180" />
            <el-table-column prop="target" label="目标值" width="120" />
            <el-table-column prop="actual" label="实际值" width="120" />
            <el-table-column prop="progress" label="进度" width="120">
              <template #default="scope">
                <el-progress :percentage="scope.row.progress" :color="getProgressColor(scope.row.progress)" />
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ getStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewGoal(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditGoal(scope.row)">
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="goalCurrentPage"
              v-model:page-size="goalPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="goalTotal"
              @size-change="handleGoalSizeChange"
              @current-change="handleGoalCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="绩效分析" name="analysis">
        <div class="liquid-glass-card">
          <div class="stats-grid">
            <div class="stats-card">
              <h3>绩效概览</h3>
              <div class="stats-item">
                <span class="stats-label">平均得分</span>
                <span class="stats-value">85.5</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">优秀率</span>
                <span class="stats-value">25%</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">良好率</span>
                <span class="stats-value">60%</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">合格率</span>
                <span class="stats-value">15%</span>
              </div>
            </div>
            <div class="stats-card">
              <h3>部门绩效</h3>
              <div class="stats-item">
                <span class="stats-label">技术部</span>
                <span class="stats-value">88.2</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">人力资源部</span>
                <span class="stats-value">82.5</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">财务部</span>
                <span class="stats-value">86.7</span>
              </div>
            </div>
          </div>
          <div class="chart-container">
            <h3>绩效分布</h3>
            <div id="performanceChart" style="width: 100%; height: 400px;"></div>
          </div>
          <div class="chart-container">
            <h3>绩效趋势</h3>
            <div id="trendChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 绩效模板对话框 -->
    <el-dialog
      v-model="templateDialogVisible"
      :title="templateDialogTitle"
      width="600px"
    >
      <el-form :model="templateForm" label-width="120px">
        <el-form-item label="模板名称">
          <el-input v-model="templateForm.name" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="templateForm.description"
            type="textarea"
            placeholder="请输入描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="评估指标">
          <el-table :data="templateForm.indicators" style="width: 100%">
            <el-table-column prop="name" label="指标名称" width="150" />
            <el-table-column prop="weight" label="权重" width="100" />
            <el-table-column prop="description" label="描述" />
            <el-table-column label="操作" width="100">
              <template #default="scope">
                <el-button size="small" type="danger" @click="handleDeleteIndicator(scope.$index)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" size="small" @click="handleAddIndicator" style="margin-top: 10px">
            添加指标
          </el-button>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="templateDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleTemplateSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 绩效评估对话框 -->
    <el-dialog
      v-model="appraisalDialogVisible"
      :title="appraisalDialogTitle"
      width="600px"
    >
      <el-form :model="appraisalForm" label-width="120px">
        <el-form-item label="员工">
          <el-select v-model="appraisalForm.employeeId" placeholder="请选择员工">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评估周期">
          <el-select v-model="appraisalForm.period" placeholder="请选择评估周期">
            <el-option label="2024年Q1" value="2024Q1" />
            <el-option label="2024年Q2" value="2024Q2" />
            <el-option label="2024年Q3" value="2024Q3" />
            <el-option label="2024年Q4" value="2024Q4" />
          </el-select>
        </el-form-item>
        <el-form-item label="评估模板">
          <el-select v-model="appraisalForm.templateId" placeholder="请选择评估模板">
            <el-option
              v-for="template in performanceTemplates"
              :key="template.id"
              :label="template.name"
              :value="template.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="得分">
          <el-input v-model="appraisalForm.score" placeholder="请输入得分" type="number" />
        </el-form-item>
        <el-form-item label="等级">
          <el-select v-model="appraisalForm.level" placeholder="请选择等级">
            <el-option label="优秀" value="优秀" />
            <el-option label="良好" value="良好" />
            <el-option label="合格" value="合格" />
            <el-option label="不合格" value="不合格" />
          </el-select>
        </el-form-item>
        <el-form-item label="评价">
          <el-input
            v-model="appraisalForm.evaluation"
            type="textarea"
            placeholder="请输入评价"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="appraisalDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAppraisalSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { Plus, Search, Star } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import * as echarts from 'echarts'

// 激活的标签页
const activeTab = ref('template')

// 员工列表
const employees = ref([
  { id: 1, name: '张三' },
  { id: 2, name: '李四' },
  { id: 3, name: '王五' }
])

// 绩效模板数据
const performanceTemplates = ref([
  {
    id: 1,
    name: '员工绩效模板',
    description: '适用于一般员工的绩效评估模板',
    createTime: '2024-01-01 00:00:00'
  },
  {
    id: 2,
    name: '管理层绩效模板',
    description: '适用于管理层的绩效评估模板',
    createTime: '2024-01-01 00:00:00'
  }
])

// 绩效评估数据
const performanceAppraisals = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    period: '2024Q1',
    templateId: 1,
    templateName: '员工绩效模板',
    score: 92,
    level: '优秀',
    status: 1
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    period: '2024Q1',
    templateId: 1,
    templateName: '员工绩效模板',
    score: 85,
    level: '良好',
    status: 1
  },
  {
    id: 3,
    employeeId: 3,
    employeeName: '王五',
    period: '2024Q1',
    templateId: 1,
    templateName: '员工绩效模板',
    score: 78,
    level: '合格',
    status: 0
  }
])

// 绩效目标数据
const performanceGoals = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    goalName: '完成项目A',
    target: '100%',
    actual: '95%',
    progress: 95,
    status: 2
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    goalName: '销售目标',
    target: '100万',
    actual: '80万',
    progress: 80,
    status: 2
  },
  {
    id: 3,
    employeeId: 3,
    employeeName: '王五',
    goalName: '招聘计划',
    target: '10人',
    actual: '10人',
    progress: 100,
    status: 3
  }
])

// 搜索条件
const templateSearch = ref({ keyword: '' })
const appraisalSearch = ref({ keyword: '', period: '' })
const goalSearch = ref({ keyword: '', status: '0' })

// 分页参数
const templateCurrentPage = ref(1)
const templatePageSize = ref(10)
const templateTotal = ref(2)

const appraisalCurrentPage = ref(1)
const appraisalPageSize = ref(10)
const appraisalTotal = ref(3)

const goalCurrentPage = ref(1)
const goalPageSize = ref(10)
const goalTotal = ref(3)

// 对话框状态
const templateDialogVisible = ref(false)
const templateDialogTitle = ref('新增绩效模板')

const appraisalDialogVisible = ref(false)
const appraisalDialogTitle = ref('新增绩效评估')

// 表单数据
const templateForm = ref({
  id: 0,
  name: '',
  description: '',
  indicators: [
    { name: '工作完成度', weight: '40%', description: '任务完成情况' },
    { name: '工作质量', weight: '30%', description: '工作成果质量' },
    { name: '团队合作', weight: '20%', description: '与团队成员协作情况' },
    { name: '创新能力', weight: '10%', description: '创新思维和方法' }
  ]
})

const appraisalForm = ref({
  id: 0,
  employeeId: 0,
  period: '',
  templateId: 0,
  score: 0,
  level: '',
  evaluation: ''
})

// 图表实例
let performanceChart: echarts.ECharts | null = null
let trendChart: echarts.ECharts | null = null

// 获取得分颜色
const getScoreColor = (score: number) => {
  if (score >= 90) return '#67C23A'
  if (score >= 80) return '#E6A23C'
  if (score >= 70) return '#F56C6C'
  return '#909399'
}

// 获取等级类型
const getLevelType = (level: string) => {
  switch (level) {
    case '优秀': return 'success'
    case '良好': return 'warning'
    case '合格': return 'info'
    case '不合格': return 'danger'
    default: return 'info'
  }
}

// 获取进度颜色
const getProgressColor = (progress: number) => {
  if (progress >= 90) return '#67C23A'
  if (progress >= 70) return '#E6A23C'
  return '#F56C6C'
}

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
    case 1: return '未开始'
    case 2: return '进行中'
    case 3: return '已完成'
    default: return '未知'
  }
}

// 处理新增绩效模板
const handleAddTemplate = () => {
  templateDialogTitle.value = '新增绩效模板'
  templateForm.value = {
    id: 0,
    name: '',
    description: '',
    indicators: []
  }
  templateDialogVisible.value = true
}

// 处理编辑绩效模板
const handleEditTemplate = (row: any) => {
  templateDialogTitle.value = '编辑绩效模板'
  templateForm.value = { ...row }
  templateDialogVisible.value = true
}

// 处理删除绩效模板
const handleDeleteTemplate = (id: number) => {
  ElMessageBox.confirm('确定要删除这个绩效模板吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除绩效模板的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看绩效模板
const handleViewTemplate = (row: any) => {
  // 这里添加查看绩效模板的逻辑
  console.log('查看绩效模板:', row)
}

// 处理添加评估指标
const handleAddIndicator = () => {
  templateForm.value.indicators.push({ name: '', weight: '', description: '' })
}

// 处理删除评估指标
const handleDeleteIndicator = (index: number) => {
  templateForm.value.indicators.splice(index, 1)
}

// 处理绩效模板提交
const handleTemplateSubmit = () => {
  // 这里添加提交绩效模板的逻辑
  templateDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增绩效评估
const handleAddAppraisal = () => {
  appraisalDialogTitle.value = '新增绩效评估'
  appraisalForm.value = {
    id: 0,
    employeeId: 0,
    period: '',
    templateId: 0,
    score: 0,
    level: '',
    evaluation: ''
  }
  appraisalDialogVisible.value = true
}

// 处理编辑绩效评估
const handleEditAppraisal = (row: any) => {
  appraisalDialogTitle.value = '编辑绩效评估'
  appraisalForm.value = { ...row }
  appraisalDialogVisible.value = true
}

// 处理查看绩效评估
const handleViewAppraisal = (row: any) => {
  // 这里添加查看绩效评估的逻辑
  console.log('查看绩效评估:', row)
}

// 处理绩效评估提交
const handleAppraisalSubmit = () => {
  // 这里添加提交绩效评估的逻辑
  appraisalDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理查看绩效目标
const handleViewGoal = (row: any) => {
  // 这里添加查看绩效目标的逻辑
  console.log('查看绩效目标:', row)
}

// 处理编辑绩效目标
const handleEditGoal = (row: any) => {
  // 这里添加编辑绩效目标的逻辑
  console.log('编辑绩效目标:', row)
}

// 处理搜索
const handleTemplateSearch = () => console.log('绩效模板搜索条件:', templateSearch.value)
const handleAppraisalSearch = () => console.log('绩效评估搜索条件:', appraisalSearch.value)
const handleGoalSearch = () => console.log('绩效目标搜索条件:', goalSearch.value)

// 处理重置
const handleTemplateReset = () => templateSearch.value = { keyword: '' }
const handleAppraisalReset = () => appraisalSearch.value = { keyword: '', period: '' }
const handleGoalReset = () => goalSearch.value = { keyword: '', status: '0' }

// 处理分页
const handleTemplateSizeChange = (size: number) => { templatePageSize.value = size }
const handleTemplateCurrentChange = (current: number) => { templateCurrentPage.value = current }
const handleAppraisalSizeChange = (size: number) => { appraisalPageSize.value = size }
const handleAppraisalCurrentChange = (current: number) => { appraisalCurrentPage.value = current }
const handleGoalSizeChange = (size: number) => { goalPageSize.value = size }
const handleGoalCurrentChange = (current: number) => { goalCurrentPage.value = current }

// 初始化图表
const initCharts = () => {
  // 绩效分布图表
  const performanceChartDom = document.getElementById('performanceChart')
  if (performanceChartDom) {
    performanceChart = echarts.init(performanceChartDom)
    const performanceOption = {
      title: {
        text: '绩效等级分布'
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
          name: '绩效等级',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 25, name: '优秀' },
            { value: 60, name: '良好' },
            { value: 15, name: '合格' }
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
    performanceChart.setOption(performanceOption)
  }

  // 绩效趋势图表
  const trendChartDom = document.getElementById('trendChart')
  if (trendChartDom) {
    trendChart = echarts.init(trendChartDom)
    const trendOption = {
      title: {
        text: '绩效趋势变化'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['技术部', '人力资源部', '财务部']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['2023Q1', '2023Q2', '2023Q3', '2023Q4', '2024Q1']
      },
      yAxis: {
        type: 'value',
        min: 70,
        max: 100
      },
      series: [
        {
          name: '技术部',
          type: 'line',
          data: [82, 85, 86, 87, 88]
        },
        {
          name: '人力资源部',
          type: 'line',
          data: [78, 80, 81, 82, 83]
        },
        {
          name: '财务部',
          type: 'line',
          data: [80, 82, 84, 85, 87]
        }
      ]
    }
    trendChart.setOption(trendOption)
  }
}

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
  initCharts()
})

// 组件卸载时执行
onUnmounted(() => {
  if (performanceChart) {
    performanceChart.dispose()
  }
  if (trendChart) {
    trendChart.dispose()
  }
})
</script>

<style scoped>
.performance-container {
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