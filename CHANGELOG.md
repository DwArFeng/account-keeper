# ChangeLog

### Release_1.8.2_20250608_build_A

#### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/UsingTelqos.md。

- 依赖升级。
  - 升级 `snowflake` 依赖版本为 `1.6.4.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.10.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `1.0.2.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.1_20250512_build_A

#### 功能构建

- 部分代码注释优化。
  - com.dwarfeng.acckeeper.impl.bean.BeanMapper。
  - com.dwarfeng.acckeeper.sdk.bean.BeanMapper。

- Wiki 编写。
  - docs/wiki/zh_CN/ShellScripts.md。
  - docs/wiki/zh_CN/BatchScripts.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh_CN/Introduction.md。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.0_20250511_build_A

#### 功能构建

- 优化项目的启停脚本，以规避潜在的路径问题。
  - binres/acckeeper-start.sh。
  - binres/acckeeper-stop.sh。

- 为部分重置器的依赖注入对象增加限定符，以避免潜在的冲突。
  - com.dwarfeng.acckeeper.impl.handler.resetter.DubboResetter。

- 为部分工具类中方法的入口参数增加 `@Nonnull` 注解。
  - com.dwarfeng.acckeeper.impl.service.telqos.CommandUtil。

- 优化实体映射器机制。

- 优化部分类中部分方法的行为分析行为。
  - com.dwarfeng.acckeeper.impl.cache.AccountCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.DeriveHistoryCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.LoginHistoryCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.LoginStateCacheImpl。
  - com.dwarfeng.acckeeper.impl.dao.AccountDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.DeriveHistoryDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.LoginHistoryDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.LoginStateDaoImpl。
  - com.dwarfeng.acckeeper.impl.handler.LoginHandlerImpl。
  - com.dwarfeng.acckeeper.impl.service.AccountMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.DeriveHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginParamRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginStateMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectDetailRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorInfoMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorSupportMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorVariableMaintainServiceImpl。

- Wiki 编写。
  - docs/wiki/zh_CN/VersionBlacklist.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh_CN/Introduction.md。

- 优化部分单元测试代码，以规避潜在的 bug。
  - com.dwarfeng.acckeeper.impl.service.AccountOperateServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.AccountServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.DeriveHistoryServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.LoginHistoryServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.LoginParamRecordMaintainServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.LoginServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.LoginStateServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.ProtectorInfoMaintainServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.ProtectorSupportMaintainServiceImplTest。
  - com.dwarfeng.acckeeper.impl.service.ProtectorVariableMaintainServiceImplTest。

- 导入运维指令。
  - com.dwarfeng.datamark.service.telqos.*。

- 增加 Hibernate 实体数据标记字段，并应用相关实体侦听器。
  - com.dwarfeng.acckeeper.impl.bean.entity.HibernateAccount。
  - com.dwarfeng.acckeeper.impl.bean.entity.HibernateProtectorInfo。

- 增加依赖。
  - 增加依赖 `dwarfeng-datamark` 以应用其新功能，版本为 `1.0.1.a`。

- SPI 目录结构优化。
  - 将驱动机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将执行机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将推送机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将重置机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.39` 以规避漏洞。
  - 升级 `protobuf` 依赖版本为 `3.25.5` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.119.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.3` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.6.3.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.9.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.14.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.13.a` 以规避漏洞。

#### Bug修复

- 修正 `impl` 模块中错误的 dubbo 应用名称。

#### 功能移除

- (无)

---

### Release_1.7.4_20240801_build_A

#### 功能构建

- 优化部分类中的日志文本。
  - com.dwarfeng.acckeeper.impl.handler.AccountOperateHandlerImpl。

- 新增运维指令。
  - com.dwarfeng.acckeeper.impl.service.telqos.AccountCommand。

- 优化 `node` 模块部分服务启停脚本的注释。
  - binres/acckeeper-start.bat。
  - binres/acckeeper-start.sh。

- 部分 dubbo 消费者服务注册配置添加 `check="false"` 属性。
  - snowflakeGenerateService。

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.system.MemoryCommand。
  - com.dwarfeng.springtelqos.api.integration.log4j2.Log4j2Command。

- 优化项目启停脚本设置程序的根目录的方式。

- 优化启停脚本的目录结构。

- 优化部分维护服务实现中的部分方法的性能。
  - com.dwarfeng.acckeeper.impl.service.AccountMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.DeriveHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginParamRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginStateMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectDetailRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorInfoMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorSupportMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorVariableMaintainServiceImpl。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.5.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring` 依赖版本为 `5.3.37` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.108.Final` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.13.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.9.a` 以规避漏洞。

- 优化部分类中部分方法的行为分析行为。
  - com.dwarfeng.acckeeper.impl.service.LoginStateMaintainServiceImpl。

#### Bug修复

- 修复部分 wiki 文档中的格式及语法问题。
  - zh_CN/Introduction.md。

- 修复 telqos 工具类中部分注解不正确的 bug。

- 修复部分功能启动延时为 0 时行为不正确的 bug。
  - 清理服务启动。

#### 功能移除

- (无)

---

### Release_1.7.3_20240407_build_A

#### 功能构建

- 启动器优化。
  - 将入口方法中完整独立的功能封装在子方法中，使入口方法代码结构更加清晰。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。
  - 优化日志配置结构，提供 `confext/logging-settings.xml` 配置文件，以供外部功能自定义日志配置。
  - 优化启动脚本，使服务支持新的日志配置结构。
  - 优化 `assembly.xml`，使项目打包时输出新的日志配置结构。
  - 优化 `confext/README.md`，添加新的日志配置结构的相关说明。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.0.a` 以规避漏洞。

#### Bug修复

- 修复部分 CrudOperation 中 `allExists` 方法行为异常的 bug。
  - com.dwarfeng.acckeeper.impl.service.operation.AccountCrudOperation。
  - com.dwarfeng.acckeeper.impl.service.operation.LoginHistoryCrudOperation。
  - com.dwarfeng.acckeeper.impl.service.operation.ProtectorInfoCrudOperation。

- 修复派生机制的 bug。
  - 修复派生过程中，登录状态的序列版本不能正确设置的 bug。

#### 功能移除

- (无)

---

### Release_1.7.2_20240212_build_A

#### 功能构建

- 优化预设保护器。
  - 优化 DoNothingProtector.execProtect 返回的信息，以消除歧义。

- 优化派生机制。
  - 动态派生过程增加事务支持。
  - 静态派生过程增加事务支持。

- Wiki 编写。
  - 构建 wiki 目录结构。
  - docs/wiki/en_US/Contents.md。
  - docs/wiki/en_US/Introduction.md。
  - docs/wiki/zh_CN/Contents.md。
  - docs/wiki/zh_CN/Introduction.md。

#### Bug修复

- 修复登录机制的 bug。
  - 修复登录不成功时，登录历史无法记录的 bug。

- 修复派生机制的 bug。
  - 修复登录状态在已过期的状态下，仍然可以派生的 bug。

#### 功能移除

- (无)

---

### Release_1.7.1_20240210_build_A

#### 功能构建

- 优化保护机制。
  - 增加账户元数据结构体字段 Protector.AccountMeta.deriveCount。

- 增加微服务注册。
  - com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.7.0_20240208_build_A

#### 功能构建

- 配置文件优化。
  - 优化 impl 模块下 `spring/application-context-placeholder.xml` 配置文件。
  - 优化 node 模块下 `spring/application-context-placeholder.xml` 配置文件。

- 优化清理机制。
  - 使用分布式锁管理清理作业，使得同一时刻下一个集群中只有一个节点执行清理作业。

- 文本、注释、文档优化。
  - 更正单词：登陆 -> 登录。
  - PushHandler.loginHistoryRecorded 文档注释优化。
  - Pusher.loginHistoryRecorded 文档注释优化。
  - LogPusher.loginHistoryRecorded 日志文案优化。

- 新增运维指令。
  - com.dwarfeng.acckeeper.impl.service.telqos.ProtectLocalCacheCommand。

- 新增派生机制。
  - 新增派生服务及其 QOS 服务。
  - 实现派生服务的基本功能。
  - 增加派生历史被记录系统时间及其推送功能。
  - 新增派生历史实体。
  - 账户新增字段，以适配派生机制。
  - 增加派生机制的 Telqos 指令。

- 新增实体。
  - com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory。

- 优化预设推送器。
  - 优化 LogPusher 的日志输出格式。

- 重做登录机制。
  - 增加动态登录机制。
  - 增加静态登录机制。
  - 废弃旧的登录机制。
  - 登录状态实体的数据访问层变为 Hibernate 实现。
  - 增加保护器上下文的接口方法，以适配新的登录机制。
  - 重做登录相关的 Telqos 指令。
  - 优化清理机制，以适配新的登录机制。
  - 更新 api 模块中的 subgrade 集成接口。
  - 登录历史新增字段，以适配新的登录机制。

#### Bug修复

- 部分预设 Criteria 制造器修正。
  - 修正 ProtectorVariablePresetCriteriaMaker.childForProtectorInfo 中的错误。

- 部分配置文件修正。
  - 修正 `spring/application-context-scan.xml` 中缺失的配置内容。

#### 功能移除

- (无)

---

### Release_1.6.6_20240128_build_A

#### 功能构建

- (无)

#### Bug修复

- 修复 Hibernate Key 缺失 `equals` 和 `hashCode` 方法的 bug。
  - com.dwarfeng.acckeeper.impl.bean.key.HibernateProtectorVariableKey。

#### 功能移除

- (无)

---

### Release_1.6.5_20240127_build_A

#### 功能构建

- 将工程中的 `Spring Bean` 注册方式尽可能地由 `@Autowired` 变更为构造器注入。

- 增加 `PusherAdapter`。
  - 建议任何插件的推送器实现都继承自该适配器。
  - 适配器对所有的事件推送方法都进行了空实现。
  - 解决了增加了新的事件时，旧的推送器实现必须实现新的方法的问题。
  - 从此以后，推送器增加新的事件，将被视作兼容性更新。

- 优化文件格式。
  - 优化 `*.properties` 文件的格式。

- 升级 spring-telqos 并应用其新功能。
  - 使用包扫描的方式注册指令。
  - 优化 `telqos/connection.properties` 中配置的键名。

- 为 dubbo 增加超时时间的配置选项。

- 依赖升级。
  - 升级 `snowflake` 依赖版本为 `1.5.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `subgrade` 依赖版本为 `1.4.8.b` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring` 依赖版本为 `5.3.31` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.2.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.104.final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.7.2` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.12.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.8.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.4_20230620_build_A

#### 功能构建

- 优化配置文件。
  - 优化 `application-context-database.xml`，使得更多属性可以在配置文件中配置。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.3_20230617_build_A

#### 功能构建

- 优化了 AbstractResetter 的代码结构，同时对其实现进行了修改。
  - com.dwarfeng.acckeeper.impl.handler.resetter.CronResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.NeverResetter。

- 依赖升级。
  - 升级 `dubbo` 依赖版本为 `2.7.22` 以规避漏洞。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。

#### Bug修复

- 修复 `DubboResetter` 注册微服务时没有指定 `group` 的问题。

#### 功能移除

- 删除不需要的依赖。
  - 删除 `aopalliance` 依赖。

---

### Release_1.6.2_20230421_build_A

#### 功能构建

- 优化 Mapper 接口的文件路径。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.11.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.6.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.1_20230420_build_A

#### 功能构建

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

- 增加注册账户时创建默认保护器的逻辑。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.26` 以规避漏洞。
  - 升级 `snakeyaml` 依赖版本为 `2.0.0` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.21` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.86.Final` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.2.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.0_20230120_build_A

#### 功能构建

- 增加重置机制，实现保护本地缓存的动态重置。
  - com.dwarfeng.acckeeper.impl.handler.resetter.AbstractResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.CronResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.acckeeper.impl.handler.resetter.NeverResetter。

- 实现推送机制，并开发预设推送器。
  - com.dwarfeng.acckeeper.impl.handler.pusher.AbstractPusher。
  - com.dwarfeng.acckeeper.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.acckeeper.impl.handler.pusher.LogPusher。
  - com.dwarfeng.acckeeper.impl.handler.pusher.MultiPusher。

- 实现保护机制，并开发预设保护器。
  - com.dwarfeng.acckeeper.impl.handler.protector.AbstractProtectorRegistry。
  - com.dwarfeng.acckeeper.impl.handler.protector.DoNothingProtectorRegistry。
  - com.dwarfeng.acckeeper.impl.handler.protector.PasswordProtectorRegistry。

- 实现运维指令。
  - com.dwarfeng.acckeeper.impl.service.telqos.LoginCommand。

- 新增实体。
  - com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory。
  - com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo。
  - com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport。
  - com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable。
  - com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord。
  - com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord。

- 新增字段。
  - Account.loginCount。
  - Account.passwordUpdateCount。

- 实现清理服务。
  - 自动清理过期的登录状态。

- 优化并规范 Telqos 指令实现代码。

- 规范 `LoginState` 实体的访问接口。

- 优化操作服务验证环节的代码结构。

- AccountMaintainService 增加批量增删改查功能。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.1_20230113_build_A

#### 功能构建

- 新增字段。
  - Account.registeredDate。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.0_20221129_build_A

#### 功能构建

- Dubbo 微服务增加分组配置。

- 代码健壮性增强。
  - com.dwarfeng.acckeeper.impl.service.telqos.StateCommand。
  - com.dwarfeng.acckeeper.impl.cache.LoginStateCacheImpl。

- 将依赖注入方式从注解注入改为构造器注入。

- 使用 `MapStruct` 重构 `BeanTransformer`。

- 增加依赖。
  - 增加依赖 `javassist` 以规避漏洞，版本为 `3.23.2-GA`。
  - 增加依赖 `protobuf` 以规避漏洞，版本为 `3.19.6`。
  - 增加依赖 `guava` 以规避漏洞，版本为 `31.1-jre`。
  - 增加依赖 `gson` 以规避漏洞，版本为 `2.8.9`。
  - 增加依赖 `snakeyaml` 以规避漏洞，版本为 `1.33`。

- 依赖升级。
  - 升级 `mysql` 依赖版本为 `8.0.31` 以规避漏洞。
  - 升级 `jedis` 依赖版本为 `3.8.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.5` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.5.7` 以规避漏洞。
  - 升级 `curator` 依赖版本为 `4.3.0` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.2.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.10.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.14.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.10.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.5.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的依赖。
  - 删除 `el` 依赖。
  - 删除 `zkclient` 依赖。
  - 删除 `commons-lang3` 依赖。
  - 删除 `hessian` 依赖。
  - 删除 `jetty` 依赖。
  - 删除 `dozer` 依赖。

---

### Release_1.4.4_20220912_build_A

#### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

- 依赖升级。
  - 升级 `spring-terminator` 依赖版本为 `1.0.9.a`。
  - 升级 `dutil` 依赖版本为 `beta-0.3.1.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.10.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.4.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.9.a` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.28` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.3_20220606_build_A

#### 功能构建

- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.20` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.27` 以规避漏洞。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.77.Final` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.0.21.Final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.17.2` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.2.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.4.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.7.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.7.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.3.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的依赖。
  - 删除 `joda-time` 依赖。
  - 删除 `pagehelper` 依赖。
  - 删除 `jsqlparser` 依赖。
  - 删除 `commons-fileupload` 依赖。

---

### Release_1.4.2_20220313_build_A

#### 功能构建

- 升级部分依赖的版本。
  - 升级 `log4j2` 版本至 `2.17.1`。
  - 升级 `hibernate` 版本至 `5.4.24.Final`。
  - 升级 `dubbo` 版本至 `2.7.15`。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.1_20211023_build_A

#### 功能构建

- 登录接口增加方法。
  - com.dwarfeng.acckeeper.stack.service.LoginService.getLoginState。

#### Bug修复

- 修正 `LoginServiceImpl` 中方法没有行为分析以及事务注解的 bug。

#### 功能移除

- (无)

---

### Release_1.4.0_20211022_build_A

#### 功能构建

- 账户、密码、登录相关接口完全重写。
  - com.dwarfeng.acckeeper.stack.service.AccountOperateService。
  - com.dwarfeng.acckeeper.stack.service.LoginService。

- 添加账户接口的预设查询。
  - com.dwarfeng.acckeeper.stack.service.AccountMaintainService.DISPLAY_NAME_LIKE。

- 移除项目中不需要的依赖。
  - `spring-web`。
  - `spring-mvc`。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.1_20211021_build_A

#### 功能构建

- 增加实体字段。
  - com.dwarfeng.acckeeper.stack.bean.entity.Account.displayName。

- 升级第三方依赖版本。
  - `hibernate-validator` 依赖版本升级至 `6.0.20.Final`。

- 去除无用的三方依赖。
  - httpclient。
  - httpmime。
  - httpcore。
  - solr-solrj。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.0_20201210_build_A

#### 功能构建

- 账户、密码、登录相关接口完全重写。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.1_20201209_build_A

#### 功能构建

- 优化 dubbo 的配置。
- 升级依赖版本。
  - druid 依赖版本升级至 1.1.20。
  - hibernate 依赖版本升级至 5.3.15.Final。
  - slf4j 依赖版本升级至 1.7.5。
  - subgrade 依赖版本升级至 1.2.0.b。
  - spring-terminator 依赖版本升级至 1.0.7.a。
  - spring-telqos 依赖版本升级至 1.1.1.a。
- 优化 redis 的配置。
- 消除预设配置文件中的真实的 ip 地址。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20201018_build_A

#### 功能构建

- 将配置项目 hibernate 更名为 database。
- 更新多个依赖项目的版本。
  - 更新依赖 spring-terminator 版本至 1.0.6.a。
  - 更新依赖 subgrade 版本至 1.1.2.a。
  - 更新依赖 dubbo 版本至 2.7.6。
- 引入 spring-telqos 框架。
  - 新增指令 com.dwarfeng.acckeeper.impl.service.telqos.KickCommand。
  - 新增指令 com.dwarfeng.acckeeper.impl.service.telqos.StateCommand。
- 将 application-context-task.xml 中的参数设置为可配置参数。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.7_20200512_build_A

#### 功能构建

- 完善@Transactional注解的回滚机制。
- 更改项目的打包名称。
- 更新README.md。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.6_20200410_build_B

#### 功能构建

- (无)

#### Bug修复

- 禁止node模块运行（通常是在正式使用）时输出sql语句。

#### 功能移除

- (无)

---

### Release_1.1.6_20200410_build_A

#### 功能构建

- 优化程序的启动脚本。
- 将程序的启动方式整合至spring-terminator。

#### Bug修复

- 修复com.dwarfeng.acckeeper.api.integration包错误的拼写。
- 修复项目编译打包过程中发生的报警。

#### 功能移除

- (无)

---

### Release_1.1.5_20200222_build_A

#### 功能构建

- 将RegisterServiceImplTest中用于测试的账号名称从root改为foo，避免在测试的时候误删除已经存在的root账号。
- 升级subgrade项目版本为beta-0.2.4.a。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.4_20200218_build_B

#### 功能构建

- (无)

#### Bug修复

- 优化实体服务的预设查询样式。

#### 功能移除

- (无)

---

### Release_1.1.4_20200218_build_A

#### 功能构建

- 为FastJson相关的对象添加 of 静态方法。

#### Bug修复

- 升级subgrade项目版本为beta-0.2.3.a以修复PagedData对象中字段拼写错误bug。

#### 功能移除

- (无)

---

### Release_1.1.3_20200216_build_B

#### 功能构建

- (无)

#### Bug修复

- 升级subgrade项目版本为beta-0.2.2.b以修复查询对象的总页数错误bug。

#### 功能移除

- (无)

---

### Release_1.1.3_20200216_build_A

#### 功能构建

- 添加JS修复的实体对象。
- 实现AccountMaintainService查询所有实体的功能。
- 实现AccountMaintainService实体预设查询的功能。
- 升级subgrade项目版本为beta-0.2.2.a。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.2_20200215_build_B

#### 功能构建

- (无)

#### Bug修复

- 升级subgrade至beta-0.2.1.a以避免bug。

#### 功能移除

- (无)

---

### Release_1.1.2_20200215_build_A

#### 功能构建

- 修复ExceptionCodeOffsetConfiguration配置中的错误。

#### Bug修复

- (无)

#### 功能移除

- ~~去除允许在程序在没有默认账户的情况下建立默认账户的功能~~

---

### Release_1.1.1_20200215_build_A

#### 功能构建

- 解决代码粘贴的遗留问题，将实体以及方法的入口参数中的user替换为account。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.0_20200214_build_A

#### 功能构建

- 增加配置文件，允许在程序在没有默认账户的情况下建立默认账户。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.0_20200213_build_C

#### 功能构建

- 为dubbo框架配置dubbo.host属性。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.0_20200212_build_B

#### 功能构建

- 将工程中的文本“用户”替换为“账户”。
- 调整subgrade工程版本为beta-0.2.0.b。
- ExceptionCode偏移量可配置化。

#### Bug修复

- 修正ServiceExceptionCodes中的常量错误。

#### 功能移除

- (无)

---

### Release_1.0.0_20200212_build_A

#### 功能构建

- 工程全目标实现。

#### Bug修复

- (无)

#### 功能移除

- (无)
