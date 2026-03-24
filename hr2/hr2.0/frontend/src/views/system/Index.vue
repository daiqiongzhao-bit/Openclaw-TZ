<template>
  <div class="system-container">
    <div class="page-header">
      <h2>系统管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAddUser">
          <el-icon><User /></el-icon>
          新增用户
        </el-button>
        <el-button type="success" @click="handleAddRole">
          <el-icon><Operation /></el-icon>
          新增角色
        </el-button>
        <el-button type="warning" @click="handleAddMenu">
          <el-icon><Menu /></el-icon>
          新增菜单
        </el-button>
        <el-button type="info" @click="handleAddParam">
          <el-icon><Setting /></el-icon>
          新增参数
        </el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="用户管理" name="user">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="userSearch.keyword"
              placeholder="请输入用户名或姓名"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="userSearch.status" placeholder="请选择状态" style="width: 120px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="启用" value="1" />
              <el-option label="禁用" value="0" />
            </el-select>
            <el-button type="primary" @click="handleUserSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleUserReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="users" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="username" label="用户名" width="150" />
            <el-table-column prop="name" label="姓名" width="120" />
            <el-table-column prop="email" label="邮箱" width="200" />
            <el-table-column prop="phone" label="电话" width="150" />
            <el-table-column prop="roleName" label="角色" width="120" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewUser(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditUser(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteUser(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="userCurrentPage"
              v-model:page-size="userPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="userTotal"
              @size-change="handleUserSizeChange"
              @current-change="handleUserCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="角色管理" name="role">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="roleSearch.keyword"
              placeholder="请输入角色名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleRoleSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleRoleReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="roles" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="角色名称" />
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewRole(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditRole(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteRole(scope.row.id)">
                  删除
                </el-button>
                <el-button size="small" type="info" @click="handleAssignMenu(scope.row)">
                  分配菜单
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="roleCurrentPage"
              v-model:page-size="rolePageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="roleTotal"
              @size-change="handleRoleSizeChange"
              @current-change="handleRoleCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="菜单管理" name="menu">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="menuSearch.keyword"
              placeholder="请输入菜单名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleMenuSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleMenuReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-tree
            :data="menus"
            :props="menuProps"
            node-key="id"
            default-expand-all
            style="margin-bottom: 20px"
          >
            <template #default="{ node, data }">
              <span class="tree-node">
                <span>{{ node.label }}</span>
                <span class="tree-node-actions">
                  <el-button size="small" type="primary" @click="handleViewMenu(data)">
                    查看
                  </el-button>
                  <el-button size="small" type="warning" @click="handleEditMenu(data)">
                    编辑
                  </el-button>
                  <el-button size="small" type="danger" @click="handleDeleteMenu(data.id)">
                    删除
                  </el-button>
                </span>
              </span>
            </template>
          </el-tree>
        </div>
      </el-tab-pane>

      <el-tab-pane label="系统参数" name="param">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="paramSearch.keyword"
              placeholder="请输入参数名称或键"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-button type="primary" @click="handleParamSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleParamReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="params" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="paramKey" label="参数键" width="150" />
            <el-table-column prop="paramName" label="参数名称" />
            <el-table-column prop="paramValue" label="参数值" />
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewParam(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditParam(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteParam(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="paramCurrentPage"
              v-model:page-size="paramPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="paramTotal"
              @size-change="handleParamSizeChange"
              @current-change="handleParamCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="租户管理" name="tenant">
        <div class="liquid-glass-card">
          <div class="search-bar">
            <el-input
              v-model="tenantSearch.keyword"
              placeholder="请输入租户名称"
              style="width: 300px"
              prefix-icon="Search"
            />
            <el-select v-model="tenantSearch.status" placeholder="请选择状态" style="width: 120px; margin-left: 10px">
              <el-option label="全部" value="0" />
              <el-option label="启用" value="1" />
              <el-option label="禁用" value="0" />
            </el-select>
            <el-button type="primary" @click="handleTenantSearch" style="margin-left: 10px">搜索</el-button>
            <el-button @click="handleTenantReset" style="margin-left: 10px">重置</el-button>
          </div>

          <el-table :data="tenants" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="name" label="租户名称" />
            <el-table-column prop="code" label="租户编码" width="150" />
            <el-table-column prop="contact" label="联系人" width="120" />
            <el-table-column prop="phone" label="联系电话" width="150" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleViewTenant(scope.row)">
                  查看
                </el-button>
                <el-button size="small" type="warning" @click="handleEditTenant(scope.row)">
                  编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteTenant(scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination">
            <el-pagination
              v-model:current-page="tenantCurrentPage"
              v-model:page-size="tenantPageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tenantTotal"
              @size-change="handleTenantSizeChange"
              @current-change="handleTenantCurrentChange"
            />
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 用户对话框 -->
    <el-dialog
      v-model="userDialogVisible"
      :title="userDialogTitle"
      width="600px"
    >
      <el-form :model="userForm" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="userForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="userForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="密码" v-if="!userForm.id">
          <el-input v-model="userForm.password" placeholder="请输入密码" type="password" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userForm.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="userForm.roleId" placeholder="请选择角色">
            <el-option
              v-for="role in roles"
              :key="role.id"
              :label="role.name"
              :value="role.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="userForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="userDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUserSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 角色对话框 -->
    <el-dialog
      v-model="roleDialogVisible"
      :title="roleDialogTitle"
      width="600px"
    >
      <el-form :model="roleForm" label-width="120px">
        <el-form-item label="角色名称">
          <el-input v-model="roleForm.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="roleForm.description"
            type="textarea"
            placeholder="请输入角色描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="roleForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="roleDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleRoleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 菜单对话框 -->
    <el-dialog
      v-model="menuDialogVisible"
      :title="menuDialogTitle"
      width="600px"
    >
      <el-form :model="menuForm" label-width="120px">
        <el-form-item label="菜单名称">
          <el-input v-model="menuForm.name" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="父菜单">
          <el-select v-model="menuForm.parentId" placeholder="请选择父菜单">
            <el-option label="无" value="0" />
            <el-option
              v-for="menu in menus"
              :key="menu.id"
              :label="menu.name"
              :value="menu.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="路由路径">
          <el-input v-model="menuForm.path" placeholder="请输入路由路径" />
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="menuForm.component" placeholder="请输入组件路径" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="menuForm.icon" placeholder="请输入图标名称" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="menuForm.sort" placeholder="请输入排序号" type="number" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="menuForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="menuDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleMenuSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 系统参数对话框 -->
    <el-dialog
      v-model="paramDialogVisible"
      :title="paramDialogTitle"
      width="600px"
    >
      <el-form :model="paramForm" label-width="120px">
        <el-form-item label="参数键">
          <el-input v-model="paramForm.paramKey" placeholder="请输入参数键" />
        </el-form-item>
        <el-form-item label="参数名称">
          <el-input v-model="paramForm.paramName" placeholder="请输入参数名称" />
        </el-form-item>
        <el-form-item label="参数值">
          <el-input v-model="paramForm.paramValue" placeholder="请输入参数值" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="paramForm.description"
            type="textarea"
            placeholder="请输入参数描述"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="paramForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="paramDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleParamSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 租户对话框 -->
    <el-dialog
      v-model="tenantDialogVisible"
      :title="tenantDialogTitle"
      width="600px"
    >
      <el-form :model="tenantForm" label-width="120px">
        <el-form-item label="租户名称">
          <el-input v-model="tenantForm.name" placeholder="请输入租户名称" />
        </el-form-item>
        <el-form-item label="租户编码">
          <el-input v-model="tenantForm.code" placeholder="请输入租户编码" />
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="tenantForm.contact" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="tenantForm.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input
            v-model="tenantForm.address"
            type="textarea"
            placeholder="请输入地址"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="tenantForm.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="tenantDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleTenantSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 分配菜单对话框 -->
    <el-dialog
      v-model="assignMenuDialogVisible"
      title="分配菜单"
      width="600px"
    >
      <el-form :model="assignMenuForm" label-width="120px">
        <el-form-item label="角色名称">
          <el-input v-model="assignMenuForm.roleName" disabled />
        </el-form-item>
        <el-form-item label="选择菜单">
          <el-tree
            :data="menus"
            :props="menuProps"
            node-key="id"
            show-checkbox
            default-expand-all
            v-model="assignMenuForm.menuIds"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="assignMenuDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAssignMenuSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { User, Operation, Menu, Setting, Search } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 激活的标签页
const activeTab = ref('user')

// 用户数据
const users = ref([
  {
    id: 1,
    username: 'admin',
    name: '管理员',
    email: 'admin@example.com',
    phone: '13800138001',
    roleId: 1,
    roleName: '超级管理员',
    status: 1
  },
  {
    id: 2,
    username: 'user',
    name: '普通用户',
    email: 'user@example.com',
    phone: '13800138002',
    roleId: 2,
    roleName: '普通用户',
    status: 1
  }
])

// 角色数据
const roles = ref([
  {
    id: 1,
    name: '超级管理员',
    description: '系统超级管理员',
    status: 1
  },
  {
    id: 2,
    name: '普通用户',
    description: '普通用户角色',
    status: 1
  },
  {
    id: 3,
    name: '人事管理员',
    description: '人事管理权限',
    status: 1
  }
])

// 菜单数据
const menus = ref([
  {
    id: 1,
    name: '系统管理',
    parentId: 0,
    path: '/system',
    component: 'views/system/Index.vue',
    icon: 'Setting',
    sort: 1,
    status: 1,
    children: [
      {
        id: 2,
        name: '用户管理',
        parentId: 1,
        path: '/system/user',
        component: '',
        icon: 'User',
        sort: 1,
        status: 1
      },
      {
        id: 3,
        name: '角色管理',
        parentId: 1,
        path: '/system/role',
        component: '',
        icon: 'Operation',
        sort: 2,
        status: 1
      },
      {
        id: 4,
        name: '菜单管理',
        parentId: 1,
        path: '/system/menu',
        component: '',
        icon: 'Menu',
        sort: 3,
        status: 1
      }
    ]
  }
])

// 菜单属性
const menuProps = {
  children: 'children',
  label: 'name'
}

// 系统参数数据
const params = ref([
  {
    id: 1,
    paramKey: 'SYSTEM_NAME',
    paramName: '系统名称',
    paramValue: 'HR管理系统',
    description: '系统显示名称',
    status: 1
  },
  {
    id: 2,
    paramKey: 'SYSTEM_VERSION',
    paramName: '系统版本',
    paramValue: '1.0.0',
    description: '系统版本号',
    status: 1
  },
  {
    id: 3,
    paramKey: 'MAX_UPLOAD_SIZE',
    paramName: '最大上传大小',
    paramValue: '10485760',
    description: '文件最大上传大小(字节)',
    status: 1
  }
])

// 租户数据
const tenants = ref([
  {
    id: 1,
    name: '默认租户',
    code: 'default',
    contact: '管理员',
    phone: '13800138001',
    address: '北京市朝阳区',
    status: 1
  },
  {
    id: 2,
    name: '测试租户',
    code: 'test',
    contact: '测试人员',
    phone: '13800138002',
    address: '上海市浦东新区',
    status: 1
  }
])

// 搜索条件
const userSearch = ref({ keyword: '', status: '0' })
const roleSearch = ref({ keyword: '' })
const menuSearch = ref({ keyword: '' })
const paramSearch = ref({ keyword: '' })
const tenantSearch = ref({ keyword: '', status: '0' })

// 分页参数
const userCurrentPage = ref(1)
const userPageSize = ref(10)
const userTotal = ref(2)

const roleCurrentPage = ref(1)
const rolePageSize = ref(10)
const roleTotal = ref(3)

const paramCurrentPage = ref(1)
const paramPageSize = ref(10)
const paramTotal = ref(3)

const tenantCurrentPage = ref(1)
const tenantPageSize = ref(10)
const tenantTotal = ref(2)

// 对话框状态
const userDialogVisible = ref(false)
const userDialogTitle = ref('新增用户')

const roleDialogVisible = ref(false)
const roleDialogTitle = ref('新增角色')

const menuDialogVisible = ref(false)
const menuDialogTitle = ref('新增菜单')

const paramDialogVisible = ref(false)
const paramDialogTitle = ref('新增系统参数')

const tenantDialogVisible = ref(false)
const tenantDialogTitle = ref('新增租户')

const assignMenuDialogVisible = ref(false)

// 表单数据
const userForm = ref({
  id: 0,
  username: '',
  name: '',
  password: '',
  email: '',
  phone: '',
  roleId: 0,
  status: 1
})

const roleForm = ref({
  id: 0,
  name: '',
  description: '',
  status: 1
})

const menuForm = ref({
  id: 0,
  name: '',
  parentId: 0,
  path: '',
  component: '',
  icon: '',
  sort: 0,
  status: 1
})

const paramForm = ref({
  id: 0,
  paramKey: '',
  paramName: '',
  paramValue: '',
  description: '',
  status: 1
})

const tenantForm = ref({
  id: 0,
  name: '',
  code: '',
  contact: '',
  phone: '',
  address: '',
  status: 1
})

const assignMenuForm = ref({
  roleId: 0,
  roleName: '',
  menuIds: []
})

// 处理新增用户
const handleAddUser = () => {
  userDialogTitle.value = '新增用户'
  userForm.value = {
    id: 0,
    username: '',
    name: '',
    password: '',
    email: '',
    phone: '',
    roleId: 0,
    status: 1
  }
  userDialogVisible.value = true
}

// 处理编辑用户
const handleEditUser = (row: any) => {
  userDialogTitle.value = '编辑用户'
  userForm.value = { ...row }
  userDialogVisible.value = true
}

// 处理删除用户
const handleDeleteUser = (id: number) => {
  ElMessageBox.confirm('确定要删除这个用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除用户的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看用户
const handleViewUser = (row: any) => {
  // 这里添加查看用户的逻辑
  console.log('查看用户:', row)
}

// 处理用户提交
const handleUserSubmit = () => {
  // 这里添加提交用户的逻辑
  userDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增角色
const handleAddRole = () => {
  roleDialogTitle.value = '新增角色'
  roleForm.value = {
    id: 0,
    name: '',
    description: '',
    status: 1
  }
  roleDialogVisible.value = true
}

// 处理编辑角色
const handleEditRole = (row: any) => {
  roleDialogTitle.value = '编辑角色'
  roleForm.value = { ...row }
  roleDialogVisible.value = true
}

// 处理删除角色
const handleDeleteRole = (id: number) => {
  ElMessageBox.confirm('确定要删除这个角色吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除角色的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看角色
const handleViewRole = (row: any) => {
  // 这里添加查看角色的逻辑
  console.log('查看角色:', row)
}

// 处理角色提交
const handleRoleSubmit = () => {
  // 这里添加提交角色的逻辑
  roleDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理分配菜单
const handleAssignMenu = (row: any) => {
  assignMenuForm.value = {
    roleId: row.id,
    roleName: row.name,
    menuIds: []
  }
  assignMenuDialogVisible.value = true
}

// 处理分配菜单提交
const handleAssignMenuSubmit = () => {
  // 这里添加提交分配菜单的逻辑
  assignMenuDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增菜单
const handleAddMenu = () => {
  menuDialogTitle.value = '新增菜单'
  menuForm.value = {
    id: 0,
    name: '',
    parentId: 0,
    path: '',
    component: '',
    icon: '',
    sort: 0,
    status: 1
  }
  menuDialogVisible.value = true
}

// 处理编辑菜单
const handleEditMenu = (row: any) => {
  menuDialogTitle.value = '编辑菜单'
  menuForm.value = { ...row }
  menuDialogVisible.value = true
}

// 处理删除菜单
const handleDeleteMenu = (id: number) => {
  ElMessageBox.confirm('确定要删除这个菜单吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除菜单的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看菜单
const handleViewMenu = (row: any) => {
  // 这里添加查看菜单的逻辑
  console.log('查看菜单:', row)
}

// 处理菜单提交
const handleMenuSubmit = () => {
  // 这里添加提交菜单的逻辑
  menuDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增系统参数
const handleAddParam = () => {
  paramDialogTitle.value = '新增系统参数'
  paramForm.value = {
    id: 0,
    paramKey: '',
    paramName: '',
    paramValue: '',
    description: '',
    status: 1
  }
  paramDialogVisible.value = true
}

// 处理编辑系统参数
const handleEditParam = (row: any) => {
  paramDialogTitle.value = '编辑系统参数'
  paramForm.value = { ...row }
  paramDialogVisible.value = true
}

// 处理删除系统参数
const handleDeleteParam = (id: number) => {
  ElMessageBox.confirm('确定要删除这个系统参数吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除系统参数的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看系统参数
const handleViewParam = (row: any) => {
  // 这里添加查看系统参数的逻辑
  console.log('查看系统参数:', row)
}

// 处理系统参数提交
const handleParamSubmit = () => {
  // 这里添加提交系统参数的逻辑
  paramDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理新增租户
const handleAddTenant = () => {
  tenantDialogTitle.value = '新增租户'
  tenantForm.value = {
    id: 0,
    name: '',
    code: '',
    contact: '',
    phone: '',
    address: '',
    status: 1
  }
  tenantDialogVisible.value = true
}

// 处理编辑租户
const handleEditTenant = (row: any) => {
  tenantDialogTitle.value = '编辑租户'
  tenantForm.value = { ...row }
  tenantDialogVisible.value = true
}

// 处理删除租户
const handleDeleteTenant = (id: number) => {
  ElMessageBox.confirm('确定要删除这个租户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里添加删除租户的逻辑
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 处理查看租户
const handleViewTenant = (row: any) => {
  // 这里添加查看租户的逻辑
  console.log('查看租户:', row)
}

// 处理租户提交
const handleTenantSubmit = () => {
  // 这里添加提交租户的逻辑
  tenantDialogVisible.value = false
  ElMessage.success('操作成功')
}

// 处理搜索
const handleUserSearch = () => console.log('用户搜索条件:', userSearch.value)
const handleRoleSearch = () => console.log('角色搜索条件:', roleSearch.value)
const handleMenuSearch = () => console.log('菜单搜索条件:', menuSearch.value)
const handleParamSearch = () => console.log('系统参数搜索条件:', paramSearch.value)
const handleTenantSearch = () => console.log('租户搜索条件:', tenantSearch.value)

// 处理重置
const handleUserReset = () => userSearch.value = { keyword: '', status: '0' }
const handleRoleReset = () => roleSearch.value = { keyword: '' }
const handleMenuReset = () => menuSearch.value = { keyword: '' }
const handleParamReset = () => paramSearch.value = { keyword: '' }
const handleTenantReset = () => tenantSearch.value = { keyword: '', status: '0' }

// 处理分页
const handleUserSizeChange = (size: number) => { userPageSize.value = size }
const handleUserCurrentChange = (current: number) => { userCurrentPage.value = current }
const handleRoleSizeChange = (size: number) => { rolePageSize.value = size }
const handleRoleCurrentChange = (current: number) => { roleCurrentPage.value = current }
const handleParamSizeChange = (size: number) => { paramPageSize.value = size }
const handleParamCurrentChange = (current: number) => { paramCurrentPage.value = current }
const handleTenantSizeChange = (size: number) => { tenantPageSize.value = size }
const handleTenantCurrentChange = (current: number) => { tenantCurrentPage.value = current }

// 组件挂载时执行
onMounted(() => {
  // 这里可以添加获取数据的逻辑
})
</script>

<style scoped>
.system-container {
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

.tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.tree-node-actions {
  display: flex;
  gap: 5px;
}
</style>