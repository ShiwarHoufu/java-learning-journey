# java-learning-journey
### 个人 Java 学习仓库，记录练习代码和笔记。

> 学习资源：黑马程序员B站视频 [Java](https://www.bilibili.com/video/BV1TJxCzSEEZ/?spm_id_from=333.788.video.desc.click)
> [JavaWeb + AI](https://www.bilibili.com/video/BV1yGydYEE3H/)


## 目录主要结构

```
java-learning-journey/
├── java-basics/              # Java 基础练习
├── web-ai-project01/         # JavaWeb 入门阶段练习
├── web-ai-project02/         # 综合案例：Tlias智能学习辅助系统
│   └── tilas-web-management/ # Spring Boot + MyBatis + MySQL
└── notes/                    # 学习笔记（迁移至独立仓库）
```

每个子目录都是相互独立的 Maven 工程，需要分别导入 IDEA 打开运行。

## 本地运行

以 `web-ai-project02/tilas-web-management` 为例：

1. 本地启动MySQL建表

   项目 `sql/` 目录下若有建表脚本，执行即可建库。

2. 从一个 `.env` 文件读取数据库账号密码

   仓库根目录提供了模板 `.env.example`，复制一份并填入自己的密码：

   `.env` 内容（这是 Java properties 格式，不是 shell 格式：不加引号、不写 `export`）：

   ```properties
   DB_USERNAME=root
   DB_PASSWORD=你的数据库密码
   ```

   `application.yml` 里靠 `spring.config.import` 把它加载进来：

   ```yaml
   spring:
     config:
       import:
         - optional:file:.env[.properties]
         - optional:file:../../.env[.properties]
     datasource:
       url: "${DB_URL:jdbc:mysql://localhost:3307/tilas}"
       username: "${DB_USERNAME:root}"
       password: "${DB_PASSWORD:}"
   ```

   > 注意：
   > - 不同模块默认连的库不同（`tilas` 和 `db01`），各自由 `application.yml` 里的默认值决定；

3. 运行 `TilasWebManagementApplication`


## 说明

本仓库代码均为**学习练习**，主要用于记录学习过程、方便日后回顾。代码中保留了大量的注释和多种实现方式，并不是生产级代码。
