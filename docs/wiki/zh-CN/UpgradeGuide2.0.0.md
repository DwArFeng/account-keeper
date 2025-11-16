# Upgrade Guide 2.0.0 - 升级指南 2.0.0

## 综述

本文档提供了从 Account Keeper 1.10.x 及更早版本升级到 2.0.0 版本的详细指南。

**重要提示**：2.0.0 版本包含**不兼容更新**，升级前请仔细阅读本文档，并按照指南进行代码迁移和数据库迁移。

本次更新主要涉及核心机制的重构，将 `LoginHandler`/`LoginService` 重命名为 `AccessHandler`/`AccessService`，
将 `LoginState`的主键类型从 `LongIdKey` 改为 `StringIdKey`，并引入了登录状态主键生成机制。
同时，派生相关接口返回类型从 `LoginState` 改为`DeriveResult`。

---

## 不兼容性改动

### Service 接口变更

#### LoginService 接口重命名为 AccessService

**变更前**：

```java
public interface LoginService extends Service {

    boolean isLogin(LongIdKey loginStateKey) throws ServiceException;

    LoginState getLoginState(LongIdKey loginStateKey) throws ServiceException;

    LoginState dynamicLogin(DynamicLoginInfo loginInfo) throws ServiceException;

    LoginState staticLogin(StaticLoginInfo loginInfo) throws ServiceException;

    void logout(LongIdKey loginStateKey) throws ServiceException;

    LoginState postpone(LongIdKey loginStateKey) throws ServiceException;
}
```

**变更后**：

```java
public interface AccessService extends Service {

    AuthInspectResult authInspect(AuthInspectInfo info) throws ServiceException;

    DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws ServiceException;

    StaticLoginResult staticLogin(StaticLoginInfo info) throws ServiceException;

    void logout(LogoutInfo info) throws ServiceException;

    PostponeResult postpone(PostponeInfo info) throws ServiceException;

    void kick(KickInfo info) throws ServiceException;
}
```

**注意**：

- 接口名称从 `LoginService` 改为 `AccessService`。
- `isLogin()` 和 `getLoginState()` 方法合并为 `authInspect()` 方法，返回 `AuthInspectResult`。
- 所有使用 `LongIdKey loginStateKey` 的方法都已改为使用相应的 Info DTO。
- 返回类型从 `LoginState` 实体改为相应的 Result DTO。
- 新增 `kick()` 方法用于踢出。

#### LoginQosService 接口重命名为 AccessQosService

`LoginQosService` 接口已重命名为 `AccessQosService`，方法签名与 `AccessService` 保持一致。

### LoginState 主键类型变更

**变更前**：

```java
public class LoginState implements Entity<LongIdKey> {

    private LongIdKey key;
    // ...
}
```

**变更后**：

```java
public class LoginState implements Entity<StringIdKey> {

    private StringIdKey key;
    // ...
}
```

**注意**：

- `LoginState` 的主键类型从 `LongIdKey` 改为 `StringIdKey`。
- 数据库表 `tbl_login_state` 的主键字段 `id` 需要从 `BIGINT` 类型改为 `VARCHAR(128)` 类型。
- 所有使用 `LoginState` 主键的地方都需要相应修改。

### 新增异常

#### LoginStateKeyConflictException

新增 `LoginStateKeyConflictException` 异常，当生成登录状态主键发生冲突且超过最大尝试次数时抛出。

```java
public class LoginStateKeyConflictException extends HandlerException {
    // ...
}
```

### 移除废弃方法

#### Protector.getLoginInfo()

已移除 `com.dwarfeng.acckeeper.stack.handler.Protector.getLoginInfo()` 方法。

---

## 配置要求

### 概述

2.0.0 版本引入了登录状态主键生成机制，需要配置登录状态主键生成器才能正常使用新服务。升级后，您需要添加以下配置文件：

### 必需的配置文件

1. **lskgen.properties**：登录状态主键生成器的配置文件。

   - 位置：`conf/acckeeper/lskgen.properties`。
   - 说明：配置登录状态主键生成器的类型和相关参数。

2. **opt-lskgen.xml**：登录状态主键生成器的可选 Spring 配置。

   - 位置：`opt/opt-lskgen.xml`。
   - 说明：启用需要使用的登录状态主键生成器实现类。

### 快速配置检查清单

在升级到 2.0.0 版本后，请确认以下配置：

- [ ] 已创建 `conf/acckeeper/lskgen.properties` 配置文件
- [ ] 已配置 `lskgen.type` 属性
- [ ] 已创建或复制 `opt/opt-lskgen.xml` 配置文件
- [ ] 已在 `opt-lskgen.xml` 中启用对应的登录状态主键生成器实现类

### 最小配置示例

如果您想快速开始，可以使用以下最小配置：

**conf/acckeeper/lskgen.properties**：

```properties
# 使用 UUID 登录状态主键生成器。
lskgen.type=uuid
```

**opt/opt-lskgen.xml**：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.acckeeper.impl.handler.lskgen" use-default-filters="false">
        <!-- 启用 UuidLoginStateKeyGenerator。 -->
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.acckeeper.impl.handler.lskgen.UuidLoginStateKeyGenerator"
        />
    </context:component-scan>
</beans>
```

---

## 数据库迁移方案

### 迁移概述

2.0.0 版本将 `LoginState` 的主键类型从 `LongIdKey` 改为 `StringIdKey`，
对应的数据库表 `tbl_login_state` 的主键字段 `id` 需要从 `BIGINT` 类型改为 `VARCHAR(128)` 类型。

**重要提示**：数据库迁移操作具有破坏性，请在执行前务必进行完整的数据备份。

### 迁移前准备

1. **数据备份**：
   - 备份整个数据库，确保可以完全恢复。
   - 特别关注 `tbl_login_state` 表的数据。

2. **应用停机**：
   - 停止所有使用 Account Keeper 的应用程序。
   - 确保没有正在进行的登录或派生操作。

3. **环境准备**：
   - 在测试环境中先进行迁移测试。
   - 准备回滚方案。

### 迁移步骤

#### 方案一：清空数据后修改结构（推荐，适用于可以清空登录状态的场景）

如果您的应用可以接受清空所有登录状态，这是最简单的迁移方案：

```sql
-- 1. 清空登录状态表
TRUNCATE TABLE tbl_login_state;

-- 2. 修改主键字段类型
ALTER TABLE tbl_login_state MODIFY COLUMN id VARCHAR (128) NOT NULL;
```

#### 方案二：保留数据迁移（适用于需要保留现有登录状态的场景）

如果需要保留现有的登录状态数据，需要执行数据迁移：

```sql
-- 1. 创建临时表
CREATE TABLE tbl_login_state_temp LIKE tbl_login_state;
ALTER TABLE tbl_login_state_temp MODIFY COLUMN id VARCHAR (128) NOT NULL;

-- 2. 迁移数据（将 Long 类型的主键转换为 String 类型）
INSERT INTO tbl_login_state_temp (id, account_id, expire_date, serial_version, generated_date, type, remark)
SELECT CAST(id AS CHAR) AS id,
       account_id,
       expire_date,
       serial_version,
       generated_date,
       type,
       remark
FROM tbl_login_state;

-- 3. 备份原表（可选，但强烈推荐）
RENAME
TABLE tbl_login_state TO tbl_login_state_backup;

-- 4. 重命名临时表
RENAME
TABLE tbl_login_state_temp TO tbl_login_state;

-- 5. 重建索引和约束（如果需要）
-- 注意：根据您的数据库实际情况，可能需要重建外键约束
```

**注意**：如果 `tbl_login_state` 表有外键约束，需要先删除外键约束，修改结构后再重新创建。

#### 方案三：使用中间字段迁移（适用于大型生产环境）

对于大型生产环境，可以使用更安全的迁移方式：

```sql
-- 1. 添加新的 VARCHAR 类型字段
ALTER TABLE tbl_login_state
    ADD COLUMN id_new VARCHAR(128) NULL;

-- 2. 迁移数据到新字段
UPDATE tbl_login_state
SET id_new = CAST(id AS CHAR);

-- 3. 删除旧的主键约束
ALTER TABLE tbl_login_state DROP PRIMARY KEY;

-- 4. 删除旧字段
ALTER TABLE tbl_login_state DROP COLUMN id;

-- 5. 重命名新字段
ALTER TABLE tbl_login_state CHANGE COLUMN id_new id VARCHAR (128) NOT NULL;

-- 6. 重建主键
ALTER TABLE tbl_login_state
    ADD PRIMARY KEY (id);
```

### 迁移后验证

1. **结构验证**：
   ```sql
   -- 检查表结构
   DESCRIBE tbl_login_state;
   -- 确认 id 字段类型为 VARCHAR(128)
   ```

2. **数据验证**：
   ```sql
   -- 检查数据完整性
   SELECT COUNT(*) FROM tbl_login_state;
   -- 检查主键唯一性
   SELECT id, COUNT(*) FROM tbl_login_state GROUP BY id HAVING COUNT(*) > 1;
   ```

3. **应用验证**：
   - 启动应用程序，确认没有启动错误。
   - 执行登录操作，确认可以正常创建登录状态。
   - 检查日志，确认没有数据库相关的错误。

### 回滚方案

如果迁移失败，需要回滚：

1. **停止应用程序**。
2. **恢复数据库备份**：
   ```sql
   -- 如果使用了备份表
   DROP TABLE tbl_login_state;
   RENAME TABLE tbl_login_state_backup TO tbl_login_state;
   ```
   或者从完整备份恢复数据库。
3. **验证回滚结果**。
4. **重新启动应用程序**。

---

## 改动原因

本次不兼容更新的主要原因如下：

1. **命名统一性**：将 `LoginHandler`/`LoginService` 重命名为 `AccessHandler`/`AccessService`，
   使命名更加统一和准确，更好地反映其功能（访问控制而非单纯的登录）。

2. **安全性提升**：将 `LoginState` 的主键类型从 `LongIdKey` 改为 `StringIdKey`。使用数字类型的主键容易被猜测，存在安全隐患。
   字符串类型的主键（如 UUID）是随机生成的，具有更好的安全性。

3. **接口优化**：将 `isLogin()` 和 `getLoginState()` 合并为 `authInspect()` 方法，使接口更加简洁和统一。

4. **功能扩展**：新增 `kick()` 方法，提供更灵活的登录状态管理功能。

5. **主键生成机制**：引入登录状态主键生成机制，支持多种生成策略（UUID、随机字符串等），提高系统的灵活性和可扩展性。

---

## 代码迁移指南

### 1. AccessService 迁移指南

**变更前**：

```java

@Service
public class UserService {

    @Autowired
    private LoginService loginService;

    public void loginUser(StringIdKey accountKey, String password) throws ServiceException {
        DynamicLoginInfo loginInfo = new DynamicLoginInfo(accountKey, password, null, null);
        LoginState loginState = loginService.dynamicLogin(loginInfo);
        // 使用 loginState.getKey() 获取登录状态主键。
        LongIdKey loginStateKey = loginState.getKey();
        // 存储登录状态主键。
        storeLoginStateKey(loginStateKey);
    }

    public void logoutUser(LongIdKey loginStateKey) throws ServiceException {
        loginService.logout(loginStateKey);
    }

    public boolean checkLogin(LongIdKey loginStateKey) throws ServiceException {
        return loginService.isLogin(loginStateKey);
    }

    public LoginState getLoginStateInfo(LongIdKey loginStateKey) throws ServiceException {
        return loginService.getLoginState(loginStateKey);
    }

    public void extendLogin(LongIdKey loginStateKey) throws ServiceException {
        LoginState loginState = loginService.postpone(loginStateKey);
        // 使用延期后的登录状态。
    }
}
```

**变更后**：

```java

@Service
public class UserService {

    @Autowired
    private AccessService accessService;

    public void loginUser(StringIdKey accountKey, String password) throws ServiceException {
        DynamicLoginInfo loginInfo = new DynamicLoginInfo(accountKey, password, null, null);
        DynamicLoginResult result = accessService.dynamicLogin(loginInfo);
        // 使用 result.getLoginStateKey() 获取登录状态主键。
        StringIdKey loginStateKey = result.getLoginStateKey();
        // 存储登录状态主键。
        storeLoginStateKey(loginStateKey);
    }

    public void logoutUser(StringIdKey loginStateKey) throws ServiceException {
        LogoutInfo logoutInfo = new LogoutInfo(loginStateKey);
        accessService.logout(logoutInfo);
    }

    public boolean checkLogin(StringIdKey loginStateKey) throws ServiceException {
        AuthInspectInfo info = new AuthInspectInfo(loginStateKey);
        AuthInspectResult result = accessService.authInspect(info);
        return result.isLogin();
    }

    public LoginState getLoginStateInfo(StringIdKey loginStateKey) throws ServiceException {
        AuthInspectInfo info = new AuthInspectInfo(loginStateKey);
        AuthInspectResult result = accessService.authInspect(info);
        return result.getLoginState();
    }

    public void extendLogin(StringIdKey loginStateKey) throws ServiceException {
        PostponeInfo postponeInfo = new PostponeInfo(loginStateKey);
        PostponeResult result = accessService.postpone(postponeInfo);
        // 使用延期后的结果。
    }

    public void kickUser(StringIdKey loginStateKey) throws ServiceException {
        KickInfo kickInfo = new KickInfo(loginStateKey, null);
        accessService.kick(kickInfo);
    }
}
```

**迁移要点**：

- 将所有使用 `LoginService` 的地方改为使用 `AccessService`。
- 将所有使用 `LongIdKey loginStateKey` 的地方改为使用 `StringIdKey loginStateKey`。
- 将 `isLogin()` 和 `getLoginState()` 方法调用改为使用 `authInspect()` 方法。
- 需要创建相应的 Info DTO 对象作为方法参数（`AuthInspectInfo`、`LogoutInfo`、`PostponeInfo`、`KickInfo`）。
- 如果原来使用 `loginState.getKey()`，现在需要使用 `result.getLoginStateKey()`。
- 新增 `kick()` 方法可用于踢出。

### 2. DeriveService 迁移指南

**变更前**：

```java

@Service
public class DeriveService {

    @Autowired
    private DeriveService deriveService;

    public void deriveLogin(LongIdKey parentLoginStateKey) throws ServiceException {
        DynamicDeriveInfo deriveInfo = new DynamicDeriveInfo(parentLoginStateKey, null);
        LoginState derivedLoginState = deriveService.dynamicDerive(deriveInfo);
        // 使用 derivedLoginState.getKey() 获取派生登录状态主键。
        LongIdKey derivedLoginStateKey = derivedLoginState.getKey();
        // 存储派生登录状态主键。
        storeDerivedLoginStateKey(derivedLoginStateKey);
    }
}
```

**变更后**：

```java

@Service
public class DeriveService {

    @Autowired
    private DeriveService deriveService;

    public void deriveLogin(StringIdKey parentLoginStateKey) throws ServiceException {
        DynamicDeriveInfo deriveInfo = new DynamicDeriveInfo(parentLoginStateKey, null);
        DynamicDeriveResult result = deriveService.dynamicDerive(deriveInfo);
        // 使用 result.getLoginStateKey() 获取派生登录状态主键。
        StringIdKey derivedLoginStateKey = result.getLoginStateKey();
        // 存储派生登录状态主键。
        storeDerivedLoginStateKey(derivedLoginStateKey);
    }
}
```

**迁移要点**：

- 将所有使用 `LongIdKey parentLoginStateKey` 的地方改为使用 `StringIdKey parentLoginStateKey`。
- 将返回类型从 `LoginState` 改为 `DynamicDeriveResult` 和 `StaticDeriveResult`。
- 如果原来使用 `derivedLoginState.getKey()`，现在需要使用 `result.getLoginStateKey()`。

### 3. LoginState 主键类型迁移

**变更前**：

```java
public void processLoginState(LongIdKey loginStateKey) {
    // 使用 LongIdKey
    LoginState loginState = loginStateMaintainService.get(loginStateKey);
    LongIdKey key = loginState.getKey();
}
```

**变更后**：

```java
public void processLoginState(StringIdKey loginStateKey) {
    // 使用 StringIdKey
    LoginState loginState = loginStateMaintainService.get(loginStateKey);
    StringIdKey key = loginState.getKey();
}
```

**迁移要点**：

- 将所有使用 `LongIdKey` 作为 `LoginState` 主键的地方改为使用 `StringIdKey`。
- 更新所有相关的变量声明、方法参数、返回值类型。
- 更新数据库查询条件，从数字比较改为字符串比较。

---

## 配置迁移详细说明

### 配置文件位置

#### lskgen.properties

该配置文件用于配置登录状态主键生成器的类型和相关参数。

**配置文件位置**：

- 默认位置：`classpath:acckeeper/lskgen.properties`（jar 包内）。
- 运行时配置：`conf/acckeeper/lskgen.properties`（推荐，会覆盖默认配置）。

**配置加载机制**：

- 系统会先加载 jar 包内的默认配置。
- 如果存在 `conf/acckeeper/lskgen.properties`，会覆盖默认配置。
- 如果存在 `confext/*.properties`，会进一步覆盖配置。

#### opt-lskgen.xml

该配置文件用于启用需要使用的登录状态主键生成器实现类。

**配置文件位置**：

- 默认位置：`opt/opt-lskgen.xml`（项目根目录下的 opt 文件夹）。
- 外部配置：`optext/opt-*.xml`（项目根目录下的 optext 文件夹，可选）。

**配置加载机制**：

- 系统会加载 `opt/opt*.xml` 文件。
- 如果存在 `optext/opt*.xml` 文件，会进一步加载。

### lskgen.properties 配置说明

#### 全局配置

**lskgen.type**

- **说明**：当前使用的登录状态主键生成器类型。
- **类型**：String。
- **必需**：是。
- **可选值**：
   - `uuid`：使用 UUID 登录状态主键生成器（推荐，默认值）。
   - `randx`：使用 Randx 登录状态主键生成器（生成固定长度的随机字符串）。
   - `snowflake`：使用 Snowflake 登录状态主键生成器（已废弃，仅用于兼容旧格式）。
- **示例**：`lskgen.type=uuid`。

#### UUID 登录状态主键生成器配置

如果使用 `uuid` 类型，无需额外配置。

#### Randx 登录状态主键生成器配置

如果使用 `randx` 类型，需要配置以下参数：

**lskgen.randx.length**

- **说明**：生成的登录状态主键的长度。
- **类型**：int。
- **必需**：是（使用 randx 类型时）。
- **默认值**：128。
- **取值范围**：必须大于 0。
- **示例**：`lskgen.randx.length=128`。

#### Snowflake 登录状态主键生成器配置

如果使用 `snowflake` 类型，无需额外配置。

**注意**：`snowflake` 类型已被废弃，仅用于兼容旧的登录状态主键格式。Snowflake ID 基于时间戳和机器 ID 生成，容易被推测，存在安全风险，建议使用
`uuid` 或 `randx` 生成器。

### opt-lskgen.xml 配置说明

该配置文件用于启用需要使用的登录状态主键生成器实现类。默认情况下，所有登录状态主键生成器都被注释掉了，您需要取消注释来启用它们。

#### 启用 UuidLoginStateKeyGenerator

如果您使用 `uuid` 类型的登录状态主键生成器，需要取消注释以下内容：

```xml

<context:include-filter
        type="assignable"
        expression="com.dwarfeng.acckeeper.impl.handler.lskgen.UuidLoginStateKeyGenerator"
/>
```

#### 启用 RandxLoginStateKeyGenerator

如果您使用 `randx` 类型的登录状态主键生成器，需要取消注释以下内容：

```xml

<context:include-filter
        type="assignable"
        expression="com.dwarfeng.acckeeper.impl.handler.lskgen.RandxLoginStateKeyGenerator"
/>
```

#### 启用 SnowflakeLoginStateKeyGenerator

如果您使用 `snowflake` 类型的登录状态主键生成器（不推荐），需要取消注释以下内容：

```xml
<!--suppress DeprecatedClassUsageInspection -->
<context:include-filter
        type="assignable"
        expression="com.dwarfeng.acckeeper.impl.handler.lskgen.SnowflakeLoginStateKeyGenerator"
/>
```

**注意**：`SnowflakeLoginStateKeyGenerator` 已被废弃，仅用于兼容旧的登录状态主键格式。

#### 同时启用多个登录状态主键生成器

如果您需要同时启用多个登录状态主键生成器（例如，在开发环境中测试不同的生成器），可以取消注释多个 `include-filter`：

```xml

<context:component-scan base-package="com.dwarfeng.acckeeper.impl.handler.lskgen" use-default-filters="false">
    <!-- 启用 UuidLoginStateKeyGenerator。 -->
    <context:include-filter
            type="assignable"
            expression="com.dwarfeng.acckeeper.impl.handler.lskgen.UuidLoginStateKeyGenerator"
    />

    <!-- 启用 RandxLoginStateKeyGenerator。 -->
    <context:include-filter
            type="assignable"
            expression="com.dwarfeng.acckeeper.impl.handler.lskgen.RandxLoginStateKeyGenerator"
    />
</context:component-scan>
```

**注意**：虽然可以同时启用多个登录状态主键生成器，但 `lskgen.properties` 中的 `lskgen.type` 只能指定一个类型。
系统会根据该类型选择对应的登录状态主键生成器。

### 配置示例

#### 示例 1：使用 UUID 登录状态主键生成器（推荐）

**conf/acckeeper/lskgen.properties**：

```properties
###################################################
#                     global                      #
###################################################
# 当前的登录状态主键生成器类型。
lskgen.type=uuid
```

**opt/opt-lskgen.xml**：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.acckeeper.impl.handler.lskgen" use-default-filters="false">
        <!-- 启用 UuidLoginStateKeyGenerator。 -->
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.acckeeper.impl.handler.lskgen.UuidLoginStateKeyGenerator"
        />
    </context:component-scan>
</beans>
```

#### 示例 2：使用 Randx 登录状态主键生成器

**conf/acckeeper/lskgen.properties**：

```properties
###################################################
#                     global                      #
###################################################
# 当前的登录状态主键生成器类型。
lskgen.type=randx
###################################################
#                      randx                      #
###################################################
# randx 登录状态主键生成器生成的登录状态主键的长度。
lskgen.randx.length=128
```

**opt/opt-lskgen.xml**：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.acckeeper.impl.handler.lskgen" use-default-filters="false">
        <!-- 启用 RandxLoginStateKeyGenerator。 -->
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.acckeeper.impl.handler.lskgen.RandxLoginStateKeyGenerator"
        />
    </context:component-scan>
</beans>
```

### 配置验证

配置完成后，启动应用程序时，系统会：

1. 加载 `lskgen.properties` 配置文件。
2. 根据 `lskgen.type` 查找对应的登录状态主键生成器。
3. 如果找不到对应的登录状态主键生成器，会抛出异常：`未知的 loginStateKeyGenerator 类型: [类型]`。

**验证方法**：

- 检查应用程序启动日志，确认没有配置相关的错误。
- 尝试调用登录服务，确认登录状态主键能够正常生成。
- 如果出现配置错误，请检查配置文件的位置和内容。

---

## 注意事项

1. **数据库迁移**：

   - 数据库迁移操作具有破坏性，必须在执行前进行完整的数据备份。
   - 建议在测试环境中先进行迁移测试，确认无误后再在生产环境执行。
   - 如果 `tbl_login_state` 表有外键约束，需要先删除外键约束，修改结构后再重新创建。
   - 迁移过程中需要停止应用程序，确保没有正在进行的操作。

2. **主键类型变更**：

   - 所有使用 `LoginState` 主键的地方都需要从 `LongIdKey` 改为 `StringIdKey`。
   - 更新所有相关的变量声明、方法参数、返回值类型。
   - 更新数据库查询条件，从数字比较改为字符串比较。
   - 如果您的代码中存储了登录状态主键（如缓存、Session 等），需要确保存储格式兼容。

3. **接口重命名**：

   - 所有使用 `LoginService` 的地方都需要改为使用 `AccessService`。
   - 所有使用 `LoginHandler` 的地方都需要改为使用 `AccessHandler`。
   - 所有使用 `LoginQosService` 的地方都需要改为使用 `AccessQosService`。

4. **方法变更**：

   - `isLogin()` 和 `getLoginState()` 方法已合并为 `authInspect()` 方法。
   - 需要创建相应的 Info DTO 对象作为方法参数。
   - 返回类型从 `LoginState` 实体改为相应的 Result DTO 对象。

5. **登录状态主键生成器**：

   - 2.0.0 版本引入了登录状态主键生成机制，必须配置登录状态主键生成器才能正常使用新服务。
   - 配置文件位置：
      - `conf/acckeeper/lskgen.properties`：登录状态主键生成器的配置文件（必需）。
      - `opt/opt-lskgen.xml`：登录状态主键生成器的可选 Spring 配置（必需，需启用对应的登录状态主键生成器）。
   - 配置文件的优先级：`confext/*.properties` > `conf/*.properties` > `classpath:*.properties`。
   - 如果配置缺失或登录状态主键生成器未启用，应用程序启动时会抛出异常，请检查配置文件。
   - 建议在升级后立即配置登录状态主键生成器，否则无法使用新的访问和派生服务。

6. **测试**：

   - 升级后请充分测试所有涉及登录和派生的功能。
   - 特别注意测试登录状态主键的生成、存储和验证。
   - 测试所有使用访问和派生功能的地方。

7. **向后兼容**：

   - 虽然接口发生了不兼容变更，但功能上保持兼容。
   - 如果原来使用数字字符串作为登录状态主键，可以直接使用。

8. **依赖升级**：

   - 2.0.0 版本升级了 `subgrade` 依赖到 1.7.1.a。
   - 如果您使用了 `subgrade` 框架，请参考 `subgrade` 对应的升级指南（如果适用）。
   - 升级了 `snowflake`、`spring-telqos`、`dwarfeng-datamark` 等依赖以规避漏洞。

---

## 常见问题

### Q: 我的登录状态主键原来是 Long 类型，现在需要改为 StringIdKey，会不会影响性能？

A: 不会。字符串类型的处理性能与 Long 类型相当，且现在的设计更加安全。
如果您的数据库中存储的是 Long 类型的主键，需要在数据库迁移时进行转换，但接口层面统一使用 `StringIdKey` 类型更加安全。

### Q: 我的代码需要访问底层实体主键，现在返回 Result DTO，我该怎么办？

A: 新机制返回 Result DTO 是为了使接口的签名更加简洁。
Result DTO 中包含 `loginStateKey`（`StringIdKey` 类型）字段，用于获取登录状态主键。
如果您的代码确实需要访问完整的 `LoginState` 实体，可以通过 `LoginStateMaintainService` 进行查询。

### Q: 我需要修改多少文件？

A: 这取决于您项目中实现和使用了哪些接口。主要需要修改：

- 所有使用 `LoginService`、`LoginHandler`、`LoginQosService` 的代码。
- 所有使用 `LoginState` 主键的地方，需要改为使用 `StringIdKey`。
- 所有使用 `DeriveService` 的代码，需要处理返回类型变更。

### Q: 升级后我的代码还能正常运行吗？

A: 如果您按照本文档的指南进行了完整的迁移，代码可以正常运行。建议在测试环境中充分测试后再部署到生产环境。

### Q: 数据库迁移失败怎么办？

A: 如果数据库迁移失败，请按照本文档的"回滚方案"部分进行回滚。确保在迁移前已经进行了完整的数据备份。

### Q: 升级后应用程序启动失败，提示"未知的 loginStateKeyGenerator 类型"，我该怎么办？

A: 这个错误表示系统找不到对应的登录状态主键生成器。请检查以下配置：

1. 确认已创建 `conf/acckeeper/lskgen.properties` 配置文件。
2. 确认配置文件中 `lskgen.type` 的值正确（`uuid`、`randx` 或 `snowflake`）。
3. 确认已在 `opt/opt-lskgen.xml` 中启用了对应的登录状态主键生成器实现类。
4. 确认配置文件的位置正确（`conf/acckeeper/` 和 `opt/` 目录）。

如果使用 `randx` 类型，还需要确认配置了 `lskgen.randx.length` 属性。

### Q: 我应该使用哪种登录状态主键生成器？

A: 推荐使用 `uuid` 类型的登录状态主键生成器，因为：

- UUID 生成的登录状态主键具有更好的唯一性保证。
- 无需额外配置，使用简单。
- 生成的登录状态主键格式标准，便于调试。

如果您需要生成固定长度的随机字符串登录状态主键，可以使用 `randx` 类型的登录状态主键生成器，
但需要配置 `lskgen.randx.length` 属性。

**注意**：`snowflake` 类型已被废弃，仅用于兼容旧的登录状态主键格式。Snowflake ID 基于时间戳和机器 ID 生成，容易被推测，存在安全风险，建议使用
`uuid` 或 `randx` 生成器。

### Q: 配置文件应该放在哪里？

A: 配置文件的位置如下：

- **lskgen.properties**：放在 `conf/acckeeper/` 目录下。
   - 如果该文件不存在，系统会使用 jar 包内的默认配置。
   - 如果该文件存在，会覆盖默认配置。
   - 如果 `confext/` 目录下有同名文件，会进一步覆盖配置。

- **opt-lskgen.xml**：放在 `opt/` 目录下。
   - 该文件必须存在，否则无法启用登录状态主键生成器。
   - 如果 `optext/` 目录下有相关的配置（如插件中额外的 `LoginStateKeyGenerator` 实现），会进一步加载。

### Q: 我可以同时使用多个登录状态主键生成器吗？

A: 可以，但不推荐。虽然可以在 `opt-lskgen.xml` 中同时启用多个登录状态主键生成器，
但 `lskgen.properties` 中的 `lskgen.type` 只能指定一个类型。

系统会根据 `lskgen.type` 的值选择对应的登录状态主键生成器。如果指定的类型对应的登录状态主键生成器未启用，会抛出异常。

建议只启用需要使用的登录状态主键生成器，这样可以减少不必要的类加载，提高启动速度。

### Q: 数据库迁移时，如果表中有外键约束怎么办？

A: 如果 `tbl_login_state` 表有外键约束，需要按以下步骤操作：

1. 删除外键约束：
   ```sql
   ALTER TABLE [引用表名] DROP FOREIGN KEY [外键约束名];
   ```

2. 执行主键类型修改（参考本文档的"数据库迁移方案"部分）。

3. 重新创建外键约束：
   ```sql
   ALTER TABLE [引用表名] ADD CONSTRAINT [外键约束名] 
   FOREIGN KEY ([外键字段名]) REFERENCES tbl_login_state(id);
   ```

**注意**：外键字段的类型也需要相应修改为 `VARCHAR(128)`。

### Q: 数据库迁移时，如果表中有大量数据怎么办？

A: 如果表中有大量数据，建议：

1. 在业务低峰期执行迁移。
2. 使用方案三（使用中间字段迁移）进行迁移，这样可以减少锁表时间。
3. 分批迁移数据，避免一次性迁移大量数据导致数据库性能问题。
4. 在测试环境中先进行压力测试，评估迁移时间。

### Q: 迁移后，旧的登录状态主键（Long 类型）还能使用吗？

A: 不能。迁移后，`LoginState` 的主键类型已从 `LongIdKey` 改为 `StringIdKey`，旧的数字类型主键无法使用。
如果您的应用需要保留旧的登录状态，需要在数据库迁移时进行数据转换（参考本文档的"数据库迁移方案"部分）。
