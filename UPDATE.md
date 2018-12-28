yexuejc-base 更新记录
------------------

#### version ：1.2.8
**time：2018-12-28 20:10:14** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 新增[ObjUtil](src/main/java/com/yexuejc/base/util/ObjUtil.java) 对类（对象）进行处理，提供深度克隆

#
#### version ：1.2.6
**time：2018-12-21 14:58:49** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. RSA 验签增加初始化方法

#
#### version ：1.2.7
**time：2018-12-24 15:31:01** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. FileUtil增加base64转File `base64ToFile()`

#
#### version ：1.2.6
**time：2018-12-21 14:58:49** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. RSA 验签增加初始化方法

#
#### version ：1.2.5
**time：2018-12-20 13:13:23** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 丰富[JsonUtil](src/main/java/com/yexuejc/base/util/JsonUtil.java),支持直接对Map泛型转换

#
#### version ：1.2.4
**time：2018-11-27 14:46:04** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 工具类的优化
>2.规范代码

#
#### version ：1.2.3
**time：2018-11-23 16:45:42** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 修复RSA加密（签名）时选择的Base64（encodeBase64URLSafeString、encodeBase64String）区分
#
#### version ：1.2.1
**time：2018-11-9 15:05:06** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 优化resps
#
#### version ：1.2.2
**time：2018-11-20 20:20:12** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 优化RSA 加解密
>1. 增加RSA 签名
#
#### version ：1.2.1
**time：2018-11-9 15:05:06** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 优化resps
#
#### version ：1.2.0
**time：2018-10-19 11:38:20** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 增加异步线程处理工具
```$java
SysUtil.threadRun(() -> {
    //异步执行代码块
}
```
#

#### version ：1.1.9
**time：2018-9-23 11:57:36** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 优化工具类包名：不向下兼容，升级请修改
>2. 升级JWT工具类：更改为单例模式，可配置参数
#

#### version ：1.1.8
**time：2018-9-3 19:29:56** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 增肌图片处理工具类
>2. 增肌3des工具类
>3. 增肌RSA工具类
>4. 优化其他工具类
#
#### version ：1.1.7
**time：2018-8-17 11:22:50** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 优化ApiVO
#

#### version ：1.1.6
**time：2018-7-7 11:32:56** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. maven仓库更新
#

#### version ：1.1.5
**time：2018-6-19 22:16:34** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 优化ApiVO

#
#### version ：1.1.4
**time：2018-6-14 22:27:59** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 统一编码：UTF-8

#
#### version ：1.1.3
**time：2018年6月2日12:16:58** <br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 修改正则RegexUtils.java
>2. 修改正则StrUtil.java->扩展genUUID()

#
#### version ：1.1.2
**time：** 2018-5-16 15:03:28<br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 修改依赖

#
#### version ：1.1.1
**time：** 2018-5-12 22:25:05<br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 添加RSA
#

##### version ：1.1.0
**time：** 2018-5-12 22:25:05<br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 添加支持加密功能
#

#### version ：1.0.0
**time：** 2018-1-31 12:16:10<br/>
**branch：** master    <br/>
**update：**     <br/>
>1. 基于java8开发的web应用工具包
#