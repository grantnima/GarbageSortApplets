package com.grant.outsourcing.gs.config;

import com.google.common.base.Strings;
import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

/**
 * Grant 2019年10月16日
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redisson.redis.address}")
    private String address;
    private int connectionMinimumIdleSize = 10;
    private int idleConnectionTimeout=10000;
    private int pingTimeout=1000;
    private int connectTimeout=10000;
    private int timeout=3000;
    private int retryAttempts=3;
    private int retryInterval=1500;
    private int reconnectionTimeout=3000;
    private int failedAttempts=3;
    @Value("${spring.redisson.redis.password}")
    private String password;
    private int subscriptionsPerConnection=5;
    private String clientName=null;
    private int subscriptionConnectionMinimumIdleSize = 1;
    private int subscriptionConnectionPoolSize = 50;
    private int connectionPoolSize = 128;
    private int database = 0;
    // private boolean dnsMonitoring = false;
    private int dnsMonitoringInterval = 5000;

    private int thread; //当前处理核数量 * 2

    private String codec="org.redisson.codec.JsonJacksonCodec";

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() throws Exception {
        if (Strings.isNullOrEmpty(password)) {
            password = null;
        }
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database)
                .setDnsMonitoringInterval(dnsMonitoringInterval)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
                .setFailedAttempts(failedAttempts)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setReconnectionTimeout(reconnectionTimeout)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setPassword(password);
        Codec codec=(Codec) ClassUtils.forName(this.codec, ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setThreads(thread);
        config.setEventLoopGroup(new NioEventLoopGroup());
        // config.setUseLinuxNativeEpoll(false);
        return Redisson.create(config);
    }
}
