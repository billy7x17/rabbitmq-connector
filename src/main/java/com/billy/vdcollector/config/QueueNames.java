package com.billy.vdcollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 队列名称bean
 *
 * @author <a href="mailto:li-zr@neusoft.com">li-zr</a>
 *         2017/5/27 15:53
 */
@Component
@ConfigurationProperties(prefix = "queueConfig")
public class QueueNames {

    private List<String> queueNames;

    public List<String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(List<String> queueNames) {
        this.queueNames = queueNames;
    }
}
