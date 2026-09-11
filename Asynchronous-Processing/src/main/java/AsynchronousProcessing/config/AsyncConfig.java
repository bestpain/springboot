package AsynchronousProcessing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig {

    @Value("${thread-pool.corePoolSize}")
    private int corePoolSize;

    @Value("${thread-pool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${thread-pool.queueCapacity}")
    private int queueCapacity;

    @Value("${thread-pool.threadNamePrefix}")
    private String threadNamePrefix;

    @Bean
    public ExecutorService customExecutorService() {
        ThreadFactory threadFactory = runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName(threadNamePrefix + " " + thread.getId());
            return thread;
        };

        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity), threadFactory);
    }

    @Bean
    public Executor customThreadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);

        executor.setThreadNamePrefix(threadNamePrefix);

        executor.initialize();

        return executor;
    }
}
