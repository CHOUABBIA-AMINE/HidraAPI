/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditingConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence.configuration
 *
 * @Description : Enables technical JPA auditing with the current platform actor identifier.
 *
 */
package dz.sh.hidra.platform.persistence.configuration;

import dz.sh.hidra.platform.security.context.CurrentActorResolver;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration(proxyBeanMethods = false)
@EnableJpaAuditing(auditorAwareRef = "platformAuditorAware")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> platformAuditorAware(CurrentActorResolver currentActorResolver) {
        return () -> Optional.of(currentActorResolver.currentActorId().value());
    }
}
