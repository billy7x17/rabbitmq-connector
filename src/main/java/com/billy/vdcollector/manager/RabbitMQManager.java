package com.billy.vdcollector.manager;

import com.billy.vdcollector.config.MqConfigBean;
import com.billy.vdcollector.config.QueueNames;
import com.billy.vdcollector.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * rabbitmq监听配置
 *
 * @author <a href="mailto:li-zr@neusoft.com">li-zr</a>
 *         2017/3/28 11:14
 */
@Configuration
public class RabbitMQManager {

    /**
     * 日志打印
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MqConfigBean configBean;

    /**
     * 队列名称
     */
    @Autowired
    private QueueNames queueNames;
    /**
     * 消息控制器
     */
    @Autowired
    private MessageController messageController;

    /**
     * 配置链接信息
     *
     * @return
     */
//    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(configBean.getHosts());
        connectionFactory.setUsername(configBean.getUsername());
        connectionFactory.setPassword(configBean.getPassword());
        connectionFactory.setVirtualHost(configBean.getVirtualHost());
//        connectionFactory.setPublisherConfirms(true); // 必须要设置
        return connectionFactory;
    }

    /**
     * 配置消息队列1
     * 针对消费者配置
     *
     * @return
     */
//    @Bean
    public Queue[] queue() {
        List<String> queueNameList = queueNames.getQueueNames();

        Queue[] queues = new Queue[queueNameList.size()];

        for (int i = 0; i < queues.length; i++) {
            queues[i] = new Queue(queueNameList.get(i), false);
        }

        return queues;

    }


    /**
     * 接受消息的监听，这个监听会接受消息队列的消息
     * 针对消费者配置
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        // 配合文件
        container.setExposeListenerChannel(true);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            String msgBody = new String(message.getBody());

            ResultType resultType = ResultType.refuse;

            try {
                resultType = messageController.execute(msgBody);
            } catch (Throwable throwable) {
                log.error("运行时异常：", throwable);
            } finally {
                switch (resultType) {
                    case success:
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        break;
                    case retry:
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                        break;
                    default:
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                }
            }

        });
        return container;
    }

    /**
     * 结果类型：成功、重试、拒绝
     */
    public static enum ResultType {
        success, retry, refuse
    }
}
