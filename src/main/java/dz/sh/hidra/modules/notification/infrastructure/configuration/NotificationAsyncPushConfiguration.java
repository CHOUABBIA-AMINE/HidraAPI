/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAsyncPushConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.configuration
 *
 * @Description : Configures the asynchronous notification push executor.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configures the asynchronous notification push executor.
 */
@Configuration
@EnableAsync(proxyTargetClass = false)
public class NotificationAsyncPushConfiguration {

    @Bean(name = "notificationPushExecutor")
    Executor notificationPushExecutor(
            @Value("${hidra.notification.async-push.core-pool-size:4}") int corePoolSize,
            @Value("${hidra.notification.async-push.max-pool-size:12}") int maxPoolSize,
            @Value("${hidra.notification.async-push.queue-capacity:500}") int queueCapacity,
            @Value("${hidra.notification.async-push.thread-name-prefix:HidraNotificationPush-}") String threadNamePrefix
    ) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }
}
