/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeResolverConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.configuration
 *
 * @Description : Provides a fail-closed operational-scope resolver fallback.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.configuration;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Provides a fallback resolver only when no authoritative owner resolver bean exists.
 *
 * <p>The fallback deliberately supports no entity-backed scope type. This keeps the
 * application context bootable while preserving fail-closed registration semantics:
 * GLOBAL may still be registered, while entity-backed scopes remain rejected until
 * an approved owner resolver is installed.</p>
 */
@Configuration(proxyBeanMethods = false)
public class OperationalScopeResolverConfiguration {

    @Bean
    @ConditionalOnMissingBean(OperationalScopeTargetResolverPort.class)
    OperationalScopeTargetResolverPort failClosedOperationalScopeTargetResolver() {
        return new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return false;
            }

            @Override
            public Optional<ResolvedTarget> resolve(
                    OperationalScopeType type,
                    String targetId
            ) {
                return Optional.empty();
            }
        };
    }
}
