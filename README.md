通用工具包

### 说明
>1. 支持环境：java8
>2. 该工具包基于springboot提取，按理说适用于所有java工程
>3. 其中依赖jjwt、validation-api，排除请使用
```
<exclusions>
    <exclusion>
        <artifactId>xxx</artifactId>
        <groupId>xxxx</groupId>
    </exclusion>
</exclusions>
```
>4. 1.1.9升级JWT为单例类


### 使用
>yexuejc.base.version=1.1.9

pom.xml
```
<dependencies>
    <dependency>
        <groupId>com.yexuejc.base</groupId>
        <artifactId>yexuejc-base</artifactId>
        <version>${yexuejc.base.version}</version>
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