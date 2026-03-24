<template>
  <div class="salary-container">
    <div class="page-header">
      <h2>薪酬福利管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddSalaryStructure">
          <el-icon><Plus /></el-icon>
          新增薪酬结构
        </el-button>
        <el-button type="success" @click="handleAddWelfare">
          <el-icon><Gift /></el-icon>
          新增福利
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="薪酬结构" name="structure">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="structureSearch.keyword"
              placeholder="请输入薪酬结构名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleStructureSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleStructureReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="salaryStructures" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="结构名称" width="150" />
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewSalaryStructure(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditSalaryStructure(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteSalaryStructure(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="structureCurrentPage"
              v-model:page-size="structurePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="structureTotal"
              @size-change="handleStructureSizeChange"
              @current-change="handleStructureCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="薪资计算" name="calculation">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="calculationSearch.keyword"
              placeholder="请输入员工姓名或工号"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="calculationSearch.month" placeholder="请选择月份" style="width: 150px; margin-left: 10px">
              <el-option
                v-for="month in months"
                :key="month.value"
                :label="month.label"
                :value="month.value"
              />
            </el-select>
            <el-button type="primary" @click="handleCalculationSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleCalculationReset" style="margin-left: 10px">重置</el-button>
            <el-button type="success" style="margin-left: 10px" @click="handleCalculateSalary">
              计算薪资
            </el-button>
          </div>

          <el-table :data="salaryCalculations" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeId" label="工号" width="100" />
            <el-table-column prop="employeeName" label="姓名" width="120" />
            <el-table-column prop="month" label="月份" width="120" />
            <el-table-column prop="baseSalary" label="基本工资" width="120" />
            <el-table-column prop="allowance" label="津贴" width="100" />
            <el-table-column prop="bonus" label="奖金" width="100" />
            <el-table-column prop="deduction" label="扣除" width="100" />
            <el-table-column prop="netSalary" label="实发工资" width="120">
              <template #default="scope">
                <span style="font-weight: bold; color: #10D9A3;">{{ scope.row.netSalary }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '已发放' : '待发放' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewSalaryCalculation(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditSalaryCalculation(scope.row)">
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="calculationCurrentPage"
              v-model:page-size="calculationPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="calculationTotal"
              @size-change="handleCalculationSizeChange"
              @current-change="handleCalculationCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="福利管理" name="welfare">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="welfareSearch.keyword"
              placeholder="请输入福利名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="welfareSearch.type" placeholder="请选择福利类型" style="width: 150px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="法定福利" value="1" />
              <el-option label="企业福利" value="2" />
              <el-option label="节日福利" value="3" />
            </el-select>
            <el-button type="primary" @click="handleWelfareSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleWelfareReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="welfareTypes" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="福利名称" width="150" />
            <el-table-column prop="type" label="福利类型" width="120">
              <template #default="scope">
                {{ getWelfareTypeText(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column prop="description" label="描述" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewWelfare(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditWelfare(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteWelfare(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="welfareCurrentPage"
              v-model:page-size="welfarePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="welfareTotal"
              @size-change="handleWelfareSizeChange"
              @current-change="handleWelfareCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="社保管理" name="social">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="socialSearch.keyword"
              placeholder="请输入员工姓名或工号"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleSocialSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleSocialReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="socialSecurityAccounts" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="employeeId" label="工号" width="100" />
            <el-table-column prop="employeeName" label="姓名" width="120" />
            <el-table-column prop="socialSecurityNumber" label="社保号" width="180" />
            <el-table-column prop="medicalInsuranceNumber" label="医保号" width="180" />
            <el-table-column prop="housingFundNumber" label="公积金号" width="180" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
                  {{ scope.row.status === 1 ? '正常' : '暂停' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewSocialAccount(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditSocialAccount(scope.row)">
                  编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="socialCurrentPage"
              v-model:page-size="socialPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="socialTotal"
              @size-change="handleSocialSizeChange"
              @current-change="handleSocialCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 薪酬结构对话框 -->
    <el-dialog
      v-model="structureDialogVisible"
      :title="structureDialogTitle"
      width="600px"
    >
      <el-form :model="structureForm" label-width="120px">
        <el-form-item label="结构名称">
          <el-input v-model="structureForm.name" placeholder="请输入薪酬结构名称" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="structureForm.description"
            type="textarea"
            placeholder="请输入描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="薪资项目">
          <el-table :data="structureForm.items" style="width: 100%">
            <el-table-column prop="name" label="项目名称" width="150" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="scope">
                {{ scope.row.type === 1 ? '收入' : '支出' }}
              </template>
            </el-table-column>
            <el-table-column prop="amount" label="金额/比例" width="120" />
            <el-table-column label="操作" width="100">
              <template #default="scope">
                <el-button size="small" type="danger" @click="handleDeleteItem(scope.$index)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" size="small" @click="handleAddItem" style="margin-top: 10px">
            添加项目
          </el-button>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="structureDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleStructureSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 福利对话框 -->
    <el-dialog
      v-model="welfareDialogVisible"
      :title="welfareDialogTitle"
      width="600px"
    >
      <el-form :model="welfareForm" label-width="120px">
        <el-form-item label="福利名称">
          <el-input v-model="welfareForm.name" placeholder="请输入福利名称" />
        </el-form-item>
        <el-form-item label="福利类型">
          <el-select v-model="welfareForm.type" placeholder="请选择福利类型">
            <el-option label="法定福利" value="1" />
            <el-option label="企业福利" value="2" />
            <el-option label="节日福利" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="welfareForm.description"
            type="textarea"
            placeholder="请输入描述"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="welfareDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleWelfareSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Plus, Search, Gift } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 激活的标签页
const activeTab = ref('structure')

// 月份选项
const months = ref([
  { label: '2024年1月', value: '202401' },
  { label: '2024年2月', value: '202402' },
  { label: '2024年3月', value: '202403' },
  { label: '2024年4月', value: '202404' },
  { label: '2024年5月', value: '202405' },
  { label: '2024年6月', value: '202406' }
])

// 薪酬结构数据
const salaryStructures = ref([
  {
    id: 1,
    name: '标准薪酬结构',
    description: '公司标准薪酬结构',
    createTime: '2024-01-01 00:00:00'
  },
  {
    id: 2,
    name: '管理层薪酬结构',
    description: '管理层专用薪酬结构',
    createTime: '2024-01-01 00:00:00'
  }
])

// 薪资计算数据
const salaryCalculations = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    month: '202401',
    baseSalary: 8000,
    allowance: 1000,
    bonus: 2000,
    deduction: 1500,
    netSalary: 9500,
    status: 1
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    month: '202401',
    baseSalary: 7000,
    allowance: 800,
    bonus: 1500,
    deduction: 1200,
    netSalary: 8100,
    status: 1
  },
  {
    id: 3,
    employeeId: 3,
    employeeName: '王五',
    month: '202401',
    baseSalary: 6000,
    allowance: 600,
    bonus: 1000,
    deduction: 1000,
    netSalary: 6600,
    status: 0
  }
])

// 福利类型数据
const welfareTypes = ref([
  {
    id: 1,
    name: '五险一金',
    type: 1,
    description: '法定社会保险和住房公积金'
  },
  {
    id: 2,
    name: '节日福利',
    type: 3,
    description: '节假日发放的福利'
  },
  {
    id: 3,
    name: '餐补',
    type: 2,
    description: '公司提供的餐费补贴'
  }
])

// 社保账户数据
const socialSecurityAccounts = ref([
  {
    id: 1,
    employeeId: 1,
    employeeName: '张三',
    socialSecurityNumber: '123456789012345678',
    medicalInsuranceNumber: '987654321098765432',
    housingFundNumber: '112233445566778899',
    status: 1
  },
  {
    id: 2,
    employeeId: 2,
    employeeName: '李四',
    socialSecurityNumber: '123456789012345679',
    medicalInsuranceNumber: '987654321098765433',
    housingFundNumber: '112233445566778900',
    status: 1
  }
])

// 搜索条件
const structureSearch = ref({ keyword: '' })
const calculationSearch = ref({ keyword: '', month: '' })
const welfareSearch = ref({ keyword: '', type: '0' })
const socialSearch = ref({ keyword: '' })

// 分页参数
const structureCurrentPage = ref(1)
const structurePageSize = ref(10)
const structureTotal = ref(2)

const calculationCurrentPage = ref(1)
const calculationPageSize = ref(10)
const calculationTotal = ref(3)

const welfareCurrentPage = ref(1)
const welfarePageSize = ref(10)
const welfareTotal = ref(3)

const socialCurrentPage = ref(1)
const socialPageSize = ref(10)
const socialTotal = ref(2)

// 对话框状态
const structureDialogVisible = ref(false)
const structureDialogTitle = ref('新增薪酬结构')

const welfareDialogVisible = ref(false)
const welfareDialogTitle = ref('新增福利')

// 表单数据
const structureForm = ref({
  id: 0,
  name: '',
  description: '',
  items: [
    { name: '基本工资', type: 1, amount: '8000' },
    { name: '餐补', type: 1, amount: '500' },
    { name: '社保', type: 2, amount: '1200' },
    { name: '个税', type: 2, amount: '300' }
  ]
})

const welfareForm = ref({
  id: 0,
  name: '',
  type: 1,
  description: ''
})

// 获取福利类型文本
const getWelfareTypeText = (type: number) => {
  switch (type) {
    case 1: return '法定福利'
    case 2: return '企业福利'
    case 3: return '节日福利'
    default: return '未知'
  }
}

// 处理新增薪酬结构
const handleAddSalaryStructure = () => {
  structureDialogTitle.value = '新增薪酬结构'
  structureForm.value = {
    id: 0,
    name: '',
    description: '',
    items: []
  }
  structureDialogVisible.value = true
}

// 处理编辑薪酬结构
const handleEditSalaryStructure = (row: any) => {
  structureDialogTitle.value = '编辑薪酬结构'
  structureForm.value = { ...row }
  structureDialogVisible.value = true
}

// 处理删除薪酬结构
const handleDeleteSalaryStructure = (id: number) => {
  ElMessageBox.confirm('确定要删除这个薪酬结构吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除薪酬结构的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看薪酬结构
const handleViewSalaryStructure = (row: any) => {
  // 这里添加查看薪酬结构的逻辑
  console.log('查看薪酬结构:', row)
}

// 处理添加薪酬项目
const handleAddItem = () => {
  structureForm.value.items.push({ name: '', type: 1, amount: '' })
}

// 处理删除薪酬项目
const handleDeleteItem = (index: number) => {
  structureForm.value.items.splice(index, 1)
}

// 处理薪酬结构提交
const handleStructureSubmit = () => {
  // 这里添加提交薪酬结构的逻辑
  structureDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增福利
const handleAddWelfare = () => {
  welfareDialogTitle.value = '新增福利'
  welfareForm.value = {
    id: 0,
    name: '',
    type: 1,
    description: ''
  }
  welfareDialogVisible.value = true
}

// 处理编辑福利
const handleEditWelfare = (row: any) => {
  welfareDialogTitle.value = '编辑福利'
  welfareForm.value = { ...row }
  welfareDialogVisible.value = true
}

// 处理删除福利
const handleDeleteWelfare = (id: number) => {
  ElMessageBox.confirm('确定要删除这个福利吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除福利的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看福利
const handleViewWelfare = (row: any) => {
  // 这里添加查看福利的逻辑
  console.log('查看福利:', row)
}

// 处理福利提交
const handleWelfareSubmit = () => {
  // 这里添加提交福利的逻辑
  welfareDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理计算薪资
const handleCalculateSalary = () => {
  // 这里添加计算薪资的逻辑
  ElMessage.success('薪资计算完成')
}

// 处理查看薪资计算
const handleViewSalaryCalculation = (row: any) => {
  // 这里添加查看薪资计算的逻辑
  console.log('查看薪资计算:', row)
}

// 处理编辑薪资计算
const handleEditSalaryCalculation = (row: any) => {
  // 这里添加编辑薪资计算的逻辑
  console.log('编辑薪资计算:', row)
}

// 处理查看社保账户
const handleViewSocialAccount = (row: any) => {
  // 这里添加查看社保账户的逻辑
  console.log('查看社保账户:', row)
}

// 处理编辑社保账户
const handleEditSocialAccount = (row: any) => {
  // 这里添加编辑社保账户的逻辑
  console.log('编辑社保账户:', row)
}

// 处理搜索
const handleStructureSearch = () => console.log('薪酬结构搜索条件:', structureSearch.value)
const handleCalculationSearch = () => console.log('薪资计算搜索条件:', calculationSearch.value)
const handleWelfareSearch = () => console.log('福利搜索条件:', welfareSearch.value)
const handleSocialSearch = () => console.log('社保搜索条件:', socialSearch.value)

// 处理重置
const handleStructureReset = () => structureSearch.value = { keyword: '' }
const handleCalculationReset = () => calculationSearch.value = { keyword: '', month: '' }
const handleWelfareReset = () => welfareSearch.value = { keyword: '', type: '0' }
const handleSocialReset = () => socialSearch.value = { keyword: '' }

// 处理分页
const handleStructureSizeChange = (size: number) => { structurePageSize.value = size }
const handleStructureCurrentChange = (current: number) => { structureCurrentPage.value = current }
const handleCalculationSizeChange = (size: number) => { calculationPageSize.value = size }
const handleCalculationCurrentChange = (current: number) => { calculationCurrentPage.value = current }
const handleWelfareSizeChange = (size: number) => { welfarePageSize.value = size }
const handleWelfareCurrentChange = (current: number) => { welfareCurrentPage.value = current }
const handleSocialSizeChange = (size: number) => { socialPageSize.value = size }
const handleSocialCurrentChange = (current: number) => { socialCurrentPage.value = current }

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
})
</script>

<style scoped>
.salary-container {
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