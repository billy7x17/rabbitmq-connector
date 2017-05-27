# rabbitmq connector #

连接rabbitmq的springboot程序

## Features

- 对接简洁：实现controller接口即可
- 配置简单：rabbitmq基本信息及队列名称
- 支持高可用的rabbitmq集群的监听
- 支持多个队列同时监听

## Installation

maven:


    <dependency>
        <groupId>com.billy</groupId>
        <artifactId>rabbitmq-connector</artifactId>
        <version>1.0</version>
    </dependency>
    
## Quick Start

**构建spring boot**

/application.yml:

    spring:
      profiles:
        active: system

/application-system.yml:

    rabbitmq:
      hosts: 192.168.1.1:5672,192.168.1.2:5672
      username: guest
      password: guest
      virtual-host: /
    
    queueConfig:
      queueNames:
        - testQueue1
        - testQueue2
        
/.../Application.java

    package com.billy;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    
    @SpringBootApplication
    public class Application {
    
    
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    
    
    }

**构建队列消息的控制器**

Controller实现类

    package com.billy.vdcollector.controller;
    
    import org.springframework.context.annotation.Primary;
    import org.springframework.stereotype.Controller;
    
    @Controller
    public class SimpleController implements MessageController {
    
        public RabbitMQManager.ResultType execute(String msgBody) {
            return RabbitMQManager.ResultType.success;
        }
    
    }


## License

billy