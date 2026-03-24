<template>
  <div class="training-container">
    <div class="page-header">
      <h2>培训管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddCourse">
          <el-icon><Plus /></el-icon>
          新增课程
        </el-button>
        <el-button type="success" @click="handleAddPlan">
          <el-icon><Calendar /></el-icon>
          新增培训计划
        </el-button>
        <el-button type="warning" @click="handleAddInstructor">
          <el-icon><UserFilled /></el-icon>
          新增讲师
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="课程管理" name="course">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="courseSearch.keyword"
              placeholder="请输入课程名称或讲师"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="courseSearch.category" placeholder="请选择课程类别" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="技术培训" value="1" />
              <el-option label="管理培训" value="2" />
              <el-option label="软技能培训" value="3" />
            </el-select>
            <el-button type="primary" @click="handleCourseSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleCourseReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="courses" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="courseName" label="课程名称" width="180" />
            <el-table-column prop="instructorName" label="讲师" width="120" />
            <el-table-column prop="category" label="课程类别" width="120">
              <template #default="scope">
                {{ getCategoryText(scope.row.category) }}
              </template>
            </el-table-column>
            <el-table-column prop="duration" label="课时" width="100" />
            <el-table-column prop="level" label="难度" width="100">
              <template #default="scope">
                {{ getLevelText(scope.row.level) }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewCourse(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditCourse(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteCourse(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="courseCurrentPage"
              v-model:page-size="coursePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="courseTotal"
              @size-change="handleCourseSizeChange"
              @current-change="handleCourseCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="培训计划" name="plan">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="planSearch.keyword"
              placeholder="请输入计划名称或部门"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-date-picker
              v-model="planSearch.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width: 300px; margin-left: 10px"
            />
            <el-button type="primary" @click="handlePlanSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handlePlanReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="trainingPlans" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="planName" label="计划名称" width="180" />
            <el-table-column prop="departmentName" label="部门" width="120" />
            <el-table-column prop="startDate" label="开始日期" width="150" />
            <el-table-column prop="endDate" label="结束日期" width="150" />
            <el-table-column prop="totalHours" label="总课时" width="100" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ getStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewPlan(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditPlan(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeletePlan(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="planCurrentPage"
              v-model:page-size="planPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="planTotal"
              @size-change="handlePlanSizeChange"
              @current-change="handlePlanCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="讲师管理" name="instructor">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="instructorSearch.keyword"
              placeholder="请输入讲师姓名或专长"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleInstructorSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleInstructorReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="instructors" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="姓名" width="100" />
            <el-table-column prop="specialty" label="专长领域" width="150" />
            <el-table-column prop="phone" label="电话" width="150" />
            <el-table-column prop="email" label="邮箱" width="200" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewInstructor(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditInstructor(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteInstructor(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="instructorCurrentPage"
              v-model:page-size="instructorPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="instructorTotal"
              @size-change="handleInstructorSizeChange"
              @current-change="handleInstructorCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="培训分析" name="analysis">
        <div class="liquid-glass-card">
          <div class="stats-grid">
            <div class="stats-card">
              <h3>培训概览</h3>
              <div class="stats-item">
                <span class="stats-label">总课程数</span>
                <span class="stats-value">50</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">总培训计划</span>
                <span class="stats-value">20</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">总培训时长</span>
                <span class="stats-value">500小时</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">参训人数</span>
                <span class="stats-value">200人</span>
              </div>
            </div>
            <div class="stats-card">
              <h3>培训效果</h3>
              <div class="stats-item">
                <span class="stats-label">平均满意度</span>
                <span class="stats-value">4.5/5</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">培训完成率</span>
                <span class="stats-value">95%</span>
              </div>
              <div class="stats-item">
                <span class="stats-label">考试通过率</span>
                <span class="stats-value">90%</span>
              </div>
            </div>
          </div>
          <div class="chart-container">
            <h3>培训类别分布</h3>
            <div id="categoryChart" style="width: 100%; height: 400px;"></div>
          </div>
          <div class="chart-container">
            <h3>培训趋势</h3>
            <div id="trendChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 课程对话框 -->
    <el-dialog
      v-model="courseDialogVisible"
      :title="courseDialogTitle"
      width="600px"
    >
      <el-form :model="courseForm" label-width="120px">
        <el-form-item label="课程名称">
          <el-input v-model="courseForm.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="讲师">
          <el-select v-model="courseForm.instructorId" placeholder="请选择讲师">
            <el-option
              v-for="instructor in instructors"
              :key="instructor.id"
              :label="instructor.name"
              :value="instructor.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程类别">
          <el-select v-model="courseForm.category" placeholder="请选择课程类别">
            <el-option label="技术培训" value="1" />
            <el-option label="管理培训" value="2" />
            <el-option label="软技能培训" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="课时">
          <el-input v-model="courseForm.duration" placeholder="请输入课时" type="number" />
        </el-form-item>
        <el-form-item label="难度">
          <el-select v-model="courseForm.level" placeholder="请选择难度">
            <el-option label="初级" value="1" />
            <el-option label="中级" value="2" />
            <el-option label="高级" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input
            v-model="courseForm.description"
            type="textarea"
            placeholder="请输入课程描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="课程大纲">
          <el-input
            v-model="courseForm.outline"
            type="textarea"
            placeholder="请输入课程大纲"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="courseDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleCourseSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 培训计划对话框 -->
    <el-dialog
      v-model="planDialogVisible"
      :title="planDialogTitle"
      width="600px"
    >
      <el-form :model="planForm" label-width="120px">
        <el-form-item label="计划名称">
          <el-input v-model="planForm.planName" placeholder="请输入计划名称" />
        </el-form-item>
        <el-form-item label="所属部门">
          <el-select v-model="planForm.departmentId" placeholder="请选择部门">
            <el-option
              v-for="dept in departments"
              :key="dept.id"
              :label="dept.name"
              :value="dept.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker
            v-model="planForm.startDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker
            v-model="planForm.endDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="总课时">
          <el-input v-model="planForm.totalHours" placeholder="请输入总课时" type="number" />
        </el-form-item>
        <el-form-item label="培训课程">
          <el-select v-model="planForm.courseIds" multiple placeholder="请选择培训课程">
            <el-option
              v-for="course in courses"
              :key="course.id"
              :label="course.courseName"
              :value="course.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="计划描述">
          <el-input
            v-model="planForm.description"
            type="textarea"
            placeholder="请输入计划描述"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="planDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handlePlanSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 讲师对话框 -->
    <el-dialog
      v-model="instructorDialogVisible"
      :title="instructorDialogTitle"
      width="600px"
    >
      <el-form :model="instructorForm" label-width="120px">
        <el-form-item label="姓名">
          <el-input v-model="instructorForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="专长领域">
          <el-input v-model="instructorForm.specialty" placeholder="请输入专长领域" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="instructorForm.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="instructorForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input
            v-model="instructorForm.bio"
            type="textarea"
            placeholder="请输入讲师简介"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="instructorDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleInstructorSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { Plus, Search, Calendar, UserFilled } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import * as echarts from 'echarts'

// 激活的标签页
const activeTab = ref('course')

// 部门列表
const departments = ref([
  { id: 1, name: '技术部' },
  { id: 2, name: '人力资源部' },
  { id: 3, name: '财务部' },
  { id: 4, name: '市场部' }
])

// 课程数据
const courses = ref([
  {
    id: 1,
    courseName: '前端开发进阶',
    instructorId: 1,
    instructorName: '张老师',
    category: 1,
    duration: 16,
    level: 2,
    status: 1
  },
  {
    id: 2,
    courseName: '后端开发实战',
    instructorId: 2,
    instructorName: '李老师',
    category: 1,
    duration: 24,
    level: 3,
    status: 1
  },
  {
    id: 3,
    courseName: '管理技能提升',
    instructorId: 3,
    instructorName: '王老师',
    category: 2,
    duration: 12,
    level: 2,
    status: 1
  }
])

// 培训计划数据
const trainingPlans = ref([
  {
    id: 1,
    planName: '技术部Q1培训计划',
    departmentId: 1,
    departmentName: '技术部',
    startDate: '2024-01-01',
    endDate: '2024-03-31',
    totalHours: 40,
    status: 2
  },
  {
    id: 2,
    planName: '人力资源部培训计划',
    departmentId: 2,
    departmentName: '人力资源部',
    startDate: '2024-02-01',
    endDate: '2024-02-28',
    totalHours: 20,
    status: 1
  }
])

// 讲师数据
const instructors = ref([
  {
    id: 1,
    name: '张老师',
    specialty: '前端开发',
    phone: '13800138001',
    email: 'zhang@example.com',
    status: 1
  },
  {
    id: 2,
    name: '李老师',
    specialty: '后端开发',
    phone: '13800138002',
    email: 'li@example.com',
    status: 1
  },
  {
    id: 3,
    name: '王老师',
    specialty: '管理培训',
    phone: '13800138003',
    email: 'wang@example.com',
    status: 1
  }
])

// 搜索条件
const courseSearch = ref({ keyword: '', category: '0' })
const planSearch = ref({ keyword: '', dateRange: [] })
const instructorSearch = ref({ keyword: '' })

// 分页参数
const courseCurrentPage = ref(1)
const coursePageSize = ref(10)
const courseTotal = ref(3)

const planCurrentPage = ref(1)
const planPageSize = ref(10)
const planTotal = ref(2)

const instructorCurrentPage = ref(1)
const instructorPageSize = ref(10)
const instructorTotal = ref(3)

// 对话框状态
const courseDialogVisible = ref(false)
const courseDialogTitle = ref('新增课程')

const planDialogVisible = ref(false)
const planDialogTitle = ref('新增培训计划')

const instructorDialogVisible = ref(false)
const instructorDialogTitle = ref('新增讲师')

// 表单数据
const courseForm = ref({
  id: 0,
  courseName: '',
  instructorId: 0,
  category: 1,
  duration: 0,
  level: 1,
  description: '',
  outline: ''
})

const planForm = ref({
  id: 0,
  planName: '',
  departmentId: 0,
  startDate: '',
  endDate: '',
  totalHours: 0,
  courseIds: [],
  description: ''
})

const instructorForm = ref({
  id: 0,
  name: '',
  specialty: '',
  phone: '',
  email: '',
  bio: ''
})

// 图表实例
let categoryChart: echarts.ECharts | null = null
let trendChart: echarts.ECharts | null = null

// 获取课程类别文本
const getCategoryText = (category: number) => {
  switch (category) {
    case 1: return '技术培训'
    case 2: return '管理培训'
    case 3: return '软技能培训'
    default: return '未知'
  }
}

// 获取难度文本
const getLevelText = (level: number) => {
  switch (level) {
    case 1: return '初级'
    case 2: return '中级'
    case 3: return '高级'
    default: return '未知'
  }
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
    case 1: return '计划中'
    case 2: return '进行中'
    case 3: return '已完成'
    default: return '未知'
  }
}

// 处理新增课程
const handleAddCourse = () => {
  courseDialogTitle.value = '新增课程'
  courseForm.value = {
    id: 0,
    courseName: '',
    instructorId: 0,
    category: 1,
    duration: 0,
    level: 1,
    description: '',
    outline: ''
  }
  courseDialogVisible.value = true
}

// 处理编辑课程
const handleEditCourse = (row: any) => {
  courseDialogTitle.value = '编辑课程'
  courseForm.value = { ...row }
  courseDialogVisible.value = true
}

// 处理删除课程
const handleDeleteCourse = (id: number) => {
  ElMessageBox.confirm('确定要删除这个课程吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除课程的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看课程
const handleViewCourse = (row: any) => {
  // 这里添加查看课程的逻辑
  console.log('查看课程:', row)
}

// 处理课程提交
const handleCourseSubmit = () => {
  // 这里添加提交课程的逻辑
  courseDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增培训计划
const handleAddPlan = () => {
  planDialogTitle.value = '新增培训计划'
  planForm.value = {
    id: 0,
    planName: '',
    departmentId: 0,
    startDate: '',
    endDate: '',
    totalHours: 0,
    courseIds: [],
    description: ''
  }
  planDialogVisible.value = true
}

// 处理编辑培训计划
const handleEditPlan = (row: any) => {
  planDialogTitle.value = '编辑培训计划'
  planForm.value = { ...row }
  planDialogVisible.value = true
}

// 处理删除培训计划
const handleDeletePlan = (id: number) => {
  ElMessageBox.confirm('确定要删除这个培训计划吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除培训计划的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看培训计划
const handleViewPlan = (row: any) => {
  // 这里添加查看培训计划的逻辑
  console.log('查看培训计划:', row)
}

// 处理培训计划提交
const handlePlanSubmit = () => {
  // 这里添加提交培训计划的逻辑
  planDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增讲师
const handleAddInstructor = () => {
  instructorDialogTitle.value = '新增讲师'
  instructorForm.value = {
    id: 0,
    name: '',
    specialty: '',
    phone: '',
    email: '',
    bio: ''
  }
  instructorDialogVisible.value = true
}

// 处理编辑讲师
const handleEditInstructor = (row: any) => {
  instructorDialogTitle.value = '编辑讲师'
  instructorForm.value = { ...row }
  instructorDialogVisible.value = true
}

// 处理删除讲师
const handleDeleteInstructor = (id: number) => {
  ElMessageBox.confirm('确定要删除这个讲师吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除讲师的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看讲师
const handleViewInstructor = (row: any) => {
  // 这里添加查看讲师的逻辑
  console.log('查看讲师:', row)
}

// 处理讲师提交
const handleInstructorSubmit = () => {
  // 这里添加提交讲师的逻辑
  instructorDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理搜索
const handleCourseSearch = () => console.log('课程搜索条件:', courseSearch.value)
const handlePlanSearch = () => console.log('培训计划搜索条件:', planSearch.value)
const handleInstructorSearch = () => console.log('讲师搜索条件:', instructorSearch.value)

// 处理重置
const handleCourseReset = () => courseSearch.value = { keyword: '', category: '0' }
const handlePlanReset = () => planSearch.value = { keyword: '', dateRange: [] }
const handleInstructorReset = () => instructorSearch.value = { keyword: '' }

// 处理分页
const handleCourseSizeChange = (size: number) => { coursePageSize.value = size }
const handleCourseCurrentChange = (current: number) => { courseCurrentPage.value = current }
const handlePlanSizeChange = (size: number) => { planPageSize.value = size }
const handlePlanCurrentChange = (current: number) => { planCurrentPage.value = current }
const handleInstructorSizeChange = (size: number) => { instructorPageSize.value = size }
const handleInstructorCurrentChange = (current: number) => { instructorCurrentPage.value = current }

// 初始化图表
const initCharts = () => {
  // 培训类别分布图表
  const categoryChartDom = document.getElementById('categoryChart')
  if (categoryChartDom) {
    categoryChart = echarts.init(categoryChartDom)
    const categoryOption = {
      title: {
        text: '培训类别分布'
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
          name: '培训类别',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 40, name: '技术培训' },
            { value: 30, name: '管理培训' },
            { value: 30, name: '软技能培训' }
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
    categoryChart.setOption(categoryOption)
  }

  // 培训趋势图表
  const trendChartDom = document.getElementById('trendChart')
  if (trendChartDom) {
    trendChart = echarts.init(trendChartDom)
    const trendOption = {
      title: {
        text: '培训趋势变化'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['技术部', '人力资源部', '财务部', '市场部']
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
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '技术部',
          type: 'line',
          data: [10, 15, 20, 18, 25, 30]
        },
        {
          name: '人力资源部',
          type: 'line',
          data: [5, 8, 10, 12, 15, 18]
        },
        {
          name: '财务部',
          type: 'line',
          data: [3, 5, 7, 8, 10, 12]
        },
        {
          name: '市场部',
          type: 'line',
          data: [8, 12, 15, 18, 20, 25]
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
  if (categoryChart) {
    categoryChart.dispose()
  }
  if (trendChart) {
    trendChart.dispose()
  }
})
</script>

<style scoped>
.training-container {
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