<template>
  <div class="bi-container">
    <div class="page-header">
      <h2>人力数据分析</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddDashboard">
          <el-icon><DataAnalysis /></el-icon>
          新增仪表盘
        </el-button>
        <el-button type="success" @click="handleAddReport">
          <el-icon><Document /></el-icon>
          新增报表
        </el-button>
        <el-button type="warning" @click="handleAddAlert">
          <el-icon><Warning /></el-icon>
          新增数据预警
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="仪表盘" name="dashboard">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="dashboardSearch.keyword"
              placeholder="请输入仪表盘名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleDashboardSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleDashboardReset" style="margin-left: 10px">重置</el-button>
          </div>

          <div class="dashboard-grid">
            <div class="dashboard-card" v-for="dashboard in dashboards" :key="dashboard.id">
              <div class="dashboard-header">
                <h3>{{ dashboard.name }}</h3>
                <div class="dashboard-actions">
                  <el-button size="small" type="primary" @click="handleViewDashboard(dashboard)">
                    查看
                  </el-button>
                  <el-button size="small" type="warning" @click="handleEditDashboard(dashboard)">
                    编辑
                  </el-button>
                  <el-button size="small" type="danger" @click="handleDeleteDashboard(dashboard.id)">
                    删除
                  </el-button>
                </div>
              </div>
              <div class="dashboard-content">
                <p>{{ dashboard.description }}</p>
                <div class="dashboard-stats">
                  <span class="stat-item">图表数: {{ dashboard.chartCount }}</span>
                  <span class="stat-item">创建时间: {{ dashboard.createTime }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="pagination">
            <el-pagination
              v-model:current-page="dashboardCurrentPage"
              v-model:page-size="dashboardPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="dashboardTotal"
              @size-change="handleDashboardSizeChange"
              @current-change="handleDashboardCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="报表管理" name="report">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="reportSearch.keyword"
              placeholder="请输入报表名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="reportSearch.type" placeholder="请选择报表类型" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="人员报表" value="1" />
              <el-option label="考勤报表" value="2" />
              <el-option label="薪酬报表" value="3" />
              <el-option label="绩效报表" value="4" />
            </el-select>
            <el-button type="primary" @click="handleReportSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleReportReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="reports" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="报表名称" />
            <el-table-column prop="type" label="报表类型" width="120">
              <template #default="scope">
                {{ getReportTypeText(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewReport(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditReport(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteReport(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="reportCurrentPage"
              v-model:page-size="reportPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="reportTotal"
              @size-change="handleReportSizeChange"
              @current-change="handleReportCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="数据预警" name="alert">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="alertSearch.keyword"
              placeholder="请输入预警名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="alertSearch.status" placeholder="请选择状态" style="width: 120px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="启用" value="1" />
              <el-option label="禁用" value="0" />
            </el-select>
            <el-button type="primary" @click="handleAlertSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleAlertReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="alerts" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="预警名称" />
            <el-table-column prop="type" label="预警类型" width="120">
              <template #default="scope">
                {{ getAlertTypeText(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column prop="threshold" label="阈值" width="100" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewAlert(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditAlert(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteAlert(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="alertCurrentPage"
              v-model:page-size="alertPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="alertTotal"
              @size-change="handleAlertSizeChange"
              @current-change="handleAlertCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="数据分析" name="analysis">
        <div class="liquid-glass-card">
          <div class="stats-grid">
            <div class="stats-card">
              <h3>人力资源概览</h3>
              <div class="stats-item">
                <span class="stats-label">总人数</span>
                <span class="stats-value">500</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">本月入职</span>
                <span class="stats-value">15</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">本月离职</span>
                <span class="stats-value">5</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">员工流失率</span>
                <span class="stats-value">1%</span>
              </div>
            </div>
            <div class="stats-card">
              <h3>部门分布</h3>
              <div id="departmentChart" style="width: 100%; height: 200px;"></div>
            </div>
            <div class="stats-card">
              <h3>人员结构</h3>
              <div id="structureChart" style="width: 100%; height: 200px;"></div>
            </div>
            <div class="stats-card">
              <h3>离职趋势</h3>
              <div id="trendChart" style="width: 100%; height: 200px;"></div>
            </div>
          </div>
          <div class="chart-container">
            <h3>员工满意度分析</h3>
            <div id="satisfactionChart" style="width: 100%; height: 400px;"></div>
          </div>
          <div class="chart-container">
            <h3>培训效果分析</h3>
            <div id="trainingChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 仪表盘对话框 -->
    <el-dialog
      v-model="dashboardDialogVisible"
      :title="dashboardDialogTitle"
      width="600px"
    >
      <el-form :model="dashboardForm" label-width="120px">
        <el-form-item label="仪表盘名称">
          <el-input v-model="dashboardForm.name" placeholder="请输入仪表盘名称" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="dashboardForm.description"
            type="textarea"
            placeholder="请输入仪表盘描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="图表数">
          <el-input v-model="dashboardForm.chartCount" placeholder="请输入图表数" type="number" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="dashboardForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dashboardDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleDashboardSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 报表对话框 -->
    <el-dialog
      v-model="reportDialogVisible"
      :title="reportDialogTitle"
      width="600px"
    >
      <el-form :model="reportForm" label-width="120px">
        <el-form-item label="报表名称">
          <el-input v-model="reportForm.name" placeholder="请输入报表名称" />
        </el-form-item>
        <el-form-item label="报表类型">
          <el-select v-model="reportForm.type" placeholder="请选择报表类型">
            <el-option label="人员报表" value="1" />
            <el-option label="考勤报表" value="2" />
            <el-option label="薪酬报表" value="3" />
            <el-option label="绩效报表" value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="reportForm.description"
            type="textarea"
            placeholder="请输入报表描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="生成周期">
          <el-select v-model="reportForm.period" placeholder="请选择生成周期">
            <el-option label="每日" value="1" />
            <el-option label="每周" value="2" />
            <el-option label="每月" value="3" />
            <el-option label="每年" value="4" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="reportDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleReportSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 数据预警对话框 -->
    <el-dialog
      v-model="alertDialogVisible"
      :title="alertDialogTitle"
      width="600px"
    >
      <el-form :model="alertForm" label-width="120px">
        <el-form-item label="预警名称">
          <el-input v-model="alertForm.name" placeholder="请输入预警名称" />
        </el-form-item>
        <el-form-item label="预警类型">
          <el-select v-model="alertForm.type" placeholder="请选择预警类型">
            <el-option label="人员流失" value="1" />
            <el-option label="考勤异常" value="2" />
            <el-option label="薪酬异常" value="3" />
            <el-option label="绩效异常" value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="阈值">
          <el-input v-model="alertForm.threshold" placeholder="请输入预警阈值" type="number" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="alertForm.description"
            type="textarea"
            placeholder="请输入预警描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="alertForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="alertDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAlertSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { DataAnalysis, Document, Warning, Search } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import * as echarts from 'echarts'

// 激活的标签页
const activeTab = ref('dashboard')

// 仪表盘数据
const dashboards = ref([
  {
    id: 1,
    name: '人力资源概览',
    description: '公司人力资源整体情况概览',
    chartCount: 8,
    createTime: '2024-01-01',
    status: 1
  },
  {
    id: 2,
    name: '招聘分析',
    description: '招聘数据统计分析',
    chartCount: 6,
    createTime: '2024-01-02',
    status: 1
  },
  {
    id: 3,
    name: '员工流失分析',
    description: '员工流失率分析',
    chartCount: 4,
    createTime: '2024-01-03',
    status: 1
  }
])

// 报表数据
const reports = ref([
  {
    id: 1,
    name: '月度人员报表',
    type: 1,
    description: '每月人员变动情况报表',
    createTime: '2024-01-01'
  },
  {
    id: 2,
    name: '考勤统计报表',
    type: 2,
    description: '员工考勤情况统计',
    createTime: '2024-01-02'
  },
  {
    id: 3,
    name: '薪酬分析报表',
    type: 3,
    description: '薪酬结构分析',
    createTime: '2024-01-03'
  }
])

// 数据预警数据
const alerts = ref([
  {
    id: 1,
    name: '人员流失预警',
    type: 1,
    threshold: 5,
    description: '当部门流失率超过5%时预警',
    status: 1
  },
  {
    id: 2,
    name: '考勤异常预警',
    type: 2,
    threshold: 3,
    description: '当员工月度迟到超过3次时预警',
    status: 1
  },
  {
    id: 3,
    name: '薪酬异常预警',
    type: 3,
    threshold: 10000,
    description: '当员工薪酬超过10000时预警',
    status: 0
  }
])

// 搜索条件
const dashboardSearch = ref({ keyword: '' })
const reportSearch = ref({ keyword: '', type: '0' })
const alertSearch = ref({ keyword: '', status: '0' })

// 分页参数
const dashboardCurrentPage = ref(1)
const dashboardPageSize = ref(10)
const dashboardTotal = ref(3)

const reportCurrentPage = ref(1)
const reportPageSize = ref(10)
const reportTotal = ref(3)

const alertCurrentPage = ref(1)
const alertPageSize = ref(10)
const alertTotal = ref(3)

// 对话框状态
const dashboardDialogVisible = ref(false)
const dashboardDialogTitle = ref('新增仪表盘')

const reportDialogVisible = ref(false)
const reportDialogTitle = ref('新增报表')

const alertDialogVisible = ref(false)
const alertDialogTitle = ref('新增数据预警')

// 表单数据
const dashboardForm = ref({
  id: 0,
  name: '',
  description: '',
  chartCount: 0,
  status: 1
})

const reportForm = ref({
  id: 0,
  name: '',
  type: 1,
  description: '',
  period: 3
})

const alertForm = ref({
  id: 0,
  name: '',
  type: 1,
  threshold: 0,
  description: '',
  status: 1
})

// 图表实例
let departmentChart: echarts.ECharts | null = null
let structureChart: echarts.ECharts | null = null
let trendChart: echarts.ECharts | null = null
let satisfactionChart: echarts.ECharts | null = null
let trainingChart: echarts.ECharts | null = null

// 获取报表类型文本
const getReportTypeText = (type: number) => {
  switch (type) {
    case 1: return '人员报表'
    case 2: return '考勤报表'
    case 3: return '薪酬报表'
    case 4: return '绩效报表'
    default: return '未知'
  }
}

// 获取预警类型文本
const getAlertTypeText = (type: number) => {
  switch (type) {
    case 1: return '人员流失'
    case 2: return '考勤异常'
    case 3: return '薪酬异常'
    case 4: return '绩效异常'
    default: return '未知'
  }
}

// 处理新增仪表盘
const handleAddDashboard = () => {
  dashboardDialogTitle.value = '新增仪表盘'
  dashboardForm.value = {
    id: 0,
    name: '',
    description: '',
    chartCount: 0,
    status: 1
  }
  dashboardDialogVisible.value = true
}

// 处理编辑仪表盘
const handleEditDashboard = (row: any) => {
  dashboardDialogTitle.value = '编辑仪表盘'
  dashboardForm.value = { ...row }
  dashboardDialogVisible.value = true
}

// 处理删除仪表盘
const handleDeleteDashboard = (id: number) => {
  ElMessageBox.confirm('确定要删除这个仪表盘吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除仪表盘的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看仪表盘
const handleViewDashboard = (row: any) => {
  // 这里添加查看仪表盘的逻辑
  console.log('查看仪表盘:', row)
}

// 处理仪表盘提交
const handleDashboardSubmit = () => {
  // 这里添加提交仪表盘的逻辑
  dashboardDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增报表
const handleAddReport = () => {
  reportDialogTitle.value = '新增报表'
  reportForm.value = {
    id: 0,
    name: '',
    type: 1,
    description: '',
    period: 3
  }
  reportDialogVisible.value = true
}

// 处理编辑报表
const handleEditReport = (row: any) => {
  reportDialogTitle.value = '编辑报表'
  reportForm.value = { ...row }
  reportDialogVisible.value = true
}

// 处理删除报表
const handleDeleteReport = (id: number) => {
  ElMessageBox.confirm('确定要删除这个报表吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除报表的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看报表
const handleViewReport = (row: any) => {
  // 这里添加查看报表的逻辑
  console.log('查看报表:', row)
}

// 处理报表提交
const handleReportSubmit = () => {
  // 这里添加提交报表的逻辑
  reportDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增数据预警
const handleAddAlert = () => {
  alertDialogTitle.value = '新增数据预警'
  alertForm.value = {
    id: 0,
    name: '',
    type: 1,
    threshold: 0,
    description: '',
    status: 1
  }
  alertDialogVisible.value = true
}

// 处理编辑数据预警
const handleEditAlert = (row: any) => {
  alertDialogTitle.value = '编辑数据预警'
  alertForm.value = { ...row }
  alertDialogVisible.value = true
}

// 处理删除数据预警
const handleDeleteAlert = (id: number) => {
  ElMessageBox.confirm('确定要删除这个数据预警吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除数据预警的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看数据预警
const handleViewAlert = (row: any) => {
  // 这里添加查看数据预警的逻辑
  console.log('查看数据预警:', row)
}

// 处理数据预警提交
const handleAlertSubmit = () => {
  // 这里添加提交数据预警的逻辑
  alertDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理搜索
const handleDashboardSearch = () => console.log('仪表盘搜索条件:', dashboardSearch.value)
const handleReportSearch = () => console.log('报表搜索条件:', reportSearch.value)
const handleAlertSearch = () => console.log('数据预警搜索条件:', alertSearch.value)

// 处理重置
const handleDashboardReset = () => dashboardSearch.value = { keyword: '' }
const handleReportReset = () => reportSearch.value = { keyword: '', type: '0' }
const handleAlertReset = () => alertSearch.value = { keyword: '', status: '0' }

// 处理分页
const handleDashboardSizeChange = (size: number) => { dashboardPageSize.value = size }
const handleDashboardCurrentChange = (current: number) => { dashboardCurrentPage.value = current }
const handleReportSizeChange = (size: number) => { reportPageSize.value = size }
const handleReportCurrentChange = (current: number) => { reportCurrentPage.value = current }
const handleAlertSizeChange = (size: number) => { alertPageSize.value = size }
const handleAlertCurrentChange = (current: number) => { alertCurrentPage.value = current }

// 初始化图表
const initCharts = () => {
  // 部门分布图表
  const departmentChartDom = document.getElementById('departmentChart')
  if (departmentChartDom) {
    departmentChart = echarts.init(departmentChartDom)
    const departmentOption = {
      tooltip: {
        trigger: 'item'
      },
      series: [
        {
          name: '部门分布',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 150, name: '技术部' },
            { value: 100, name: '人力资源部' },
            { value: 80, name: '财务部' },
            { value: 120, name: '市场部' },
            { value: 50, name: '行政部' }
          ]
        }
      ]
    }
    departmentChart.setOption(departmentOption)
  }

  // 人员结构图表
  const structureChartDom = document.getElementById('structureChart')
  if (structureChartDom) {
    structureChart = echarts.init(structureChartDom)
    const structureOption = {
      tooltip: {
        trigger: 'item'
      },
      series: [
        {
          name: '人员结构',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 200, name: '研发人员' },
            { value: 100, name: '管理人员' },
            { value: 80, name: '销售人员' },
            { value: 70, name: '行政人员' },
            { value: 50, name: '其他' }
          ]
        }
      ]
    }
    structureChart.setOption(structureOption)
  }

  // 离职趋势图表
  const trendChartDom = document.getElementById('trendChart')
  if (trendChartDom) {
    trendChart = echarts.init(trendChartDom)
    const trendOption = {
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '离职人数',
          type: 'line',
          data: [5, 8, 3, 10, 6, 4]
        }
      ]
    }
    trendChart.setOption(trendOption)
  }

  // 员工满意度分析图表
  const satisfactionChartDom = document.getElementById('satisfactionChart')
  if (satisfactionChartDom) {
    satisfactionChart = echarts.init(satisfactionChartDom)
    const satisfactionOption = {
      title: {
        text: '员工满意度分析'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['技术部', '人力资源部', '财务部', '市场部']
      },
      xAxis: {
        type: 'category',
        data: ['工作环境', '薪资福利', '职业发展', '管理水平', '团队合作']
      },
      yAxis: {
        type: 'value',
        max: 5
      },
      series: [
        {
          name: '技术部',
          type: 'line',
          data: [4.2, 3.8, 4.5, 3.5, 4.0]
        },
        {
          name: '人力资源部',
          type: 'line',
          data: [4.5, 4.0, 3.8, 4.2, 4.3]
        },
        {
          name: '财务部',
          type: 'line',
          data: [4.0, 4.2, 3.5, 4.0, 3.8]
        },
        {
          name: '市场部',
          type: 'line',
          data: [3.8, 3.5, 4.2, 3.8, 4.0]
        }
      ]
    }
    satisfactionChart.setOption(satisfactionOption)
  }

  // 培训效果分析图表
  const trainingChartDom = document.getElementById('trainingChart')
  if (trainingChartDom) {
    trainingChart = echarts.init(trainingChartDom)
    const trainingOption = {
      title: {
        text: '培训效果分析'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['满意度', '完成率', '通过率']
      },
      xAxis: {
        type: 'category',
        data: ['技术培训', '管理培训', '软技能培训', '安全培训', '产品培训']
      },
      yAxis: {
        type: 'value',
        max: 100
      },
      series: [
        {
          name: '满意度',
          type: 'bar',
          data: [95, 88, 92, 85, 90]
        },
        {
          name: '完成率',
          type: 'bar',
          data: [98, 95, 96, 92, 94]
        },
        {
          name: '通过率',
          type: 'bar',
          data: [92, 85, 88, 90, 95]
        }
      ]
    }
    trainingChart.setOption(trainingOption)
  }
}

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
  initCharts()
})

// 组件卸载时执行
onUnmounted(() => {
  if (departmentChart) departmentChart.dispose()
  if (structureChart) structureChart.dispose()
  if (trendChart) trendChart.dispose()
  if (satisfactionChart) satisfactionChart.dispose()
  if (trainingChart) trainingChart.dispose()
})
</script>

<style scoped>
.bi-container {
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

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.dashboard-card {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.dashboard-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.dashboard-actions {
  display: flex;
  gap: 5px;
}

.dashboard-content {
  margin-top: 10px;
}

.dashboard-content p {
  margin: 0 0 10px 0;
  color: #666;
  font-size: 14px;
}

.dashboard-stats {
  display: flex;
  gap: 15px;
  font-size: 12px;
  color: #999;
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