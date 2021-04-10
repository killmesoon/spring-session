package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200)
@ComponentScan("com")
public class SpringSessionConfig extends AbstractSecurityWebApplicationInitializer {
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(0);
        factory.setHostName("192.168.51.101");
        factory.setPort(6379);
        return factory;
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy () {
        return new HeaderHttpSessionStrategy();
    }
}
