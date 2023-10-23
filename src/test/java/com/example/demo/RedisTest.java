package com.example.demo;

import com.github.fppt.jedismock.RedisServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedisTest {

    private RedisServer redisServer;
    private RedissonClient redissonClient;

    @BeforeEach
    void beforeTest() throws Exception {
        redisServer = RedisServer.newRedisServer();
        redisServer.start();

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + this.redisServer.getHost() + ":" + this.redisServer.getBindPort());
        redissonClient = Redisson.create(config);
    }

    @AfterEach
    void afterTest() throws IOException {
        redisServer.stop();
    }

    @Test
    public void testMethod() {
        String key = "an-example-key";
        RLock rLock = this.redissonClient.getLock(key);

        rLock.lock();
        assertTrue(rLock.isLocked());
        rLock.unlock();
        assertFalse(rLock.isLocked());
    }
}
