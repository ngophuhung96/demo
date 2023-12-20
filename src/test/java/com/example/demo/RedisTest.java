package com.example.demo;

import com.github.fppt.jedismock.RedisServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private RedisServer redisServer;

    @BeforeEach
    void startRedis() throws Exception {
        redisServer = RedisServer.newRedisServer(6379);
        redisServer.start();
    }

    @AfterEach
    void stopRedis() throws Exception {
        redisServer.stop();
    }

    @Test
    void test() {
        String key = "testKey";
        String value = "testValue";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        String result = valueOperations.get(key);
        assertEquals(value, result);
    }
}
