# ChangeLog

## Release_3.0.1_20260607_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/InstallToCentos.md。

- Wiki 更新。
  - docs/wiki/zh-CN/CompileBySource.md。

- `account-keeper-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.acckeeper.impl.service.ProtectorVariableMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectDetailRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.handler.ProtectLocalCacheHandlerImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorSupportMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginParamRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.DeriveHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.AccountOperateServiceImplTest。

- `account-keeper-sdk` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.acckeeper.sdk.bean.BeanMapper。

- `account-keeper-stack` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.acckeeper.stack.dao.LoginParamRecordDao。
  - com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.8.3.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `2.0.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `2.0.2.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.2.0.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `2.0.1.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `assembly.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_3.0.0_20260607_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/QuickStart.md。

- Wiki 更新。
  - docs/wiki/zh-CN/UpgradeGuide2.0.0.md。
  - docs/wiki/zh-CN/CompileBySource.md。

- `account-keeper-distribute` 模块新增。
  - 新增 `account-keeper-distribute` 模块，负责各个构型的产物分发。

- `account-keeper-node` 模块重构。
  - 将 `account-keeper-node` 调整为聚合模块。
  - 新建 `account-keeper-node-all-he` 模块，迁移项目原有内容，并形成项目的 `he` 构型。
  - 其它相关文件路径、包路径、配置路径等同步调整。

- 配置命名空间全局唯一化改造。
  - 为 `account-keeper-impl` 模块配置键统一增加全球唯一前缀，以消除跨微服务同名配置冲突。
  - 为 `account-keeper-node` 模块配置键统一增加全球唯一前缀，以消除跨微服务同名配置冲突。
  - 处理 `redis/prefix.properties` 缓存前缀配置的实体命名空间。
  - 同步调整 `*.java` 中的配置读取占位符。
  - 同步调整 `*application-context-*.xml` 中的配置读取占位符。

- 部分代理类实现中的字段类型提升为对应的接口，与具体实现解耦。
  - com.dwarfeng.acckeeper.impl.cache.AccountCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.DeriveHistoryCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.LoginHistoryCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.LoginParamRecordCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.LoginStateCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.ProtectDetailRecordCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.ProtectorInfoCacheImpl。
  - com.dwarfeng.acckeeper.impl.cache.ProtectorVariableCacheImpl。
  - com.dwarfeng.acckeeper.impl.dao.AccountDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.DeriveHistoryDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.LoginHistoryDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.LoginParamRecordDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.LoginStateDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.ProtectDetailRecordDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.ProtectorInfoDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.ProtectorSupportCacheImpl。
  - com.dwarfeng.acckeeper.impl.dao.ProtectorSupportDaoImpl。
  - com.dwarfeng.acckeeper.impl.dao.ProtectorVariableDaoImpl。
  - com.dwarfeng.acckeeper.impl.service.AccountMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.DeriveHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginHistoryMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginParamRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.LoginStateMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectDetailRecordMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorInfoMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorSupportMaintainServiceImpl。
  - com.dwarfeng.acckeeper.impl.service.ProtectorVariableMaintainServiceImpl。

- 优化项目的异常处理机制。
  - `account-keeper-sdk` 子模块新增 `ServiceExceptionHelper` 工具类，统一维护项目自身的异常映射关系。
  - `account-keeper-impl` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。
  - `account-keeper-node` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。

- 依赖升级。
  - 升级 `spring-terminator` 依赖版本为 `2.0.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring-telqos` 依赖版本为 `2.0.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.1.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `jedis` 依赖版本为 `3.10.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.23` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.5` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.4` 以规避漏洞。
  - 升级 `mapstruct` 依赖版本为 `1.5.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `2.0.0.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.2.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Vibe Coding 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
