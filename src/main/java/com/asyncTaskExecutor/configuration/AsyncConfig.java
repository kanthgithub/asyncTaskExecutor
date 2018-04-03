package com.asyncTaskExecutor.configuration;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class AsyncConfig {

    @Value("${mobile.thread.core-pool}")
    private int corePoolSize;

    @Value("${mobile.thread.max-pool}")
    private int maxPoolSize;

    @Value("${mobile.queue.capacity}")
    private int queueCapacity;

    @Value("${mobile.thread.timeout}")
    private int threadTimeout;


    @Bean
    @Qualifier("mobileAsyncTaskExecutor")
    WebMvcConfigurer configurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
                threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
                threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
                threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
                threadPoolTaskExecutor.setKeepAliveSeconds(threadTimeout);
                threadPoolTaskExecutor.setThreadNamePrefix("AsyncTaskExecutorDemo-");
                threadPoolTaskExecutor.initialize();
                configurer.setTaskExecutor(threadPoolTaskExecutor);
            }
        };
    }

}