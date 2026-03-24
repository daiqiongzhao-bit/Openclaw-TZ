<template>
  <div class="attendance-container">
    <div class="page-header">
      <h2>考勤与假期管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddAttendance">
          <el-icon><Plus /></el-icon>
          新增考勤记录
        </el-button>
        <el-button type="success" @click="handleAddHoliday">
          <el-icon><Calendar /></el-icon>
          申请假期
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="考勤记录" name="attendance">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="attendanceSearch.keyword"
              placeholder="请输入员工姓名或工号"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-date-picker
              v-model="attendanceSearch.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width: 300px; margin-left: 10px"
            />
            <el-select v-model="attendanceSearch.status" placeholder="请选择考勤状态" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="正常" value="1" />
              <el-option label="迟到" value="2" />
              <el-option label="早退" value="3" />
              <el-option label="缺勤" value="4" />
            </el-select>
            <el-button type="primary" @click="handleAttendanceSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleAttendanceReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="attendanceRecords" style="width: 100%">
            <el-table-column prop="id" label="记录ID" width="100" />
            <el-table-column prop="employeeId" label="工号" width="100" />
            <el-table-column prop="employeeName" label="姓名" width="120" />
            <el-table-column prop="attendanceDate" label="考勤日期" width="150" />
            <el-table-column prop="checkInTime" label="上班时间" width="150" />
            <el-table-column prop="checkOutTime" label="下班时间" width="150" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getAttendanceStatusType(scope.row.status)">
                  {{ getAttendanceStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleEditAttendance(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteAttendance(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="attendanceCurrentPage"
              v-model:page-size="attendancePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="attendanceTotal"
              @size-change="handleAttendanceSizeChange"
              @current-change="handleAttendanceCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="假期管理" name="holiday">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="holidaySearch.keyword"
              placeholder="请输入员工姓名或工号"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="holidaySearch.type" placeholder="请选择假期类型" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="年假" value="1" />
              <el-option label="病假" value="2" />
              <el-option label="事假" value="3" />
              <el-option label="婚假" value="4" />
              <el-option label="产假" value="5" />
            </el-select>
            <el-select v-model="holidaySearch.status" placeholder="请选择状态" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="待审批" value="1" />
              <el-option label="已批准" value="2" />
              <el-option label="已拒绝" value="3" />
            </el-select>
            <el-button type="primary" @click="handleHolidaySearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleHolidayReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="holidayApplications" style="width: 100%">
            <el-table-column prop="id" label="申请ID" width="100" />
            <el-table-column prop="employeeId" label="工号" width="100" />
            <el-table-column prop="employeeName" label="姓名" width="120" />
            <el-table-column prop="holidayType" label="假期类型" width="120">
              <template #default="scope">
                {{ getHolidayTypeText(scope.row.holidayType) }}
              </template>
            </el-table-column>
            <el-table-column prop="startDate" label="开始日期" width="150" />
            <el-table-column prop="endDate" label="结束日期" width="150" />
            <el-table-column prop="days" label="天数" width="80" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getHolidayStatusType(scope.row.status)">
                  {{ getHolidayStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="reason" label="申请原因" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewHoliday(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleApproveHoliday(scope.row)">
                  审批
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="holidayCurrentPage"
              v-model:page-size="holidayPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="holidayTotal"
              @size-change="handleHolidaySizeChange"
              @current-change="handleHolidayCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="考勤统计" name="statistics">
        <div class="liquid-glass-card">
          <div class="stats-grid">
            <div class="stats-card">
              <h3>本月考勤概览</h3>
              <div class="stats-item">
                <span class="stats-label">总出勤天数</span>
                <span class="stats-value">20</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">迟到次数</span>
                <span class="stats-value">2</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">早退次数</span>
                <span class="stats-value">1</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">缺勤次数</span>
                <span class="stats-value">0</span>
              </div>
            </div>
            <div class="stats-card">
              <h3>假期余额</h3>
              <div class="stats-item">
                <span class="stats-label">年假</span>
                <span class="stats-value">5天</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">病假</span>
                <span class="stats-value">3天</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">事假</span>
                <span class="stats-value">2天</span>
              </div>
            </div>
          </div>
          <div class="chart-container">
            <h3>考勤趋势</h3>
            <div id="attendanceChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 考勤记录对话框 -->
    <el-dialog
      v-model="attendanceDialogVisible"
      :title="attendanceDialogTitle"
      width="600px"
    >
      <el-form :model="attendanceForm" label-width="120px">
        <el-form-item label="员工">
          <el-select v-model="attendanceForm.employeeId" placeholder="请选择员工">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="考勤日期">
          <el-date-picker
            v-model="attendanceForm.attendanceDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="上班时间">
          <el-time-picker
            v-model="attendanceForm.checkInTime"
            format="HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="下班时间">
          <el-time-picker
            v-model="attendanceForm.checkOutTime"
            format="HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="attendanceForm.status" placeholder="请选择状态">
            <el-option label="正常" value="1" />
            <el-option label="迟到" value="2" />
            <el-option label="早退" value="3" />
            <el-option label="缺勤" value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="attendanceForm.remark"
            type="textarea"
            placeholder="请输入备注"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="attendanceDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAttendanceSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 假期申请对话框 -->
    <el-dialog
      v-model="holidayDialogVisible"
      :title="holidayDialogTitle"
      width="600px"
    >
      <el-form :model="holidayForm" label-width="120px">
        <el-form-item label="员工">
          <el-select v-model="holidayForm.employeeId" placeholder="请选择员工">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="假期类型">
          <el-select v-model="holidayForm.holidayType" placeholder="请选择假期类型">
            <el-option label="年假" value="1" />
            <el-option label="病假" value="2" />
            <el-option label="事假" value="3" />
            <el-option label="婚假" value="4" />
            <el-option label="产假" value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker
            v-model="holidayForm.startDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker
            v-model="holidayForm.endDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="天数">
          <el-input v-model="holidayForm.days" placeholder="请输入天数" type="number" />
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input
            v-model="holidayForm.reason"
            type="textarea"
            placeholder="请输入申请原因"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="holidayDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleHolidaySubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { Plus, Search, Calendar } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import * as echarts from 'echarts'

// 激活的标签页
const activeTab = ref('attendance')

// 员工列表
const employees = ref([
  { id: 1, name: '张三' },
  { id: 2, name: '李四' },
  { id: 3, name: '王五' }
])

// 考勤记录数据
const attendanceRecords = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    attendanceDate: '2024-01-01',
    checkInTime: '09:00:00',
    checkOutTime: '18:00:00',
    status: 1,
    remark: ''
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    attendanceDate: '2024-01-01',
    checkInTime: '09:15:00',
    checkOutTime: '18:00:00',
    status: 2,
    remark: '迟到15分钟'
  },
  {
    id: 3,
    employeeId: 3,
    employeeName: '王五',
    attendanceDate: '2024-01-01',
    checkInTime: '09:00:00',
    checkOutTime: '17:30:00',
    status: 3,
    remark: '早退30分钟'
  }
])

// 假期申请数据
const holidayApplications = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    holidayType: 1,
    startDate: '2024-01-10',
    endDate: '2024-01-12',
    days: 3,
    status: 1,
    reason: '个人原因'
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    holidayType: 2,
    startDate: '2024-01-05',
    endDate: '2024-01-06',
    days: 2,
    status: 2,
    reason: '生病'
  }
])

// 考勤搜索条件
const attendanceSearch = ref({
  keyword: '',
  dateRange: [],
  status: '0'
})

// 假期搜索条件
const holidaySearch = ref({
  keyword: '',
  type: '0',
  status: '0'
})

// 考勤分页参数
const attendanceCurrentPage = ref(1)
const attendancePageSize = ref(10)
const attendanceTotal = ref(3)

// 假期分页参数
const holidayCurrentPage = ref(1)
const holidayPageSize = ref(10)
const holidayTotal = ref(2)

// 考勤对话框状态
const attendanceDialogVisible = ref(false)
const attendanceDialogTitle = ref('新增考勤记录')

// 假期对话框状态
const holidayDialogVisible = ref(false)
const holidayDialogTitle = ref('申请假期')

// 考勤表单
const attendanceForm = ref({
  id: 0,
  employeeId: 0,
  attendanceDate: '',
  checkInTime: '',
  checkOutTime: '',
  status: 1,
  remark: ''
})

// 假期表单
const holidayForm = ref({
  id: 0,
  employeeId: 0,
  holidayType: 1,
  startDate: '',
  endDate: '',
  days: 1,
  reason: ''
})

// 图表实例
let attendanceChart: echarts.ECharts | null = null

// 获取考勤状态类型
const getAttendanceStatusType = (status: number) => {
  switch (status) {
    case 1: return 'success'
    case 2: return 'warning'
    case 3: return 'warning'
    case 4: return 'danger'
    default: return 'info'
  }
}

// 获取考勤状态文本
const getAttendanceStatusText = (status: number) => {
  switch (status) {
    case 1: return '正常'
    case 2: return '迟到'
    case 3: return '早退'
    case 4: return '缺勤'
    default: return '未知'
  }
}

// 获取假期类型文本
const getHolidayTypeText = (type: number) => {
  switch (type) {
    case 1: return '年假'
    case 2: return '病假'
    case 3: return '事假'
    case 4: return '婚假'
    case 5: return '产假'
    default: return '未知'
  }
}

// 获取假期状态类型
const getHolidayStatusType = (status: number) => {
  switch (status) {
    case 1: return 'info'
    case 2: return 'success'
    case 3: return 'danger'
    default: return 'info'
  }
}

// 获取假期状态文本
const getHolidayStatusText = (status: number) => {
  switch (status) {
    case 1: return '待审批'
    case 2: return '已批准'
    case 3: return '已拒绝'
    default: return '未知'
  }
}

// 处理新增考勤记录
const handleAddAttendance = () => {
  attendanceDialogTitle.value = '新增考勤记录'
  attendanceForm.value = {
    id: 0,
    employeeId: 0,
    attendanceDate: '',
    checkInTime: '',
    checkOutTime: '',
    status: 1,
    remark: ''
  }
  attendanceDialogVisible.value = true
}

// 处理编辑考勤记录
const handleEditAttendance = (row: any) => {
  attendanceDialogTitle.value = '编辑考勤记录'
  attendanceForm.value = { ...row }
  attendanceDialogVisible.value = true
}

// 处理删除考勤记录
const handleDeleteAttendance = (id: number) => {
  ElMessageBox.confirm('确定要删除这个考勤记录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除考勤记录的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理考勤记录提交
const handleAttendanceSubmit = () => {
  // 这里添加提交考勤记录的逻辑
  attendanceDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增假期申请
const handleAddHoliday = () => {
  holidayDialogTitle.value = '申请假期'
  holidayForm.value = {
    id: 0,
    employeeId: 0,
    holidayType: 1,
    startDate: '',
    endDate: '',
    days: 1,
    reason: ''
  }
  holidayDialogVisible.value = true
}

// 处理查看假期申请
const handleViewHoliday = (row: any) => {
  // 这里添加查看假期申请的逻辑
  console.log('查看假期申请:', row)
}

// 处理审批假期申请
const handleApproveHoliday = (row: any) => {
  // 这里添加审批假期申请的逻辑
  console.log('审批假期申请:', row)
}

// 处理假期申请提交
const handleHolidaySubmit = () => {
  // 这里添加提交假期申请的逻辑
  holidayDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理考勤搜索
const handleAttendanceSearch = () => {
  // 这里添加考勤搜索逻辑
  console.log('考勤搜索条件:', attendanceSearch.value)
}

// 处理考勤重置
const handleAttendanceReset = () => {
  attendanceSearch.value = {
    keyword: '',
    dateRange: [],
    status: '0'
  }
}

// 处理假期搜索
const handleHolidaySearch = () => {
  // 这里添加假期搜索逻辑
  console.log('假期搜索条件:', holidaySearch.value)
}

// 处理假期重置
const handleHolidayReset = () => {
  holidaySearch.value = {
    keyword: '',
    type: '0',
    status: '0'
  }
}

// 处理考勤分页大小变化
const handleAttendanceSizeChange = (size: number) => {
  attendancePageSize.value = size
  // 这里添加分页逻辑
}

// 处理考勤分页当前页变化
const handleAttendanceCurrentChange = (current: number) => {
  attendanceCurrentPage.value = current
  // 这里添加分页逻辑
}

// 处理假期分页大小变化
const handleHolidaySizeChange = (size: number) => {
  holidayPageSize.value = size
  // 这里添加分页逻辑
}

// 处理假期分页当前页变化
const handleHolidayCurrentChange = (current: number) => {
  holidayCurrentPage.value = current
  // 这里添加分页逻辑
}

// 初始化图表
const initChart = () => {
  const chartDom = document.getElementById('attendanceChart')
  if (chartDom) {
    attendanceChart = echarts.init(chartDom)
    const option = {
      title: {
        text: '考勤趋势分析'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['正常', '迟到', '早退', '缺勤']
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
        data: ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '8日', '9日', '10日']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '正常',
          type: 'line',
          data: [3, 3, 3, 3, 3, 0, 0, 3, 3, 3]
        },
        {
          name: '迟到',
          type: 'line',
          data: [0, 1, 0, 0, 1, 0, 0, 0, 0, 0]
        },
        {
          name: '早退',
          type: 'line',
          data: [0, 0, 1, 0, 0, 0, 0, 0, 0, 0]
        },
        {
          name: '缺勤',
          type: 'line',
          data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        }
      ]
    }
    attendanceChart.setOption(option)
  }
}

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
  initChart()
})

// 组件卸载时执行
onUnmounted(() => {
  if (attendanceChart) {
    attendanceChart.dispose()
  }
})
</script>

<style scoped>
.attendance-container {
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