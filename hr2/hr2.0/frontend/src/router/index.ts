import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('../views/Home.vue'),
      children: [
        {
          path: 'organization',
          name: 'Organization',
          component: () => import('../views/organization/Index.vue')
        },
        {
          path: 'employee',
          name: 'Employee',
          component: () => import('../views/employee/Index.vue')
        },
        {
          path: 'attendance',
          name: 'Attendance',
          component: () => import('../views/attendance/Index.vue')
        },
        {
          path: 'salary',
          name: 'Salary',
          component: () => import('../views/salary/Index.vue')
        },
        {
          path: 'performance',
          name: 'Performance',
          component: () => import('../views/performance/Index.vue')
        },
        {
          path: 'recruitment',
          name: 'Recruitment',
          component: () => import('../views/recruitment/Index.vue')
        },
        {
          path: 'training',
          name: 'Training',
          component: () => import('../views/training/Index.vue')
        },
        {
          path: 'employee-relation',
          name: 'EmployeeRelation',
          component: () => import('../views/employee-relation/Index.vue')
        },
        {
          path: 'workflow',
          name: 'Workflow',
          component: () => import('../views/workflow/Index.vue')
        },
        {
          path: 'bi',
          name: 'BI',
          component: () => import('../views/bi/Index.vue')
        },
        {
          path: 'system',
          name: 'System',
          component: () => import('../views/system/Index.vue')
        },
        {
          path: 'ess',
          name: 'ESS',
          component: () => import('../views/ess/Index.vue'),
          children: [
            {
              path: 'profile',
              name: 'ESSProfile',
              component: () => import('../views/ess/Profile.vue')
            },
            {
              path: 'attendance',
              name: 'ESSAttendance',
              component: () => import('../views/ess/Attendance.vue')
            },
            {
              path: 'salary',
              name: 'ESSSalary',
              component: () => import('../views/ess/Salary.vue')
            },
            {
              path: 'performance',
              name: 'ESSPerformance',
              component: () => import('../views/ess/Performance.vue')
            },
            {
              path: 'process',
              name: 'ESSProcess',
              component: () => import('../views/ess/Process.vue')
            },
            {
              path: 'certificate',
              name: 'ESSCertificate',
              component: () => import('../views/ess/Certificate.vue')
            },
            {
              path: 'message',
              name: 'ESSMessage',
              component: () => import('../views/ess/Message.vue')
            }
          ]
        }
      ]
    }
  ]
})

export default router