D:\学习\Spring Boot\瑞吉外卖管理、用户端\lys01\README.md
# 瑞吉外卖管理系统 (lys01)

基于 Spring Boot 开发的在线外卖点餐系统，包含管理端和用户端两个平台。提供菜品管理、订单处理、用户管理等完整的外卖业务功能。

## 📋 项目简介

本项目是一个完整的外卖点餐解决方案，采用前后端分离架构，支持：
- **管理端**：员工管理、菜品管理、套餐管理、分类管理、订单管理等
- **用户端**：用户登录、浏览菜品、添加购物车、下单支付、地址管理等

## 🛠️ 技术栈

### 后端技术
- **框架**: Spring Boot 4.0.5
- **JDK**: Java 17
- **持久层**: MyBatis-Plus 3.5.15
- **数据库**: MySQL
- **JSON处理**: Jackson
- **工具类**: Lombok
- **构建工具**: Maven

### 前端技术
- **管理端**: Vue.js + Element UI
- **用户端**: 原生 HTML/CSS/JavaScript

## 📁 项目结构
```text
src/main/java/com/example/lys01/
├── Lys01Application.java              # 启动类
├── Result/                            # 统一返回结果封装
│   └── R.java                         # 通用返回结果类
├── common/                            # 公共组件
│   ├── BaseContext.java               # ThreadLocal 上下文管理
│   ├── MyMetaObjecthandler.java       # MyBatis-Plus 元数据处理器
│   ├── SnowflakeIdGenerator.java      # 雪花算法 ID 生成器
│   └── ValidateCodeUtils.java         # 验证码生成工具
├── config/                            # 配置类
│   ├── FilterConfig.java              # 过滤器配置
│   └── WebMvcConfig.java              # Web MVC 配置
├── controller/                        # 控制器层
│   ├── AddressBookController.java     # 地址簿管理
│   ├── CategoryController.java        # 分类管理
│   ├── CommonController.java          # 通用接口
│   ├── DishController.java            # 菜品管理
│   ├── EmployeeController.java        # 员工管理
│   ├── OrdersController.java          # 订单管理
│   ├── SetmealController.java         # 套餐管理
│   ├── ShoppingCartController.java    # 购物车管理
│   └── UserController.java            # 用户管理
├── entry/                             # 实体类
│   ├── AddressBook.java               # 地址簿实体
│   ├── Category.java                  # 分类实体
│   ├── Dish.java                      # 菜品实体
│   ├── DishFlavor.java                # 菜品口味实体
│   ├── Employee.java                  # 员工实体
│   ├── OrderDetail.java               # 订单明细实体
│   ├── Orders.java                    # 订单实体
│   ├── Setmeal.java                   # 套餐实体
│   ├── SetmealDish.java               # 套餐菜品关系实体
│   ├── ShoppingCart.java              # 购物车实体
│   └── User.java                      # 用户实体
├── filter/                            # 过滤器
│   └── EmployeeLoginCheck.java        # 员工登录验证过滤器
├── mapper/                            # Mapper 接口层
│   ├── AddressBookMapper.java
│   ├── CategoryMapper.java
│   ├── DishFlavorMapper.java
│   ├── DishMapper.java
│   ├── EmployeeMapper.java
│   ├── OrdersDetailMapper.java
│   ├── OrdersMapper.java
│   ├── SetmealDishMapper.java
│   ├── SetmealMapper.java
│   ├── ShoppingCartMapper.java
│   └── UserMapper.java
└── service/                           # 服务层接口
└── impl/                          # 服务层实现 (建议将 imp 改为 impl)
```
## 🚀 功能模块

### 管理端功能
1. **员工管理**
   - 员工登录/退出
   - 员工信息的增删改查
   - 员工状态管理（启用/禁用）

2. **分类管理**
   - 菜品分类的增删改查
   - 套餐分类的增删改查

3. **菜品管理**
   - 菜品的增删改查
   - 菜品口味设置
   - 菜品上下架管理
   - 图片上传

4. **套餐管理**
   - 套餐的增删改查
   - 套餐关联菜品
   - 套餐上下架管理

5. **订单管理**
   - 订单查询
   - 订单详情查看
   - 订单状态管理（派送、完成、取消等）

### 用户端功能
1. **用户管理**
   - 手机号登录
   - 短信验证码发送
   - 新用户自动注册

2. **地址管理**
   - 收货地址的增删改查
   - 默认地址设置

3. **菜品浏览**
   - 按分类浏览菜品
   - 查看菜品详情和口味

4. **购物车**
   - 添加/删除商品
   - 修改商品数量
   - 清空购物车

5. **订单管理**
   - 提交订单
   - 查看历史订单
   - 订单评价

## ⚙️ 环境要求

- **JDK**: 17 或更高版本
- **Maven**: 3.6+
- **MySQL**: 5.7+ 或 8.0+
- **IDE**: IntelliJ IDEA（推荐）

## 📦 安装与部署

### 1. 克隆项目
bash 
git clone <repository-url> 
cd lys01

### 2. 数据库配置

创建 MySQL 数据库 `web02`，并导入相应的 SQL 脚本（需自行准备）。

修改配置文件 `src/main/resources/application.yml`：

yaml
spring:
datasource:
# 数据库连接地址及参数说明：
# useUnicode=true&characterEncoding=utf8 : 解决中文乱码问题
# useSSL=false : 关闭 SSL 验证，避免本地开发时的警告
# serverTimezone=Asia/Shanghai : 设置服务器时区为东八区，防止时间差问题
# allowPublicKeyRetrieval=true : 允许客户端获取公钥（MySQL 8.0+ 推荐开启）
url: jdbc:mysql://localhost:3306/web02?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
username: root
password: root  # 建议修改为你本地的实际数据库密码

### 3. 配置文件说明

主要配置项：
- **server.port**: 应用端口，默认 8080
- **filepath.path**: 文件上传路径，默认 `d:/upload/`
- **mybatis-plus.configuration.map-underscore-to-camel-case**: 开启驼峰命名映射
- **mybatis-plus.configuration.log-impl**: SQL 日志输出

### 4. 编译运行

使用 Maven 编译：
bash 
mvn clean package

运行应用：
bash 
mvn spring-boot:run

或直接运行主类 `Lys01Application.java`

### 5. 访问应用

- **管理端**: http://localhost:8080/backend/index.html
- **用户端**: http://localhost:8080/front/index.html

## 🔐 默认账号

- **管理员账号**: admin
- **默认密码**: 123456

## 📝 API 接口说明

### 员工管理接口
- `POST /employee/login` - 员工登录
- `POST /employee/logout` - 退出登录
- `GET /employee/page` - 分页查询员工
- `POST /employee` - 新增员工
- `PUT /employee/status` - 修改员工状态
- `PUT /employee` - 修改员工信息
- `GET /employee/{id}` - 根据ID查询员工

### 用户管理接口
- `POST /user/sendMsg` - 发送短信验证码
- `POST /user/login` - 用户登录

### 菜品管理接口
- `GET /dish/page` - 分页查询菜品
- `POST /dish` - 新增菜品
- `PUT /dish` - 修改菜品
- `DELETE /dish` - 删除菜品
- `GET /dish/{id}` - 根据ID查询菜品

### 订单管理接口
- `GET /orders/page` - 分页查询订单
- `POST /orders/submit` - 提交订单
- `PUT /orders` - 修改订单状态

## 🗂️ 数据库表说明

主要数据表：
- `employee` - 员工表
- `user` - 用户表
- `category` - 分类表
- `dish` - 菜品表
- `dish_flavor` - 菜品口味表
- `setmeal` - 套餐表
- `setmeal_dish` - 套餐菜品关系表
- `orders` - 订单表
- `order_detail` - 订单明细表
- `shopping_cart` - 购物车表
- `address_book` - 地址簿表

## 🔧 开发说明

### 统一返回格式

所有接口返回统一的 `R<T>` 对象：
java 
{ 
"code": 1, // 1:成功, 0:失败 
"msg": "success", 
"data": {} // 返回数据 
}
### 登录验证

- 管理端：基于 Session 的员工登录验证
- 用户端：基于手机号和验证码的登录方式

### ID 生成策略

使用雪花算法（Snowflake）生成分布式唯一 ID。

### 文件上传

文件上传路径配置在 `application.yml` 中，默认为 `d:/upload/`。

## 🐛 常见问题

1. **数据库连接失败**
   - 检查 MySQL 服务是否启动
   - 确认数据库用户名和密码是否正确
   - 确认数据库 `web02` 已创建

2. **端口冲突**
   - 修改 `application.yml` 中的 `server.port`

3. **文件上传失败**
   - 确保配置的上传路径存在
   - 检查文件夹权限

## 📄 许可证

本项目仅供学习交流使用。

## 👨‍💻 作者

作者：lys

---

**注意**: 本项目为学习项目，用于 Spring Boot 学习和实践。
