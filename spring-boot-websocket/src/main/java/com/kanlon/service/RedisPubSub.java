package com.kanlon.service;

import com.kanlon.model.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * redis 订阅推送
 *
 * @author zhangcanlong
 * @since 2019-06-02
 */
@Service
public class RedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisPubSub.class);

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private ChannelTopic topic = new ChannelTopic("/redis/pubsub");

    /**
     * 定时发布消息
     **/
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    private void schedule() {
        logger.info("publish message");
        publish("admin", "hey you must go now!");
    }

    /**
     * 推送消息
     *
     * @param publisher
     * @param content
     */
    public void publish(String publisher, String content) {
        logger.info("message send {} by {}", content, publisher);

        SimpleMessage pushMsg = new SimpleMessage();
        pushMsg.setContent(content);
        pushMsg.setCreateTime(new Date());
        pushMsg.setPublisher(publisher);

        redisTemplate.convertAndSend(topic.getTopic(), pushMsg);
    }


}
