package com.zm.borrowmoneyandriodapp.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/5/31 11:04
 */
@Configuration
@Slf4j
public class CacheConfig {

//    @Autowired
//    CacheManager cacheManager;
//
//    /**
//     * 每周一任务 00:10
//     */
//    @Scheduled(cron = "* 10 0 * * MON")
//    public void everyWeekExecute() {
//        Cache maps = cacheManager.getCache("MAPS");
//        maps.clear();
//    }

    @Bean
    public com.google.common.cache.Cache<String, Object> cache() {
        return CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(7, TimeUnit.DAYS)         // 写入后七天删除
                .expireAfterAccess(3, TimeUnit.DAYS)        // 三天不访问删除
                .initialCapacity(100)    // 设置初始容量为100
                .concurrencyLevel(10) // 设置并发级别为10
                .recordStats() // 开启缓存统计
                .removalListener(new RemovalListener<String, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> rn) {
                        if (log.isInfoEnabled()) {
                            log.info("缓存被手动移除{}:{}", rn.getKey(), rn.getValue());
                        }
                    }
                }).build();

    }


    @Bean
    public com.google.common.cache.Cache<String, Object> limitCache() {
        return CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.SECONDS)         // 写入后10s删除
                .initialCapacity(50)    // 设置初始容量为100
                .concurrencyLevel(10) // 设置并发级别为10
                .recordStats() // 开启缓存统计
                .build();

    }

}
