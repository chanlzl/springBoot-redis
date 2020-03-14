package com.mybatis.generator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName RedisConfig
 * @Author lzl
 * @Description Redis配置类
 * @Date 2020/3/14 14:58
 * @Version 1.0
 **/
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.block-when-exhausted}")
    private boolean  blockWhenExhausted;

    @Bean
    public JedisPool redisPoolFactory()  throws Exception{
        JedisPool jedisPool = null;
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
            // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
            // 是否启用pool的jmx管理功能, 默认true
            jedisPoolConfig.setJmxEnabled(true);
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        } catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("JedisPool注入成功！");
        LOGGER.info("redis地址：" + host + ":" + port);
        return jedisPool;
    }
}
