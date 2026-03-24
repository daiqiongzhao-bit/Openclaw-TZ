const pptxgen = require('pptxgenjs');
const path = require('path');

const pptx = new pptxgen();
pptx.layout = 'LAYOUT_WIDE'; // 13.333 x 7.5
pptx.author = '太子 / OpenClaw';
pptx.company = 'OpenClaw';
pptx.subject = '近期工作汇报';
pptx.title = '太子近期工作汇报';
pptx.lang = 'zh-CN';
pptx.theme = {
  headFontFace: 'Microsoft YaHei',
  bodyFontFace: 'Microsoft YaHei',
  lang: 'zh-CN'
};
pptx.defineSlideMaster({
  title: 'MASTER',
  background: { color: 'F7FAFC' },
  objects: [
    { rect: { x: 0, y: 0, w: 13.333, h: 0.18, fill: { color: '10D9A3' }, line: { color: '10D9A3' } } },
    { rect: { x: 12.95, y: 0, w: 0.383, h: 7.5, fill: { color: 'EAFBF6' }, line: { color: 'EAFBF6' } } },
    { text: { text: '太子 · 近期工作汇报', options: { x: 0.55, y: 7.0, w: 4.2, h: 0.25, fontSize: 9, color: '6B7280' } } },
    { text: { text: 'OpenClaw Workspace', options: { x: 10.6, y: 7.0, w: 2.0, h: 0.25, fontSize: 9, color: '9CA3AF', align: 'right' } } }
  ]
});

function addTitle(slide, title, subtitle='') {
  slide.addText(title, { x: 0.6, y: 0.45, w: 8.8, h: 0.55, fontSize: 28, bold: true, color: '111827', margin: 0 });
  if (subtitle) slide.addText(subtitle, { x: 0.62, y: 1.0, w: 9.4, h: 0.28, fontSize: 11, color: '6B7280', margin: 0 });
}

function addMetric(slide, x, y, w, h, num, label, color='10D9A3') {
  slide.addShape(pptx.ShapeType.roundRect, { x, y, w, h, rectRadius: 0.08, fill: { color: 'FFFFFF', transparency: 0 }, line: { color: 'DDE7EE', pt: 1 } });
  slide.addShape(pptx.ShapeType.roundRect, { x: x+0.12, y: y+0.16, w: 0.12, h: h-0.32, rectRadius: 0.03, fill: { color }, line: { color } });
  slide.addText(String(num), { x: x+0.32, y: y+0.18, w: w-0.45, h: 0.35, fontSize: 24, bold: true, color: '111827', margin: 0 });
  slide.addText(label, { x: x+0.32, y: y+0.58, w: w-0.45, h: 0.22, fontSize: 10, color: '6B7280', margin: 0 });
}

function addBulletList(slide, items, x, y, w, h, opts={}) {
  let curY = y;
  const step = opts.step || 0.5;
  const fs = opts.fontSize || 16;
  items.forEach((item) => {
    slide.addShape(pptx.ShapeType.ellipse, { x, y: curY+0.08, w: 0.12, h: 0.12, fill: { color: opts.dotColor || '10D9A3' }, line: { color: opts.dotColor || '10D9A3' } });
    slide.addText(item, { x: x+0.22, y: curY, w: w-0.22, h: 0.32, fontSize: fs, color: '1F2937', breakLine: false, margin: 0, valign: 'mid' });
    curY += step;
  });
}

function addSectionCard(slide, x, y, w, h, title, bullets, accent='10D9A3') {
  slide.addShape(pptx.ShapeType.roundRect, { x, y, w, h, rectRadius: 0.08, fill: { color: 'FFFFFF' }, line: { color: 'D9E5EC', pt: 1.2 } });
  slide.addText(title, { x: x+0.22, y: y+0.18, w: w-0.44, h: 0.3, fontSize: 18, bold: true, color: '111827', margin: 0 });
  slide.addShape(pptx.ShapeType.roundRect, { x: x+0.22, y: y+0.56, w: 0.46, h: 0.08, rectRadius: 0.03, fill: { color: accent }, line: { color: accent } });
  addBulletList(slide, bullets, x+0.22, y+0.82, w-0.32, h-0.9, { step: 0.42, fontSize: 12, dotColor: accent });
}

// Slide 1
{
  const s = pptx.addSlide('MASTER');
  s.background = { color: '0F172A' };
  s.addShape(pptx.ShapeType.rect, { x: 0, y: 0, w: 13.333, h: 7.5, fill: { color: '0F172A' }, line: { color: '0F172A' } });
  s.addShape(pptx.ShapeType.rect, { x: 8.8, y: 0, w: 4.533, h: 7.5, fill: { color: '10D9A3', transparency: 88 }, line: { color: '10D9A3', transparency: 100 } });
  s.addText('近期工作汇报', { x: 0.75, y: 1.2, w: 4.8, h: 0.8, fontSize: 28, bold: true, color: 'FFFFFF', margin: 0 });
  s.addText('覆盖网站修复、技能安装、部署排障与 HR2.0 交付整理', { x: 0.78, y: 2.05, w: 6.2, h: 0.4, fontSize: 14, color: 'CBD5E1', margin: 0 });
  s.addText('汇报人：太子\n时间范围：近 3–4 天会话与工作区变更', { x: 0.78, y: 5.4, w: 4.0, h: 0.6, fontSize: 13, color: 'E5E7EB', margin: 0, breakLine: true });
  addMetric(s, 7.5, 1.3, 1.7, 1.1, 4, '重点任务');
  addMetric(s, 9.35, 1.3, 1.7, 1.1, 4, '新增技能');
  addMetric(s, 11.2, 1.3, 1.4, 1.1, '1+', '持续修复项');
  s.addText('核心基调：能落地的先落地，不能落地的把卡点钉死。', { x: 7.55, y: 3.05, w: 4.4, h: 0.6, fontSize: 17, bold: true, color: 'F8FAFC', margin: 0, breakLine: true });
  addBulletList(s, [
    '完成 Edict 看板公网入口与登录体验修整',
    '安装多项技能体，扩充浏览器自动化与主动代理能力',
    '推进 HR2.0 项目部署，解决前后端多处编译问题',
    '沉淀多份记忆记录，便于后续接力与回溯'
  ], 7.58, 4.0, 4.7, 2.2, { step: 0.52, fontSize: 13, dotColor: '10D9A3' });
}

// Slide 2
{
  const s = pptx.addSlide('MASTER');
  addTitle(s, '总览：这几天我主要干了什么', '按结果分为四条主线：网站、技能、部署、文档沉淀');
  addMetric(s, 0.7, 1.45, 2.55, 1.0, 'Edict', '看板入口与登录修整', '36BFFA');
  addMetric(s, 3.45, 1.45, 2.55, 1.0, 'Skills', '新增 4 个能力模块', '10D9A3');
  addMetric(s, 6.2, 1.45, 2.55, 1.0, 'HR2.0', '部署与兼容性排障', 'FF7D00');
  addMetric(s, 8.95, 1.45, 2.55, 1.0, 'Memory', '过程记录与状态沉淀', '8B5CF6');
  addSectionCard(s, 0.7, 2.9, 3.0, 3.2, '1. Edict 看板', [
    '确认本地服务已跑通，定位问题集中在公网入口层',
    '后续将无端口访问恢复为可用公网地址',
    '登录页和个人设置入口完成一轮可用性优化'
  ], '36BFFA');
  addSectionCard(s, 3.95, 2.9, 3.0, 3.2, '2. 技能体建设', [
    '安装 agent-browser-clawdbot',
    '安装 proactive-agent 与 auto-updater',
    '安装 playwright-mcp，增强网页自动化能力'
  ], '10D9A3');
  addSectionCard(s, 7.2, 2.9, 2.75, 3.2, '3. HR2.0 部署', [
    '解包并梳理项目结构',
    '修复前端构建错误与后端接口不一致问题',
    '持续追查 Spring / Flowable 兼容异常'
  ], 'FF7D00');
  addSectionCard(s, 10.15, 2.9, 2.45, 3.2, '4. 文档沉淀', [
    '新增多份 memory 记录',
    '保留部署与登录改动证据',
    '让后续复盘不靠口述'
  ], '8B5CF6');
}

// Slide 3
{
  const s = pptx.addSlide('MASTER');
  addTitle(s, '主线一：Edict 网站修复与可访问性推进', '目标：把“本地已跑”推进到“公网可访问、可登录、可操作”');
  addSectionCard(s, 0.7, 1.45, 3.9, 4.9, '已完成动作', [
    '确认 Edict Dashboard 本地监听在 127.0.0.1:7891',
    '将公网可用入口整理为 https://txy.960898.xyz/',
    '调整登录用户名为 admin，保留密码 123456（未变更前）',
    '移除登录页暴露配置路径的提示信息',
    '新增右上角“个人设置”：修改密码 + 退出登录',
    '安装 Chromium 与 chromium-driver，支持无头截图验收'
  ], '36BFFA');
  addSectionCard(s, 4.9, 1.45, 3.85, 4.9, '关键证据', [
    '工作目录：edict/',
    '登录验收截图：screenshots/edict-login-verify.png',
    '首页验收截图：screenshots/edict-home-verify.png',
    '认证配置：edict_auth.json / edict_auth_audit.json',
    '看板页面：edict/dashboard/dashboard.html'
  ], '10D9A3');
  addSectionCard(s, 8.95, 1.45, 3.65, 4.9, '结果判断', [
    '不是应用本体坏，而是入口与交互体验需要补齐',
    '网站修复已经从“本地 demo”推进到“可对外使用”的状态',
    '后续维护重点将转向稳定性与权限体验，而不是从零搭建'
  ], 'FF7D00');
}

// Slide 4
{
  const s = pptx.addSlide('MASTER');
  addTitle(s, '主线二：技能体扩充，补齐代理工具箱', '目标：让后续任务处理更自动化、更主动');
  s.addShape(pptx.ShapeType.roundRect, { x: 0.8, y: 1.55, w: 11.8, h: 4.9, rectRadius: 0.08, fill: { color: 'FFFFFF' }, line: { color: 'D9E5EC', pt: 1.2 } });
  const cols = [0.98, 3.95, 6.92, 9.89];
  const data = [
    ['agent-browser-clawdbot', '浏览器自动化技能', ['可用于网页点击、读取、表单操作', '适合接管需要“真进页面”的任务']],
    ['proactive-agent', '主动代理框架', ['支持更持续的工作流设计', '为后续自动巡检和持续推进打基础']],
    ['auto-updater', '自动更新技能', ['支持技能和相关组件自动更新', '减少手工维护成本']],
    ['playwright-mcp', 'Playwright 能力扩展', ['强化网页测试 / 截图 / 交互排查', '利于前端验收与页面排错']]
  ];
  data.forEach((d, i) => {
    const x = cols[i];
    s.addShape(pptx.ShapeType.roundRect, { x, y: 1.9, w: 2.55, h: 4.15, rectRadius: 0.06, fill: { color: 'F8FFFD' }, line: { color: i % 2 ? 'BDEFD8' : 'C7E8FB', pt: 1 } });
    s.addShape(pptx.ShapeType.roundRect, { x: x+0.18, y: 2.08, w: 0.48, h: 0.48, rectRadius: 0.08, fill: { color: i % 2 ? '10D9A3' : '36BFFA' }, line: { color: i % 2 ? '10D9A3' : '36BFFA' } });
    s.addText(String(i+1), { x: x+0.18, y: 2.16, w: 0.48, h: 0.15, fontSize: 11, color: 'FFFFFF', bold: true, align: 'center', margin: 0 });
    s.addText(d[0], { x: x+0.18, y: 2.7, w: 2.1, h: 0.55, fontSize: 14, bold: true, color: '111827', margin: 0, fit: 'shrink' });
    s.addText(d[1], { x: x+0.18, y: 3.28, w: 2.05, h: 0.28, fontSize: 10, color: '6B7280', margin: 0 });
    addBulletList(s, d[2], x+0.18, 3.72, 2.15, 1.8, { step: 0.6, fontSize: 11, dotColor: i % 2 ? '10D9A3' : '36BFFA' });
  });
  s.addText('补充说明：elite-longterm-memory 曾被检查，但因可疑风险未安装，避免把不干净的技能体带进工作区。', { x: 0.95, y: 6.68, w: 10.6, h: 0.3, fontSize: 11, color: '6B7280', margin: 0 });
}

// Slide 5
{
  const s = pptx.addSlide('MASTER');
  addTitle(s, '主线三：HR2.0 项目部署与排障进展', '目标：把用户提供的 HR2.0 包从“能看”推进到“能编译、能启动、能部署”');
  addSectionCard(s, 0.7, 1.45, 3.8, 4.95, '推进内容', [
    '接收并解压 /opt/hr2.0.zip 对应项目',
    '确认产物目录 hr2/hr2.0，含 Dockerfile、docker-compose、前后端工程',
    '对照需求文档，确认技术栈为 Vue3 + Spring Boot3 + MySQL/Redis/RabbitMQ'
  ], '10D9A3');
  addSectionCard(s, 4.75, 1.45, 3.9, 4.95, '已经修掉的问题', [
    '修复前端 Element Plus 图标错误导入：Gift / Start / Heart',
    '清理多处 TypeScript 未使用变量报错，令前端构建可过',
    '补上 @MapperScan("com.hr.dao")，解决 SalaryCalculationDao 注入失败',
    '调整 Resume / InterviewRecord 相关 service 与 controller 方法对齐'
  ], '36BFFA');
  addSectionCard(s, 8.9, 1.45, 3.7, 4.95, '仍在处理的卡点', [
    'hr-app 运行阶段仍出现 factoryBeanObjectType 属性类型异常',
    'Spring Boot 3.2.4 / Spring 6.1.5 与部分依赖存在兼容疑点',
    '已尝试升级 mybatis-plus、flowable，并继续做隔离排查',
    '当前已进入“定位根因”阶段，而非停留在表面编译错误'
  ], 'FF7D00');
}

// Slide 6
{
  const s = pptx.addSlide('MASTER');
  addTitle(s, '过程方法：不是只报结果，而是把问题钉死', '这几天处理方式的特点：先拿证据，再分层推进');
  s.addShape(pptx.ShapeType.roundRect, { x: 0.9, y: 1.55, w: 11.5, h: 4.9, rectRadius: 0.08, fill: { color: 'FFFFFF' }, line: { color: 'D9E5EC', pt: 1.2 } });
  const steps = [
    ['01', '先确认“有没有跑起来”', '例如区分 Edict 是应用没起，还是公网入口没接通'],
    ['02', '再修显性错误', '先消灭构建报错、路径暴露、UI 阻塞等最表层问题'],
    ['03', '继续打到兼容层', '对 HR2.0 深挖到 Spring / Flowable / Mapper 级别，不停在“像能用”'],
    ['04', '最后沉淀记忆与证据', '把截图、路径、配置、会话总结保留下来，方便复盘与接力']
  ];
  steps.forEach((st, idx) => {
    const y = 1.9 + idx * 1.1;
    s.addShape(pptx.ShapeType.roundRect, { x: 1.2, y, w: 0.62, h: 0.62, rectRadius: 0.12, fill: { color: idx % 2 ? '36BFFA' : '10D9A3' }, line: { color: idx % 2 ? '36BFFA' : '10D9A3' } });
    s.addText(st[0], { x: 1.2, y: y+0.15, w: 0.62, h: 0.18, fontSize: 12, color: 'FFFFFF', bold: true, align: 'center', margin: 0 });
    s.addText(st[1], { x: 2.0, y: y+0.02, w: 3.4, h: 0.24, fontSize: 16, bold: true, color: '111827', margin: 0 });
    s.addText(st[2], { x: 2.0, y: y+0.32, w: 8.7, h: 0.22, fontSize: 11, color: '6B7280', margin: 0 });
  });
  s.addText('这意味着：有些项目没有立即“彻底完工”，但不会再停留在含糊状态，而是能明确知道卡在哪一层、下一步该动哪里。', { x: 1.2, y: 6.72, w: 10.2, h: 0.3, fontSize: 11, color: '6B7280', margin: 0 });
}

// Slide 7
{
  const s = pptx.addSlide('MASTER');
  addTitle(s, '下一步建议', '如果要继续推进，我建议按轻重缓急这么排');
  addSectionCard(s, 0.9, 1.6, 3.55, 4.6, '优先级 A：收口 HR2.0', [
    '继续隔离 factoryBeanObjectType 异常根因',
    '把“能编译”推进到“能稳定启动”',
    '若必要，拆分 Flowable 相关模块做最小可运行版本'
  ], 'FF7D00');
  addSectionCard(s, 4.9, 1.6, 3.55, 4.6, '优先级 B：稳固 Edict', [
    '继续做公网访问、账号安全和交互小修小补',
    '补充巡检与异常提示，减少“看起来没问题、其实入口挂了”'] , '36BFFA');
  addSectionCard(s, 8.9, 1.6, 3.55, 4.6, '优先级 C：放大自动化', [
    '把新增技能体真正接入日常工作流',
    '让浏览器自动化、自动更新、主动代理开始长期值守'
  ], '10D9A3');
  s.addText('一句话总结：这几天不是“忙了一堆没结果”，而是把两个大块任务都从模糊状态推进到了可控状态。', { x: 1.0, y: 6.6, w: 10.7, h: 0.3, fontSize: 12, color: '374151', bold: true, margin: 0 });
}

const out = path.join('/root/.openclaw/workspace-taizi/reports', '太子近期工作汇报.pptx');
pptx.writeFile({ fileName: out });
console.log(out);
