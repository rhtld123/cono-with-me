package com.go.conowithme.infrastructure.config.redis;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.embedded.RedisServer;

@Profile("local")
@Configuration
public class LocalRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws Exception {
        if (isArmMac()) {
            redisServer = new RedisServer(getRedisFileForArmMac(), redisPort);
        } else {
            redisServer = RedisServer.builder().port(redisPort).setting("maxmemory 128M").build();
        }
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isArmMac() {
        return System.getProperty("os.arch").equals("aarch64") &&
            System.getProperty("os.name").equals("Mac OS X");
    }

    private File getRedisFileForArmMac() throws IOException {
        File file = new ClassPathResource("redis/redis-server-m1-mac").getFile();
        return file;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();  // Lettuce 사용
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
