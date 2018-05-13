通用工具包

### 说明
>1. 支持环境：java8
>2. 该工具包基于springboot提取，按理说适用于所有java工程
>3. 其中依赖jjwt、validation-api，但不传递依赖


### 使用
>yexuejc.base.version=1.1.0

pom.xml
```
<dependencies>
    <dependency>
        <groupId>com.github.yexuejc</groupId>
        <artifactId>yexuejc-base</artifactId>
        <version>${yexuejc.base.version}</version>
    </dependency>
</dependencies>
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### 工具文档
[Wiki](WIKI.md)

### 更新日志
[更新记录](UPDATE.md)