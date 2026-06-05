/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityApiRestConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.configuration
 *
 * @Description : Spring configuration for identity REST API mapper beans.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;

/**
 * Spring configuration for identity REST API mapper beans.
 *
 * <p>Business role:
 * Provides the REST mapper required by identity controllers for users, roles, permissions, and
 * permission evaluation endpoints.
 *
 * <p>Architecture role:
 * This API-layer configuration exposes API mapper beans only. It does not wire application services,
 * repositories, persistence adapters, security filters, identity policies, topology classes, or
 * platform infrastructure.
 *
 * <p>Validation:
 * Spring creates the mapper only when no other IdentityRestMapper bean already exists.
 *
 * <p>Usage:
 * Discovered by component scanning so identity REST controllers can receive IdentityRestMapper by
 * constructor injection.
 */
@Configuration(proxyBeanMethods = false)
public class IdentityApiRestConfiguration {

    /**
     * Creates the identity REST mapper bean.
     *
     * @return identity REST mapper
     */
    @Bean
    @ConditionalOnMissingBean
    public IdentityRestMapper identityRestMapper() {
        return new IdentityRestMapper();
    }
}
