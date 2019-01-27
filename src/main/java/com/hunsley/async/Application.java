package com.hunsley.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * <p>
     *     Set the thread pool size used to execute the Async calls to the Account services
     *
     *     In this example we have 4 {@link AccountType} enumerations which are requested in
     *     individual threads. Therefore the pool size is set to 4 so all 4 types can be
     *     interrogated concurrently
     * </p>
     * @return
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Spring-Async-");
        executor.initialize();
        return executor;
    }
}
