# Quick Start - 快速开始

## 确认系统需求

- CPU：2 核以上。
- 内存：4G 以上。
- 硬盘：100G 以上。
- CentOS 7。
- JRE 1.8。
- 任意关系型数据库，如：MySQL、Oracle、SQL Server 等。
- Redis 5.0.7。
- Zookeeper 3.5.5。
- snowflake-distributed-service 1.6.4.a。

## 获取软件包

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

## 解压软件包

交付用的发布包汇总到 `account-keeper-distribute` 模块下的 `target/distribute/` 目录中， 按节点分子目录存放；
功能、构型与制品路径说明见 [CompileBySource.md](./CompileBySource.md)。

以节点 `acckeeper-all-he` 为例， 软件包的名称格式为 `acckeeper-all-he-${version}-release.tar.gz`，
其中 `${version}` 为软件包的版本号。
若使用其它功能与构型组合的节点，压缩包及解压目录名亦遵循相同规则。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf acckeeper-all-he-${version}-release.tar.gz
mv acckeeper-all-he-${version}-release acckeeper
```

## 最小化配置

下文列出了启动程序需要改动的最少的配置文件，每个配置文件中仅展示需要改动的配置项。
在下文中，每个配置项之前的注释内容为如何修改该配置项，请参考注释内容修改该对应位置的配置文件中的内容（无需将注释粘贴到配置文件之中）。

`conf/database/connection.properties` 文件中配置数据库连接信息。

```properties
# 改为数据库连接的驱动类名。
com.dwarfeng.acckeeper.jdbc.driver=com.mysql.cj.jdbc.Driver
# 改为数据库连接的 url。
com.dwarfeng.acckeeper.jdbc.url=\
  jdbc:mysql://your-host-here:3306/acckeeper?serverTimezone=Asia/Shanghai&autoReconnect=true
# 改为数据库连接的用户名。
com.dwarfeng.acckeeper.jdbc.username=root
# 改为数据库连接的密码。
com.dwarfeng.acckeeper.jdbc.password=your-password-here
# 改为数据库连接的 Hibernate 方言。
com.dwarfeng.acckeeper.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

`conf/dubbo/connection.properties` 文件中配置 Dubbo 连接信息。

```properties
# 改为服务连接的 zookeeper 的地址。
com.dwarfeng.acckeeper.dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
# 改为服务所在主机的 ip 地址。
com.dwarfeng.acckeeper.dubbo.protocol.dubbo.host=your-host-here
```

`conf/redis/connection.properties` 文件中配置 Redis 连接信息。

```properties
# 改为 Redis 连接的主机地址。
com.dwarfeng.acckeeper.redis.hostName=your-host-here
# 改为 Redis 连接的端口号。
com.dwarfeng.acckeeper.redis.port=6379
# 改为 Redis 连接的密码。
com.dwarfeng.acckeeper.redis.password=your-password-here
```

`conf/telqos/connection.properties` 文件中配置 Telqos 服务信息。

```properties
# 改为服务所在主机的未被占用的任意端口号。
com.dwarfeng.acckeeper.telqos.port=23
```

`opt/opt-lskgen.xml` 文件中配置登录状态主键生成器。

```xml
<!-- 放开需要使用的登录状态主键生成器。 -->
<context:include-filter
        type="assignable"
        expression="com.dwarfeng.acckeeper.impl.handler.lskgen.UuidLoginStateKeyGenerator"
/>
```

## 启动程序

在 `/usr/local/acckeeper` 目录下执行如下命令：

```shell
sh  bin/acckeeper-start.sh
```

可以通过以下行为观察服务的运行效果：

1. 进入服务的 telqos 运维系统并观察。

   在终端输入指令：

   ```shell
   # 将该命令中的 ${telqos.port} 替换为配置的端口号。
   telnet localhost ${telqos.port}
   ```

   观察 telqos 运维系统，您可以输入

   ```shell
   lc
   ```

   观察所有可用的指令。

2. 观察 account-keeper 运维指令。

   在 telqos 运维系统中输入指令：

   ```shell
   account
   ```

   观察服务的输出。

## 停止程序

在 `/usr/local/acckeeper` 目录下执行如下命令：

```shell
sh  bin/acckeeper-stop.sh
```

## 参阅

- [Compile by Source](./CompileBySource.md) - 源码编译，详细说明如何从源码编译本项目。
