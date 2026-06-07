/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationApiRestConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.configuration
 *
 * @Description : Spring configuration for organization REST API mapper beans.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;

/**
 * Spring configuration for organization REST API mapper beans.
 *
 * <p>Business role:
 * Provides the REST mapper required by organization controllers for employees, organization units,
 * positions, assignments, and reporting lines.
 *
 * <p>Architecture role:
 * This API-layer configuration exposes API mapper beans only. It does not wire application services,
 * repositories, persistence adapters, identity implementation, topology implementation, or platform
 * infrastructure.
 *
 * <p>Validation:
 * Spring creates the mapper only when no other OrganizationRestMapper bean already exists.
 *
 * <p>Usage:
 * Discovered by component scanning so organization REST controllers can receive
 * OrganizationRestMapper by constructor injection.
 */
@Configuration(proxyBeanMethods = false)
public class OrganizationApiRestConfiguration {

    /**
     * Creates the organization REST mapper bean.
     *
     * @return organization REST mapper
     */
    @Bean
    @ConditionalOnMissingBean
    OrganizationRestMapper organizationRestMapper() {
        return new OrganizationRestMapper();
    }
}
