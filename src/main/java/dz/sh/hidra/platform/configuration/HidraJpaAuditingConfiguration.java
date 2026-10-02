/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraJpaAuditingConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Enables Spring Data JPA auditing and resolves the current Hidra actor.
 *
 */
package dz.sh.hidra.platform.configuration;


import dz.sh.hidra.platform.security.CurrentActorResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Spring Data JPA auditing configuration.
 */
@Configuration(proxyBeanMethods = false)
@EnableJpaAuditing(auditorAwareRef = "hidraAuditorAware")
public class HidraJpaAuditingConfiguration {

    @Bean
    AuditorAware<String> hidraAuditorAware(CurrentActorResolver currentActorResolver) {
        return () -> Optional.of(currentActorResolver.currentActorId().value());
    }
}
