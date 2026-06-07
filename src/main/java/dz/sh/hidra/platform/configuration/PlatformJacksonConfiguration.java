/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformJacksonConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Spring configuration for the platform Jackson ObjectMapper bean.
 *
 */
package dz.sh.hidra.platform.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for the platform Jackson ObjectMapper bean.
 *
 * <p>Business role:
 * Provides the JSON mapper required by platform infrastructure, especially domain event
 * serialization for the outbox/event pipeline.
 *
 * <p>Architecture role:
 * This is platform infrastructure configuration. It exposes a shared ObjectMapper bean without
 * depending on business modules, controllers, repositories, identity, organization, or topology.
 *
 * <p>Validation:
 * The mapper registers available Jackson modules automatically, including Java time support when
 * present on the classpath, and writes dates as ISO values rather than numeric timestamps.
 *
 * <p>Usage:
 * Discovered by Spring component scanning so platform serializers and any other infrastructure
 * beans can receive ObjectMapper by constructor injection.
 */
@Configuration(proxyBeanMethods = false)
public class PlatformJacksonConfiguration {

    /**
     * Creates the shared Jackson ObjectMapper bean.
     *
     * @return configured ObjectMapper
     */
    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    ObjectMapper objectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
