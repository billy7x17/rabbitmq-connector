package com.billy.vdcollector.controller;

import com.billy.vdcollector.manager.RabbitMQManager;
import org.springframework.stereotype.Controller;

/**
 * 操作队列消息的控制器<br>
 * <b>原则上只允许有一个spring bean实现该接口，若有多个实现，请用@primary标注其中一个bean来宣告自己是主要控制器</b>
 *
 * @author <a href="mailto:li-zr@neusoft.com">li-zr</a>
 *         2017/5/27 11:32
 */
@Controller
public interface MessageController {


    /**
     * 执行业务<br>
     * <b>默认实现：返回成功</b>
     *
     * @param msgBody 队列消息字符串
     * @return 返回执行结果：<br>
     * <li>success - 执行成功</li>
     * <li>retry - 需要将消息放回队列重试</li>
     * <li>refuse - 需要拒绝该队列，即丢弃</li>
     */
    public default RabbitMQManager.ResultType execute(String msgBody) {
        return RabbitMQManager.ResultType.success;
    }

}
