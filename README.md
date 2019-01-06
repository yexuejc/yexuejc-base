yexuejc-base 基于jdk8常用工具包
----------------------
源码地址:<br>
github:https://github.com/yexuejc/yexuejc-base
gitee:https://gitee.com/jzsw-it/yexuejc-base

### 说明
>1. 支持环境：java8
>2. 该工具包基于springboot提取，按理说适用于所有java工程
>3. 其中依赖jjwt、validation-api，排除请使用
>   ```
>   <exclusions>
>        <exclusion>
>            <artifactId>xxx</artifactId>
>            <groupId>xxxx</groupId>
>        </exclusion>
>    </exclusions>
>    ``` 
>
>4. `1.1.9` 升级JWT为单例类
>5. `1.2.3` 修复RSA加密(签名)Base64Url 问题，如需使用RSA请使用1.2.3+
>##### 6. 从`1.3.0`开始，版本维护转由`成都极致思维网络科技有限公司`向maven中央仓库发布版本，同时变更组织`groupId`为`top.yexuejc`。使用者请尽快升级到`1.3.0`以上（1.3.0代码向下兼容） 


### 使用
>yexuejc.base.version=1.3.0

pom.xml
```
<dependencies>
    <dependency>
        <groupId>top.yexuejc</groupId>
        <artifactId>yexuejc-base</artifactId>
        <version>${yexuejc.base.version}</version>
    </dependency>
</dependencies>
```

#### 附：1.3.0之前的使用方式
pom.xml
```
<dependencies>
    <dependency>
        <groupId>com.yexuejc.base</groupId>
        <artifactId>yexuejc-base</artifactId>
        <version>1.3.0以下</version>
    </dependency>
</dependencies>
<repositories>
    <repository>
        <id>yexuejc-nexus-public</id>
        <name>yexuejc-nexus-public</name>
        <url>https://nexus.yexuejc.club/repository/maven-public/</url>
    </repository>
</repositories>
```

### 工具文档
[Wiki](WIKI.md)

### 更新日志
[更新记录](UPDATE.md)

#### 项目发展
本工程项目由maxf基于日常使用，从[yexuejc-springboot](https://github.com/yexuejc/yexuejc-springboot.git)（_准备移交版本控制_）中抽离开源独立发展，后续增加许多常用工具包。
使用者逐渐增多后考虑可靠性和稳定性原则，移交版本控制给`成都极致思维网络科技有限公司`管理，maven包直接发布到中央仓库。
开源工程项目仍然保持继续维护和欢迎更多愿意贡献的小伙伴参与。
