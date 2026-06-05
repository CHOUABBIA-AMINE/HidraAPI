/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyApiRestConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.configuration
 *
 * @Description : Spring configuration for topology REST API mapper beans.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;

/**
 * Spring configuration for topology REST API mapper beans.
 *
 * <p>Business role:
 * Provides the REST mapper required by topology controllers for pipeline systems, pipelines,
 * facilities, topology nodes, pipeline segments, pipeline appurtenances, topology connections, and
 * equipment.
 *
 * <p>Architecture role:
 * This API-layer configuration exposes API mapper beans only. It does not wire application services,
 * repositories, persistence adapters, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or platform infrastructure.
 *
 * <p>Validation:
 * Spring creates the mapper only when no other TopologyRestMapper bean already exists.
 *
 * <p>Usage:
 * Discovered by component scanning so topology REST controllers can receive TopologyRestMapper by
 * constructor injection.
 */
@Configuration(proxyBeanMethods = false)
public class TopologyApiRestConfiguration {

    /**
     * Creates the topology REST mapper bean.
     *
     * @return topology REST mapper
     */
    @Bean
    @ConditionalOnMissingBean
    public TopologyRestMapper topologyRestMapper() {
        return new TopologyRestMapper();
    }
}
