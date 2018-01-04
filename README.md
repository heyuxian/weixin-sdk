# 微信 SDK
[![Build Status](https://www.travis-ci.org/heyuxian/weixin-sdk.svg?branch=master)](https://www.travis-ci.org/heyuxian/weixin-sdk)
[![Coverage Status](https://coveralls.io/repos/github/heyuxian/weixin-sdk/badge.svg?branch=master)](https://coveralls.io/github/heyuxian/weixin-sdk?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 项目简介

本项目使用 spring-boot-starter 的形式封装了微信公众平台的常用 API，延续了 spring-boot 的风格，以最少化配置项为目标，除了 appid, secret 等基础配置项之外，你不需要做任何其他设置，系统提供的默认设置已经能覆盖大部分的使用情况；当然，总还是会有些特殊需求不能被覆盖到，所以系统也对外提供了接口，用户可以实现对应模块的接口来覆盖系统的默认设置。

## 配置

**前提**

- 本项目基于 Spring Boot，所以要使用本项目，必须引入 Spring Boot 的相关依赖
- JDK 1.8 及以上

**引入 maven 依赖**

```xml
<dependency>
	<groupId>me.javaroad.openapi.wechat</groupId>
	<artifactId>wechat-spring-boot-starters</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

因为目前是 snapshot 版本，所以还需要引入 snapshot repository :

```xml
<repositories>
  <repository>
      <snapshots />
      <id>sonatype snapshots</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
  </repository>
</repositories>
```

**基础配置**

在 application.yml 中新增以下配置：

```yaml
weixin:
  mp:
    endpoint:
      # 微信回调地址，默认为 /weixin/callback，如需自定义，请修改以下配置项
      callback-url: /weixin/callback
    security:
      # 微信后台配置的 token
      token: 1234567890
      # 微信后台配置的消息加解密 aeskey
      encodingAesKey: abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG
      # #############################################################
      # 微信 access token 过期刷新阈值，计算方法：
      # 当前时间 - (获得token时间 + token 有效期 + 阈值) < 0 则刷新 token
      # 默认为 token 失效前 5 分钟刷新
      # #############################################################
      refresh-token-threshold: 300
    auth:
      # APPID
      appid: appid
      # APP secret
      secret: secret
```

完成以上两项配置后，已经可以成功接入微信。接下来，你可以根据自身需求阅读对应模块的[文档](https://github.com/heyuxian/weixin-sdk/wiki)，实现自己的业务逻辑。

## [目录](https://github.com/heyuxian/weixin-sdk/wiki)

## 目录

- [获取 AccessToken](https://github.com/heyuxian/weixin-sdk/wiki/%E8%8E%B7%E5%8F%96-AccessToken)
- [消息管理](https://github.com/heyuxian/weixin-sdk/wiki/%E6%B6%88%E6%81%AF%E7%AE%A1%E7%90%86)
  - 接收普通消息
  - 接收事件推送
  - 被动回复消息
  - 消息加解密
  - 获取微信服务器 IP
  - 客服消息
  - 群发接口和原创校验
  - 模板消息接口
  - 一次性订阅消息
- 素材管理
- 图文消息留言管理
- 用户管理
- 账号管理
- 数据统计
- 微信卡卷
- ... TODO


## 问题及建议

若是对于本项目有任何问题或建议,请提 [Issue](https://github.com/heyuxian/weixin-sdk/issues/new) 。
