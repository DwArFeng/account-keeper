# account-keeper

Account Keeper (简称 Acckeeper) 是一个功能全面的账户管理服务，主要提供了账户的注册销毁、登陆登出等操作。

---

## 特性

- 支持账户注册、销毁、登陆、登出等操作。
- 账户密码加密存储。
- 支持账户保护器，如密码错误次数限制保护，保护器可基于 SPI 进行扩展。
- 支持动态登录（短期登录+延迟过期方法）和静态登录（长期登录）。
- 支持登录状态的派生（基于已有的登录状态派生新的登录状态）。
- 支持登录状态的指令（如登录状态的查询、销毁），可方便地销毁特定账户的所有登录状态。
- 提供 Telqos 运维平台，能够在没有 GUI 的环境下使用本服务的功能。
- 登录和派生操作进行历史记录，可追踪与登录状态相关的所有操作。
- 支持主流关系型数据库（基于 Hibernate）。
- 支持分布式部署。

## 机制

### 账户

账户是 Acckeeper 的核心对象，它用于存储用户的基本信息，如用户名、密码、注册时间等；同时，它还用于存储用户状态信息，
如 序列版本号、登录计数、派生计数等。

账户拥有使能状态，可以禁用账户，禁用后，账户将无法登录，同时会清除该账户当前的所有登录状态。

由于该服务着重维护账户的登录等状态，因此账户除了拥有最常见的 `displayName` 和 `remark` 等显示信息外，没有其它过多的信息。
如果需要存储更多的账户信息，可以使用其它服务进行存储。

账户的密码是加密存储的，使用了 `BCrypt` 算法进行加密，可以调整 `conf/acckeeper/register.properties` 中的参数调整加密强度。

```properties
# 用户注册时加密密码时盐生成的复杂度，值越高，安全性越强，但是速度越慢。最高为30。
register.password.salt_log_rounds=10
```

## 安装说明

1. 下载源码

   使用git进行源码下载。
   ```
   git clone git@github.com:DwArFeng/account-keeper.git
   ```
   对于中国用户，可以使用gitee进行高速下载。
   ```
   git clone git@gitee.com:dwarfeng/account-keeper.git
   ```
   
2. 项目打包

   进入项目根目录，执行maven命令
   ```
   mvn clean package
   ```
   
3. 解压

   找到打包后的目标文件 
   ```
   account-keeper-node/target/acckeeper-[version]-release.tar.gz
   ```
   将其解压至windows系统或者linux系统
   
4. 配置

   1. 进入工程下的`bin`目录，修改所有执行脚本的`basedir`和`logdir`
      
   2. 修改conf文件夹下的配置文件，着重修改各连接的url与密码。
   
5. enjoy it

---

## 分布式说明

该项目使用`dubbo`作为RPC框架，本身支持分布式，您可以在实际使用时，部署该项目任意数量，以进行分布式运算。

---
