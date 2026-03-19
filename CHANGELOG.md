# ChangeLog

## Release_2.1.0_20260319_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/OptDirectory.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/SystemRequirements.md。
  - docs/wiki/zh-CN/UsingTelqos.md。
  - docs/wiki/zh-CN/Introduction.md。

- 优化预设的运维指令。
  - com.dwarfeng.acckeeper.impl.service.telqos.AccessCommand。

- 访问机制增强。
  - 增加可信登录机制，支持与第三方登录系统（OAuth2、SAML、企业 SSO）集成。

- 优化保护器上下文部分方法签名，增强安全性。
  - com.dwarfeng.acckeeper.stack.handler.Protector.Context.getDynamicLoginInfo。
  - com.dwarfeng.acckeeper.stack.handler.Protector.Context.getStaticLoginInfo。

- 清除机制相关类优化注释、文档注释内容。
  - com.dwarfeng.acckeeper.impl.handler.PurgeProcessor。
  - com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService。
  - com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService。

- 优化部分说明文件中的格式。
  - libext/README.md。
  - optext/README.md。

- `account-keeper-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.acckeeper.impl.dao.preset.AccountPresetCriteriaMaker。
  - com.dwarfeng.acckeeper.impl.service.telqos.AccountCommand。

- 依赖升级。
  - 升级 `netty` 依赖版本为 `4.2.9.Final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.3` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.1.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.8.3.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.7.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.15.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.16.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `1.1.3.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `assembly.xml` 文件的格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。

### Bug 修复

- 修正部分 `properties` 文件中错误的注释内容。
  - reset.properties。

- 修复 datamark 中错误的配置项与 bean 名称。
  - com.dwarfeng.acckeeper.impl.bean.entity.HibernateAccount。
  - datamark/settings.properties。
  - spring/application-context-datamark.xml。

- 修复 `assembly.xml` 中的配置错误。

### 功能移除

- (无)

---

## Release_2.0.0_20251117_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/en-US/README.md。
  - docs/wiki/zh-CN/README.md。
  - docs/wiki/zh-CN/VersionBlacklist.md。

- Wiki 编写。
  - docs/wiki/zh-CN/UpgradeGuide2.0.0.md。

- 优化部分单元测试代码，以规避潜在的 bug。
  - com.dwarfeng.acckeeper.impl.service.LoginParamRecordMaintainServiceImplTest。

- 重构核心机制。
  - 将 LoginHandler/LoginService 重命名为 AccessHandler/AccessService。
  - 将 LoginState 的主键类型从 LongIdKey 改为 StringIdKey。
  - 派生相关接口返回类型从 LoginState 改为 DeriveResult。
  - 新增 LoginStateKeyConflictException 异常。

- 实现运维指令。
  - com.dwarfeng.acckeeper.impl.service.telqos.LoginStateLookupCommand。
  - com.dwarfeng.acckeeper.impl.service.telqos.LoginStateKeyGenerateCommand。

- 实现预设登录状态主键生成器。
  - com.dwarfeng.acckeeper.impl.handler.lskgen.RandxLoginStateKeyGenerator。
  - com.dwarfeng.acckeeper.impl.handler.lskgen.UuidLoginStateKeyGenerator。
  - com.dwarfeng.acckeeper.impl.handler.lskgen.SnowflakeLoginStateKeyGenerator。

- 实现核心机制。
  - 登录状态主键生成机制。

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.system.UptimeCommand。
  - com.dwarfeng.springtelqos.api.integration.system.JmxRemoteCommand。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.7.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `snowflake` 依赖版本为 `1.8.2.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.15.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `1.1.1.a` 以规避漏洞。

### Bug 修复

- 项目结构修复。
  - 修复项目打包时未能正确生成 Linux 启停脚本的问题。

### 功能移除

- 移除废弃方法。
  - com.dwarfeng.acckeeper.stack.handler.Protector.getLoginInfo()。

- 移除运维指令。
  - com.dwarfeng.acckeeper.impl.service.telqos.KickCommand。
  - com.dwarfeng.acckeeper.impl.service.telqos.LoginCommand。
  - com.dwarfeng.acckeeper.impl.service.telqos.StateCommand。

---

## 更早的版本

[View all changelogs](./changelogs)
