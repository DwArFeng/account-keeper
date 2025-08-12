# ConfDirectory - 配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
│
├─settingrepo
│      background.properties
│      clean.properties
│      derive.properties
│      exception.properties
│      launcher.properties
│      login.properties
│      purge.properties
│      push.properties
│      register.properties
│      reset.properties
│
├─curator
│      connection.properties
│      latch-path.properties
│
├─database
│      connection.properties
│      performance.properties
│
├─datamark
│      settings.properties
│
├─dubbo
│      connection.properties
│
├─logging
│      README.md
│      settings.xml
│      settings-ref-linux.xml
│      settings-ref-windows.xml
│
├─redis
│      connection.properties
│      prefix.properties
│      timeout.properties
│
└─telqos
        connection.properties
```

鉴于大部分配置文件的配置项中都有详细地注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项，
省略的部分将会使用 `etc...` 进行标注。

## acckeeper 目录

| 文件名                   | 说明                           |
|-----------------------|------------------------------|
| background.properties | 后台服务配置文件，包括线程池的线程数及其它        |
| clean.properties      | 清理服务的配置文件                    |
| derive.properties     | 派生功能配置文件                     |
| exception.properties  | ServiceException 的异常代码的偏移量配置 |
| launcher.properties   | 启动器配置文件                      |
| login.properties      | 登录功能配置文件                     |
| purge.properties      | 清除服务配置文件                     |
| push.properties       | 推送服务配置文件                     |
| register.properties   | 注册服务配置文件                     |
| reset.properties      | 重置服务配置文件                     |

### background.properties

后台服务配置文件，包括线程池的线程数及其它。

```properties
# 任务执行器的线程池数量范围。
executor.pool_size=50-75
# 任务执行器的队列容量。
executor.queue_capacity=100
# 任务执行器的保活时间（秒）。
executor.keep_alive=120
# 计划执行器的线程池数量范围。
scheduler.pool_size=10
```

### clean.properties

清理服务的配置文件。

```properties
# 过期登录状态清理的 CRON 表达式
clean.expired_login_state.cron=0 15 0/1 * * *
```

本服务将会在后台定时清理过期的登录状态，即按照一定的逻辑清理被认定为过期的 `LoginState` 实体。

本服务定时清理任务的执行周期（Cron 表达式）由 `clean.expired_login_state.cron` 参数决定。

### derive.properties

派生功能配置文件。

```properties
# 动态派生的过期时长。
acckeeper.derive.dynamic.expire_duration=600000
```

动态派生是指派生请求成功后，返回一个过期时间较短的登录状态。

动态派生的过期时间由 `acckeeper.derive.dynamic.expire_duration` 参数决定。

### launcher.properties

启动器配置文件，决定了启动时的一些行为。

```properties
# 程序启动完成后，是否重置保护器支持。
launcher.reset_protector_support=true
#
# 程序启动完成后，上线清理的延时时间。
# 有些数据仓库以及清理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即上线清理服务。
# 该参数小于0，意味着程序不主动上线清理服务，需要手动上线。
launcher.online_clean_delay=3000
# 程序启动完成后，启动清理的延时时间。
# 有些数据仓库以及清理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即启动清理服务。
# 该参数小于0，意味着程序不主动启动清理服务，需要手动启动。
launcher.enable_clean_delay=3500
#
# 程序启动完成后，启动重置的延时时间。
# 有些数据仓库以及重置器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即启动重置服务。
# 该参数小于0，意味着程序不主动启动重置服务，需要手动启动。
launcher.start_reset_delay=30000
#
# 程序启动完成后，上线清除的延时时间。
# 有些数据仓库以及清除器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即上线清除服务。
# 该参数小于0，意味着程序不主动上线清除服务，需要手动上线。
launcher.online_purge_delay=4000
# 程序启动完成后，启动清除的延时时间。
# 有些数据仓库以及清除器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即启动清除服务。
# 该参数小于0，意味着程序不主动启动清除服务，需要手动启动。
launcher.enable_purge_delay=4500

```

该配置文件决定了服务被运行后，哪些功能将会自动被执行。

对于负载巨大场景，需要服务集群做读写分离，一部分服务在启动后自动执行数据业务并下线微服务（下线微服务当前还未自动化），
专注与业务的处理； 另一部分则不执行数据业务并上线微服务，专注于响应调用方。

自动执行是可选的配置功能，任何启动时没有自动执行的功能模块，均可以通过服务的 telqos 系统随时进行启用。

### login.properties

登录功能配置文件。

```properties
# 动态登录的过期时长。
acckeeper.login.dynamic.expire_duration=600000
```

动态登录是指登录请求成功后，返回一个过期时间较短的登录状态。

动态登录的过期时间由 `acckeeper.login.dynamic.expire_duration` 参数决定。

### purge.properties

清除服务配置文件。

```properties
# 清除任务的保留时长（毫秒）。
# 发生日期距离当前系统日期超过该时长的数据将被清除。
# 如果设置为 0 或负数，清除计划将不启动。
purge.retention_duration=17280000000
# 清除任务的执行周期（Cron 表达式）。
purge.task_cron=0 0 2 * * ?
# 清除任务的最大分页大小。
# 每次查询待清除数据时的最大数量。
purge.max_page_size=1000
# 清除任务的最大删除数量。
# 单次清除任务最多删除的数据条数。
purge.max_deletion_size=10000
```

清除服务用于定时清除产生时间过久的历史数据，是保证服务数据量不持续增加的机制。

历史数据仅用于取证与展示，清除它们本身没有副作用。

清除服务将会按照先后顺序对以下实体进行清除：

1. 派生历史，即 `DeriveHistory`。
2. 登录历史，即 `LoginHistory`。

配置项 `purge.retention_duration` 用于指定清除任务的保留时长，是清除任务中最重要的配置项。关于此配置，有一下原则可以参考：

- 总体而言，可以首先将此值配置为 `0` 或负数，使历史记录无限期保留，在后续的使用中，逐步探索保留的边界，
  例如经过一段时间的使用，用户只查询 3 个月内的历史数据，则可将此值调整为 `8640000000`，即 100 天。
- 如果系统有等保需求，那么历史记录必须保留 6 个月以上，可以将此配置设置为 `17280000000`，即 200 天。
- 如果对于特别重要的系统，那么历史记录必须无限期保留，必须将此值配置为 `0` 或负数。

配置项 `purge.task_cron` 用于设置清除任务的执行周期（Cron 表达式），通常是一天执行一次，
选择服务压力较小的时刻执行清除任务即可。

为了提升清除服务的性能，清除服务分批查数，并分批清除。

配置项 `purge.max_page_size` 用于指定清除服务分批清除时，每批数据的数量，可根据数据库性能及服务器的性能综合决定此值。

配置项 `purge.max_deletion_size` 用于指定单次任务最多删除的数据条数，此值可保证每次任务执行时，
都会在一定的时间内结束完成，不会因为某次需要删除的数据量过大而长时间执行，以至于影响正常业务。

使用者可以统计一段时间内平均每天的历史数据的生成量（`DeriveHistory` 和 `LoginHistory` 生成数量取最大值），
并乘以一个安全系数（如 `1.2`），以此决定 `purge.max_deletion_size` 配置项的值。

### push.properties

推送服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的推送器类型。
# 目前该项目支持的推送器类型有:
#   drain: 简单的丢弃掉所有消息的推送器。
#   multi: 同时将消息推送给所有代理的多重推送器。
#   log: 将时间格式化后打印至日志中的推送器。
#
# 对于一个具体的项目，很可能只用一个推送器。此时希望加载
# 推送器时只加载需要的那个，其余的推送器不加载。这个需求
# 可以通过编辑 application-context-scan.xml 实现。
pusher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain推送器没有任何配置。
#
###################################################
#                      multi                      #
###################################################
# 代理的推送器，推送器之间以逗号分隔。
pusher.multi.delegate_types=drain
#
###################################################
#                       log                       #
###################################################
# 记录日志的等级，由低到高依次是 TRACE, DEBUG, INFO, WARN, ERROR。
pusher.log.log_level=INFO
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-pusher.xml`，决定项目中需要使用哪些推送器。您只需要修改使用的推送器的配置。

### register.properties

注册服务配置文件。

```properties
# 用户注册时加密密码时盐生成的复杂度，值越高，安全性越强，但是速度越慢。最高为30。
register.password.salt_log_rounds=10
# 用户注册时使用的默认保护器类型。
register.default_protector.type=do_nothing_protector
# 用户注册时使用的默认保护器参数。
register.default_protector.param=
```

配置项 `register.password.salt_log_rounds` 用于指定用户名密码加密时盐生成的复杂度。

新用户注册时，用户密码作为敏感数据，是需要进行加密存储的。

本服务使用 `org.mindrot:jbcrypt` 作为密码的加密工具，该工具作为密码加密领域的成熟工具，具备广泛的认可度。

> jBCrypt is an implementation the OpenBSD Blowfish password hashing algorithm, as described in "A Future-Adaptable
> Password Scheme" by Niels Provos and David Mazieres.

配置项 `register.password.salt_log_rounds` 是 `org.mindrot:jbcrypt` 加密的参数，有关该参数的猫叔如下：

> The log2 of the number of rounds of hashing to apply - the work factor therefore increases as 2**log_rounds.

配置项 `register.default_protector.type` 和 `register.default_protector.param` 是用户默认保护器的相关配置，
需要根据业务需求，和具体保护器的参数配置决定。

### reset.properties

重置服务配置文件。

```properties
###################################################
#                      never                      #
###################################################
# Never 推送器没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# 重置的间隔。
resetter.fixed_delay.delay=43200000
#
###################################################
#                   fixed_rate                    #
###################################################
# 重置的间隔。
resetter.fixed_rate.rate=43200000
#
###################################################
#                      cron                       #
###################################################
# 执行重置的 CRON 表达式。
resetter.cron.cron=0 0 1 * * *
#
###################################################
#                      dubbo                      #
###################################################
# Dubbo 推送器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-resetter.xml`，决定项目中需要使用哪些重置器。您只需要修改使用的重置器的配置。

## curator 目录

| 文件名                   | 说明            |
|-----------------------|---------------|
| connection.properties | Curator 连接配置  |
| latch-path.properties | Curator 互斥锁路径 |

### connection.properties

Curator 连接配置。

```properties
# 连接字符，即 zookeeper 地址。
curator.connect.connect_string=your-host-here:2181
# 会话超时时间。
curator.connect.session_timeout=60000
# 连接超时时间。
curator.connect.connection_timeout=15000
# 第一次重试时的间隔时间，每重试一次，间隔时间都会指数增加，直到最大的间隔时间。
curator.retry_policy.base_sleep_time=1000
# 最大重试次数。
curator.retry_policy.max_retries=10
# 单次重试最大的间隔时间。
curator.retry_policy.max_sleep=60000
```

Curator 连接配置文件，包括 Zookeeper 连接地址，超时时间，重试策略。

### latch-path.properties

Curator 互斥锁路径。

```properties
# 清理服务的领导者锁存的路径。
curator.latch_path.clean.leader_latch=/acckeeper/clean/leader_latch
# 清除服务的领导者锁存的路径。
curator.latch_path.purge.leader_latch=/acckeeper/purge/leader_latch
```

如果您在本机上部署了多个项目，每个项目中都使用本服务，那么需要为每个项目配置不同的互斥锁路径，以避免项目之间不必要的互斥。

## database 目录

| 文件名                    | 说明        |
|------------------------|-----------|
| connection.properties  | 数据库连接配置文件 |
| performance.properties | 数据库性能配置文件 |

### connection.properties

数据库连接配置文件，除了标准的数据库配置四要素之外，还包括 Hibernate 的方言配置。

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://your-host-here:3306/acckeeper?serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=root
jdbc.password=your-password-here
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### performance.properties

数据库性能配置文件，使用默认值即可，或按照实际情况进行修改。

```properties
# 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
hibernate.jdbc.batch_size=100
# 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
hibernate.jdbc.fetch_size=50
# 连接池最大活动连接数量
data_source.max_active=20
# 连接池最小空闲连接数量
data_source.min_idle=0
```

## datamark 目录

| 文件名                 | 说明        |
|---------------------|-----------|
| settings.properties | 数据标记的配置文件 |

### settings.properties

数据标记的配置文件。

数据标记是本项目的一个运维与安全机制，它使用 `dwarfeng-datamark` 实现，其主要的功能是在重要数据插入/更改时，
向数据库特定的数据标记字段写入特定值，
这个特定值被记录在 `dwarfeng-datamark` 中的 `resource` 中 - 可以是 spring 框架支持的任何资源类型，
支持运行时修改，并对前端完全不可见。

运维人员可以用这个机制降低运维的工作量 - 尤其是从测试环境向正式环境迁移数据时，也可以用这个机制进行数据非法篡改的检测与取证。

```properties
#---------------------------------配置说明----------------------------------------
# 数据标记资源的 URL，格式参考 Spring 资源路径。
# datamark.xxx.resource_url=classpath:datamark/default.storage
# 数据标记资源的字符集。
# datamark.xxx.resource_charset=UTF-8
# 数据标记服务是否允许更新。
# datamark.xxx.update_allowed=true
#
#---------------------------------User----------------------------------------
# etc...
#
#---------------------------------Protector----------------------------------------
# etc...
```

## dubbo 目录

| 文件名                   | 说明           |
|-----------------------|--------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件。

```properties
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
dubbo.registry.zookeeper.timeout=3000
dubbo.protocol.dubbo.port=20000
dubbo.protocol.dubbo.host=your-host-here
dubbo.provider.group=
dubbo.consumer.snowflake.group=
```

其中，`dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 FDR 实例，那么需要为每个实例配置不同的 `dubbo.protocol.dubbo.port`。

如果您在本机上部署了多个项目，每个项目中都使用了 FDR，那么需要为每个项目配置不同的 `dubbo.provider.group`，
以避免微服务错误的调用。

## logging 目录

| 文件名                      | 说明                     |
|--------------------------|------------------------|
| README.md                | 说明文件                   |
| settings.xml             | 日志配置的配置文件              |
| settings-ref-linux.xml   | Linux 系统中日志配置的配置参考文件   |
| settings-ref-windows.xml | Windows 系统中日志配置的配置参考文件 |

### settings.xml

日志配置及其参考文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!-- etc... -->
    </Appenders>

    <Loggers>
        <!-- etc... -->
    </Loggers>
</Configuration>
```

需要注意的是，日志配置 **必须** 定义在 `settings.xml` 中才能生效，所有的 `settings-ref-xxx.xml` 都是参考文件，
在这些文件中进行任何配置的修改 **均不会生效**。

常用的做法是，针对不同的操作系统，将参考文件中的内容直接复制到 `settings.xml` 中，随后对 `settings.xml` 中的内容进行修改。

- 如果服务运行一天产生的日志超过了配置上限，可上调 `rolling_file.rollover.max` 参数。
- 如果存在等保需求，日志至少需要保留 6 个月，需要调整 `rolling_file.rollover.delete_age` 参数至 `200D`。

## redis 目录

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |
| prefix.properties     | 前缀配置 |
| timeout.properties    | 超时配置 |

### connection.properties

Redis 连接配置文件。

```properties
# ip地址
redis.hostName=your-host-here
# 端口号
redis.port=6379
# 如果有密码
redis.password=your-password-here
# etc...
```

### prefix.properties

Redis 前缀配置文件。

```properties
#------------------------------------------------------------------------------------
#  缓存时实体的键的格式
#------------------------------------------------------------------------------------
# 用户对象的主键格式。
cache.prefix.entity.account=entity.account.
# etc...
```

Redis 利用该配置文件，为缓存的主键添加前缀，以示区分。

如果您的项目包含其它使用 Redis 的模块，您可以修改该配置文件，以避免不同项目的同名实体前缀冲突，相互覆盖。

一个典型的前缀更改方式是在前缀的头部添加项目的名称，如：

```properties
# 用户对象的主键格式。
cache.prefix.entity.account=entity.acckeeper.account.
# etc...
```

### timeout.properties

Redis 缓存的超时配置文件。

```properties
#------------------------------------------------------------------------------------
#  实体缓存时的超时时间
#------------------------------------------------------------------------------------
# 用户对象缓存的超时时间。
cache.timeout.entity.account=3600000
# etc...
```

如果您希望缓存更快或更慢地过期，您可以修改该配置文件。

## telqos 目录

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |

### connection.properties

Telqos 连接配置文件。

```properties
# Telnet 端口。
telqos.port=23
# 字符集。
telqos.charset=UTF-8
# 白名单表达式。
telqos.whitelist_regex=
# 黑名单表达式。
telqos.blacklist_regex=
```

如果您的项目中有多个包含 Telqos 模块的服务，您应该修改 `telqos.port` 的值，以避免端口冲突。

请根据操作系统的默认字符集，修改 `telqos.charset` 的值，以避免乱码。一般情况下，Windows 系统的默认字符集为 `GBK`，
Linux 系统的默认字符集为 `UTF-8`。

如果您希望限制 Telqos 的使用范围，您可以修改 `telqos.whitelist_regex` 和 `telqos.blacklist_regex` 的值。
