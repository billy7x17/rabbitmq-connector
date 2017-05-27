package com.billy.vdcollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * rabbitmq配置信息映射
 */
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class MqConfigBean {

    /**
     * 主机（多个）
     */
    private String hosts;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 虚拟主机
     */
    private String virtualHost;

    /**
     * 获取主机
     *
     * @return 主机字符串
     */
    public String getHosts() {
        return hosts;
    }

    /**
     * 设置主机
     *
     * @param hosts 主机字符串
     * @throws Exception 空指针异常
     */
    public void setHosts(String hosts) throws Exception {
        if (null == hosts || "".equals(hosts)) {
            throw new Exception("rabbitmq hosts can not be null.");
        }
        this.hosts = hosts;
    }

    /**
     * 获取用户名
     *
     * @return 用户名字符串
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名字符串
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return 密码字符串
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码字符串
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取虚拟主机
     *
     * @return virtualHost 虚拟主机字符串
     */
    public String getVirtualHost() {
        return virtualHost;
    }

    /**
     * 设置虚拟主机
     *
     * @param virtualHost 虚拟主机字符串
     */
    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
}
