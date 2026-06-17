# huanbaoXiTong-classwork

AQI 网格监督管理系统课程作业，包含 Spring Boot 后端、公众监督员端、管理员端、网格员端和数据库初始化脚本。

## 目录

- `nepserver/nepserver`: 后端 Spring Boot 项目
- `前端/neps（公众监督员）`: 公众监督员端
- `前端/nepm（管理员）`: 管理员端
- `前端/nepg（网格员端）`: 网格员端
- `nep.sql`: 数据库初始化脚本
- `需求分析文档.docx`: 需求文档

## 本地运行

1. 导入数据库：创建 `nep` 数据库后执行 `nep.sql`。
2. 修改后端数据库配置：`nepserver/nepserver/src/main/resources/application.yml`。
3. 启动后端：

```bash
cd nepserver/nepserver
mvn spring-boot:run
```

4. 分别启动三个前端：

```bash
cd "前端/neps（公众监督员）"
npm install
npm run dev
```

```bash
cd "前端/nepm（管理员）"
npm install
npm run dev
```

```bash
cd "前端/nepg（网格员端）"
npm install
npm run dev
```

## Git 使用

本仓库已忽略 `node_modules`、`dist`、`target`、运行日志和本地数据库文件。修改后常用命令：

```bash
git status
git add .
git commit -m "描述本次修改"
git push
```
