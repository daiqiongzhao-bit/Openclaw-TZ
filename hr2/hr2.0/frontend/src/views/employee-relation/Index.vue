<template>
  <div class="employee-relation-container">
    <div class="page-header">
      <h2>员工关系管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddReward">
          <el-icon><Star /></el-icon>
          新增奖惩
        </el-button>
        <el-button type="success" @click="handleAddCare">
          <el-icon><Heart /></el-icon>
          新增关怀
        </el-button>
        <el-button type="warning" @click="handleAddCertificate">
          <el-icon><Document /></el-icon>
          新增证书申请
        </el-button>
        <el-button type="info" @click="handleAddNotice">
          <el-icon><Bell /></el-icon>
          新增人事公告
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="奖惩管理" name="reward">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="rewardSearch.keyword"
              placeholder="请输入员工姓名或奖惩原因"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="rewardSearch.type" placeholder="请选择类型" style="width: 120px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="奖励" value="1" />
              <el-option label="惩罚" value="2" />
            </el-select>
            <el-date-picker
              v-model="rewardSearch.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width: 300px; margin-left: 10px"
            />
            <el-button type="primary" @click="handleRewardSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleRewardReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="rewards" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeName" label="员工姓名" width="120" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.type === 1 ? 'success' : 'danger'">
                  {{ scope.row.type === 1 ? '奖励' : '惩罚' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="reason" label="原因" />
            <el-table-column prop="amount" label="金额" width="100" />
            <el-table-column prop="createDate" label="日期" width="150" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewReward(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditReward(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteReward(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="rewardCurrentPage"
              v-model:page-size="rewardPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="rewardTotal"
              @size-change="handleRewardSizeChange"
              @current-change="handleRewardCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="员工关怀" name="care">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="careSearch.keyword"
              placeholder="请输入员工姓名或关怀内容"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="careSearch.type" placeholder="请选择关怀类型" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="生日关怀" value="1" />
              <el-option label="节日关怀" value="2" />
              <el-option label="其他关怀" value="3" />
            </el-select>
            <el-button type="primary" @click="handleCareSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleCareReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="cares" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeName" label="员工姓名" width="120" />
            <el-table-column prop="type" label="关怀类型" width="120">
              <template #default="scope">
                {{ getCareTypeText(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column prop="content" label="关怀内容" />
            <el-table-column prop="careDate" label="关怀日期" width="150" />
            <el-table-column prop="handler" label="处理人" width="120" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewCare(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditCare(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteCare(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="careCurrentPage"
              v-model:page-size="carePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="careTotal"
              @size-change="handleCareSizeChange"
              @current-change="handleCareCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="证书申请" name="certificate">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="certificateSearch.keyword"
              placeholder="请输入员工姓名或证书名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="certificateSearch.status" placeholder="请选择状态" style="width: 120px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="待审批" value="1" />
              <el-option label="已通过" value="2" />
              <el-option label="已拒绝" value="3" />
            </el-select>
            <el-button type="primary" @click="handleCertificateSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleCertificateReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="certificates" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeName" label="员工姓名" width="120" />
            <el-table-column prop="certificateName" label="证书名称" width="180" />
            <el-table-column prop="applyDate" label="申请日期" width="150" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getCertificateStatusType(scope.row.status)">
                  {{ getCertificateStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewCertificate(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditCertificate(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteCertificate(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="certificateCurrentPage"
              v-model:page-size="certificatePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="certificateTotal"
              @size-change="handleCertificateSizeChange"
              @current-change="handleCertificateCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="人事公告" name="notice">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="noticeSearch.keyword"
              placeholder="请输入公告标题或内容"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-date-picker
              v-model="noticeSearch.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width: 300px; margin-left: 10px"
            />
            <el-button type="primary" @click="handleNoticeSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleNoticeReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="notices" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="title" label="公告标题" />
            <el-table-column prop="publishDate" label="发布日期" width="150" />
            <el-table-column prop="publisher" label="发布人" width="120" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '已发布' : '草稿' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewNotice(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditNotice(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteNotice(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="noticeCurrentPage"
              v-model:page-size="noticePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="noticeTotal"
              @size-change="handleNoticeSizeChange"
              @current-change="handleNoticeCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 奖惩对话框 -->
    <el-dialog
      v-model="rewardDialogVisible"
      :title="rewardDialogTitle"
      width="600px"
    >
      <el-form :model="rewardForm" label-width="120px">
        <el-form-item label="员工姓名">
          <el-select v-model="rewardForm.employeeId" placeholder="请选择员工">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="rewardForm.type">
            <el-radio label="1">奖励</el-radio>
            <el-radio label="2">惩罚</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="原因">
          <el-input
            v-model="rewardForm.reason"
            type="textarea"
            placeholder="请输入奖惩原因"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="金额">
          <el-input v-model="rewardForm.amount" placeholder="请输入金额" type="number" />
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker
            v-model="rewardForm.createDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="rewardDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleRewardSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 员工关怀对话框 -->
    <el-dialog
      v-model="careDialogVisible"
      :title="careDialogTitle"
      width="600px"
    >
      <el-form :model="careForm" label-width="120px">
        <el-form-item label="员工姓名">
          <el-select v-model="careForm.employeeId" placeholder="请选择员工">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关怀类型">
          <el-select v-model="careForm.type" placeholder="请选择关怀类型">
            <el-option label="生日关怀" value="1" />
            <el-option label="节日关怀" value="2" />
            <el-option label="其他关怀" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="关怀内容">
          <el-input
            v-model="careForm.content"
            type="textarea"
            placeholder="请输入关怀内容"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="关怀日期">
          <el-date-picker
            v-model="careForm.careDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="处理人">
          <el-input v-model="careForm.handler" placeholder="请输入处理人" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="careDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleCareSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 证书申请对话框 -->
    <el-dialog
      v-model="certificateDialogVisible"
      :title="certificateDialogTitle"
      width="600px"
    >
      <el-form :model="certificateForm" label-width="120px">
        <el-form-item label="员工姓名">
          <el-select v-model="certificateForm.employeeId" placeholder="请选择员工">
            <el-option
              v-for="employee in employees"
              :key="employee.id"
              :label="employee.name"
              :value="employee.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="证书名称">
          <el-input v-model="certificateForm.certificateName" placeholder="请输入证书名称" />
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input
            v-model="certificateForm.applyReason"
            type="textarea"
            placeholder="请输入申请原因"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="申请日期">
          <el-date-picker
            v-model="certificateForm.applyDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="certificateForm.status" placeholder="请选择状态">
            <el-option label="待审批" value="1" />
            <el-option label="已通过" value="2" />
            <el-option label="已拒绝" value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="certificateDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleCertificateSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 人事公告对话框 -->
    <el-dialog
      v-model="noticeDialogVisible"
      :title="noticeDialogTitle"
      width="600px"
    >
      <el-form :model="noticeForm" label-width="120px">
        <el-form-item label="公告标题">
          <el-input v-model="noticeForm.title" placeholder="请输入公告标题" />
        </el-form-item>
        <el-form-item label="公告内容">
          <el-input
            v-model="noticeForm.content"
            type="textarea"
            placeholder="请输入公告内容"
            rows="5"
          />
        </el-form-item>
        <el-form-item label="发布日期">
          <el-date-picker
            v-model="noticeForm.publishDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="发布人">
          <el-input v-model="noticeForm.publisher" placeholder="请输入发布人" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="noticeForm.status">
            <el-radio label="1">已发布</el-radio>
            <el-radio label="0">草稿</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="noticeDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleNoticeSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Star, Heart, Document, Bell, Search } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 激活的标签页
const activeTab = ref('reward')

// 员工列表
const employees = ref([
  { id: 1, name: '张三' },
  { id: 2, name: '李四' },
  { id: 3, name: '王五' },
  { id: 4, name: '赵六' }
])

// 奖惩数据
const rewards = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    type: 1,
    reason: '工作表现优秀',
    amount: 1000,
    createDate: '2024-01-01'
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    type: 2,
    reason: '迟到多次',
    amount: 200,
    createDate: '2024-01-02'
  }
])

// 员工关怀数据
const cares = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    type: 1,
    content: '生日关怀礼物',
    careDate: '2024-01-15',
    handler: '人事部门'
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    type: 2,
    content: '春节关怀礼包',
    careDate: '2024-02-01',
    handler: '人事部门'
  }
])

// 证书申请数据
const certificates = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    certificateName: '专业技能证书',
    applyDate: '2024-01-10',
    status: 1
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    certificateName: '安全培训证书',
    applyDate: '2024-01-12',
    status: 2
  }
])

// 人事公告数据
const notices = ref([
  {
    id: 1,
    title: '公司年会通知',
    content: '2024年公司年会将于1月30日举行',
    publishDate: '2024-01-05',
    publisher: '行政部',
    status: 1
  },
  {
    id: 2,
    title: '春节放假安排',
    content: '春节放假时间为2月10日至2月17日',
    publishDate: '2024-01-15',
    publisher: '行政部',
    status: 1
  }
])

// 搜索条件
const rewardSearch = ref({ keyword: '', type: '0', dateRange: [] })
const careSearch = ref({ keyword: '', type: '0' })
const certificateSearch = ref({ keyword: '', status: '0' })
const noticeSearch = ref({ keyword: '', dateRange: [] })

// 分页参数
const rewardCurrentPage = ref(1)
const rewardPageSize = ref(10)
const rewardTotal = ref(2)

const careCurrentPage = ref(1)
const carePageSize = ref(10)
const careTotal = ref(2)

const certificateCurrentPage = ref(1)
const certificatePageSize = ref(10)
const certificateTotal = ref(2)

const noticeCurrentPage = ref(1)
const noticePageSize = ref(10)
const noticeTotal = ref(2)

// 对话框状态
const rewardDialogVisible = ref(false)
const rewardDialogTitle = ref('新增奖惩')

const careDialogVisible = ref(false)
const careDialogTitle = ref('新增员工关怀')

const certificateDialogVisible = ref(false)
const certificateDialogTitle = ref('新增证书申请')

const noticeDialogVisible = ref(false)
const noticeDialogTitle = ref('新增人事公告')

// 表单数据
const rewardForm = ref({
  id: 0,
  employeeId: 0,
  type: 1,
  reason: '',
  amount: 0,
  createDate: ''
})

const careForm = ref({
  id: 0,
  employeeId: 0,
  type: 1,
  content: '',
  careDate: '',
  handler: ''
})

const certificateForm = ref({
  id: 0,
  employeeId: 0,
  certificateName: '',
  applyReason: '',
  applyDate: '',
  status: 1
})

const noticeForm = ref({
  id: 0,
  title: '',
  content: '',
  publishDate: '',
  publisher: '',
  status: 1
})

// 获取关怀类型文本
const getCareTypeText = (type: number) => {
  switch (type) {
    case 1: return '生日关怀'
    case 2: return '节日关怀'
    case 3: return '其他关怀'
    default: return '未知'
  }
}

// 获取证书状态类型
const getCertificateStatusType = (status: number) => {
  switch (status) {
    case 1: return 'info'
    case 2: return 'success'
    case 3: return 'danger'
    default: return 'info'
  }
}

// 获取证书状态文本
const getCertificateStatusText = (status: number) => {
  switch (status) {
    case 1: return '待审批'
    case 2: return '已通过'
    case 3: return '已拒绝'
    default: return '未知'
  }
}

// 处理新增奖惩
const handleAddReward = () => {
  rewardDialogTitle.value = '新增奖惩'
  rewardForm.value = {
    id: 0,
    employeeId: 0,
    type: 1,
    reason: '',
    amount: 0,
    createDate: ''
  }
  rewardDialogVisible.value = true
}

// 处理编辑奖惩
const handleEditReward = (row: any) => {
  rewardDialogTitle.value = '编辑奖惩'
  rewardForm.value = { ...row }
  rewardDialogVisible.value = true
}

// 处理删除奖惩
const handleDeleteReward = (id: number) => {
  ElMessageBox.confirm('确定要删除这个奖惩记录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除奖惩的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看奖惩
const handleViewReward = (row: any) => {
  // 这里添加查看奖惩的逻辑
  console.log('查看奖惩:', row)
}

// 处理奖惩提交
const handleRewardSubmit = () => {
  // 这里添加提交奖惩的逻辑
  rewardDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增员工关怀
const handleAddCare = () => {
  careDialogTitle.value = '新增员工关怀'
  careForm.value = {
    id: 0,
    employeeId: 0,
    type: 1,
    content: '',
    careDate: '',
    handler: ''
  }
  careDialogVisible.value = true
}

// 处理编辑员工关怀
const handleEditCare = (row: any) => {
  careDialogTitle.value = '编辑员工关怀'
  careForm.value = { ...row }
  careDialogVisible.value = true
}

// 处理删除员工关怀
const handleDeleteCare = (id: number) => {
  ElMessageBox.confirm('确定要删除这个员工关怀记录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除员工关怀的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看员工关怀
const handleViewCare = (row: any) => {
  // 这里添加查看员工关怀的逻辑
  console.log('查看员工关怀:', row)
}

// 处理员工关怀提交
const handleCareSubmit = () => {
  // 这里添加提交员工关怀的逻辑
  careDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增证书申请
const handleAddCertificate = () => {
  certificateDialogTitle.value = '新增证书申请'
  certificateForm.value = {
    id: 0,
    employeeId: 0,
    certificateName: '',
    applyReason: '',
    applyDate: '',
    status: 1
  }
  certificateDialogVisible.value = true
}

// 处理编辑证书申请
const handleEditCertificate = (row: any) => {
  certificateDialogTitle.value = '编辑证书申请'
  certificateForm.value = { ...row }
  certificateDialogVisible.value = true
}

// 处理删除证书申请
const handleDeleteCertificate = (id: number) => {
  ElMessageBox.confirm('确定要删除这个证书申请吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除证书申请的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看证书申请
const handleViewCertificate = (row: any) => {
  // 这里添加查看证书申请的逻辑
  console.log('查看证书申请:', row)
}

// 处理证书申请提交
const handleCertificateSubmit = () => {
  // 这里添加提交证书申请的逻辑
  certificateDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增人事公告
const handleAddNotice = () => {
  noticeDialogTitle.value = '新增人事公告'
  noticeForm.value = {
    id: 0,
    title: '',
    content: '',
    publishDate: '',
    publisher: '',
    status: 1
  }
  noticeDialogVisible.value = true
}

// 处理编辑人事公告
const handleEditNotice = (row: any) => {
  noticeDialogTitle.value = '编辑人事公告'
  noticeForm.value = { ...row }
  noticeDialogVisible.value = true
}

// 处理删除人事公告
const handleDeleteNotice = (id: number) => {
  ElMessageBox.confirm('确定要删除这个人事公告吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除人事公告的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看人事公告
const handleViewNotice = (row: any) => {
  // 这里添加查看人事公告的逻辑
  console.log('查看人事公告:', row)
}

// 处理人事公告提交
const handleNoticeSubmit = () => {
  // 这里添加提交人事公告的逻辑
  noticeDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理搜索
const handleRewardSearch = () => console.log('奖惩搜索条件:', rewardSearch.value)
const handleCareSearch = () => console.log('员工关怀搜索条件:', careSearch.value)
const handleCertificateSearch = () => console.log('证书申请搜索条件:', certificateSearch.value)
const handleNoticeSearch = () => console.log('人事公告搜索条件:', noticeSearch.value)

// 处理重置
const handleRewardReset = () => rewardSearch.value = { keyword: '', type: '0', dateRange: [] }
const handleCareReset = () => careSearch.value = { keyword: '', type: '0' }
const handleCertificateReset = () => certificateSearch.value = { keyword: '', status: '0' }
const handleNoticeReset = () => noticeSearch.value = { keyword: '', dateRange: [] }

// 处理分页
const handleRewardSizeChange = (size: number) => { rewardPageSize.value = size }
const handleRewardCurrentChange = (current: number) => { rewardCurrentPage.value = current }
const handleCareSizeChange = (size: number) => { carePageSize.value = size }
const handleCareCurrentChange = (current: number) => { careCurrentPage.value = current }
const handleCertificateSizeChange = (size: number) => { certificatePageSize.value = size }
const handleCertificateCurrentChange = (current: number) => { certificateCurrentPage.value = current }
const handleNoticeSizeChange = (size: number) => { noticePageSize.value = size }
const handleNoticeCurrentChange = (current: number) => { noticeCurrentPage.value = current }

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
})
</script>

<style scoped>
.employee-relation-container {
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