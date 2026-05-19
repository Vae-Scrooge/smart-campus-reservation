# 贡献指南

感谢你考虑为智能校园预约系统贡献力量！

## 提交 Issue

- **Bug 报告**：请提供复现步骤、预期行为和实际行为
- **功能建议**：请清晰描述你希望的功能及其使用场景

## 提交 PR

1. Fork 本仓库
2. 从 `main` 分支创建你的特性分支：
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. 提交你的变更：
   ```bash
   git commit -m "feat: add some feature"
   ```
4. 推送到你的 Fork：
   ```bash
   git push origin feature/your-feature-name
   ```
5. 提交 Pull Request 到 `main` 分支

## 代码规范

### 后端

- 遵循 Spring Boot 官方推荐的项目结构和命名规范
- 使用 Lombok 减少样板代码
- REST API 遵循 RESTful 设计原则
- 所有公共方法添加有意义的注释

### 前端

- 使用 Vue 3 Composition API (`<script setup>`)
- 组件文件名使用 PascalCase
- 使用 Element Plus 组件保持 UI 一致性
- API 调用统一通过 `src/api/` 目录下的模块管理

## Commit 规范

使用 Conventional Commits 格式：

- `feat`: 新功能
- `fix`: Bug 修复
- `docs`: 文档更新
- `style`: 代码风格调整（不影响功能）
- `refactor`: 重构
- `test`: 测试相关
- `chore`: 构建/工具链变更

## 开发流程

1. 确保所有现有测试通过
2. 新功能应包含对应的测试
3. 提交前检查代码风格
4. PR 需要至少一人 Review 后方可合并
