package io.github.arturgaleno.sessionmanager.redisson;

import io.github.arturgaleno.domain.SessionObject;
import org.redisson.Redisson;
import org.redisson.RedissonTopic;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisPubSubListener;
import org.redisson.client.protocol.pubsub.PubSubType;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by arturgaleno on 16/02/2018.
 */
@Configuration
public class RedissonConfiguration {

    private RedissonTopic<Object> objectRedissonTopic;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }

    @Bean
    public RLocalCachedMap<String, SessionObject> sessionMap(RedissonClient redissonClient) {

        LocalCachedMapOptions<String, SessionObject> options = LocalCachedMapOptions.<String, SessionObject>defaults()
                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.LRU)
                .cacheSize(1000)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR)
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .timeToLive(2, TimeUnit.HOURS)
                .maxIdle(30, TimeUnit.MINUTES);

        RLocalCachedMap<String, SessionObject> sessionMap = redissonClient.getLocalCachedMap("sessionMap", options);

        /*
         *  RLocalCachedMap doesn't have an addListener api,
         *  this is a workaround to register a listener at redisson topic (redis pubsub)
         */
        objectRedissonTopic = new RedissonTopic<>(((Redisson) redissonClient).getCommandExecutor(), "{sessionMap}:topic");

        int id = objectRedissonTopic.addListener(new RedisPubSubListener<Object>() {
            @Override
            public boolean onStatus(PubSubType type, String channel) {
                return false;
            }

            @Override
            public void onPatternMessage(String pattern, String channel, Object message) {
            }

            @Override
            public void onMessage(String channel, Object msg) {
                System.out.println(channel + ": " + msg);
            }
        });

        return sessionMap;
    }
}
