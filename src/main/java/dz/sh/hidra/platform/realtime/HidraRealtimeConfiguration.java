/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Configures Hidra realtime WebSocket and STOMP broker endpoints.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configures Hidra realtime WebSocket and STOMP broker endpoints.
 */
@Configuration(proxyBeanMethods = false)
@EnableScheduling
@EnableWebSocketMessageBroker
public class HidraRealtimeConfiguration implements WebSocketMessageBrokerConfigurer {

    private final String allowedOrigins;

    public HidraRealtimeConfiguration(
            @Value("${hidra.platform.security.cors.allowed-origins:}") String allowedOrigins
    ) {
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/realtime/ws")
                .setAllowedOriginPatterns(originPatterns().toArray(String[]::new));
    }

    private List<String> originPatterns() {
        if (allowedOrigins == null || allowedOrigins.isBlank()) {
            return List.of("*");
        }
        return Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .toList();
    }
}
