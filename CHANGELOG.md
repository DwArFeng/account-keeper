# ChangeLog

## Release_2.0.0_20251116_build_A

### 功能构建

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
