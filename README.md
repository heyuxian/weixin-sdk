# 微信 SDK
[![Build Status](https://www.travis-ci.org/heyuxian/weixin-sdk.svg?branch=master)](https://www.travis-ci.org/heyuxian/weixin-sdk)
[![Coverage Status](https://coveralls.io/repos/github/heyuxian/weixin-sdk/badge.svg?branch=master)](https://coveralls.io/github/heyuxian/weixin-sdk?branch=master)[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 项目简介

本项目使用 spring-boot-starter 的形式封装了微信公众平台的常用 API。

## Quick Start

**前提**

- 本项目是基于 Spring Boot，所以要使用本项目，必须引入 Spring Boot 的相关依赖
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
  endpoint:
    # 微信回调地址，默认为 /weixin/callback，如需自定义，请修改以下配置项
    callback-url: /weixin/callback
  mp:
    security:
      # 微信后台配置的 token
      token: 1234567890
      # 微信后台配置的消息加解密 aeskey
      encodingAesKey: abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG
    auth:
      # APPID
      appid: appid
      # APP secret
      secret: secret
```

**消息模块**

- 普通消息
- 事件推送
- 消息加解密
- 被动回复

所有的消息继承自 `Message` ， 当系统收到微信服务器推送的消息时，会自动判定消息是否经过加密，如果加密，则会进行解密操作。收到消息后，系统会做以下两个操作：

- 系统会广播 `ReceiveMessageEvent` 事件，用户可以订阅此事件接收对应的消息。
- 会同步调用系统的 `MessageHandler`，每一种消息都有对应的 `MessageHandler`，如 `TextMessageHandler` 这个 Handler 处理普通的文本消息。具体可查看 `wechat-samples` 模块。同时，`MessageHandler` 需要返回信息给微信服务器，返回的消息需要实现 `ResponseMessage` ，系统已经提供了默认实现，可在 `me.javaroad.openapi.wechat.mp.model` 下面找到所有实现类。同样的，如果收到的消息是经过加密的，返回的消息也会自动加密，不需要做特殊处理。

**示例**：

```java
/**
 * @author heyx
 * 这个方法处理普通文本消息
 */
@Component
public class TextMessageHandler extends AbstractMessageHandler<TextMessage> {

    public TextMessageHandler(MessageHandlerFactory messageHandlerFactory) {
        super(messageHandlerFactory);
    }
    @Override
    public ResponseMessage handleMessage(TextMessage message) {
        // 此处根据业务对收到的消息做处理，注意，处理时间不能超过 5s，否则会导致微信对用户给出严重错误的提示
        // 如果时间很可能超过 5s, 需要在新线程中处理
        // 如果不需要对用户返回任何消息，则请在此处返回 EmptyResponseMessage
        return new TextResponseMessage();
    }
}
```

## 结论

总的来说，你只需要做两件事情：

1. 在 `application.yml` (或是 application.properties) 中配置对应的参数。
2. 自定义或直接使用默认的 `MessageHandler` 处理消息并返回。

## 问题及建议

若是对于本项目有任何问题或建议,请提 [Issue](https://github.com/heyuxian/mcloud/issues/new) 。
