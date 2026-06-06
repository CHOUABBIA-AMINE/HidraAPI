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
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyCatalogTypeUseCase;

/**
 * Spring configuration for topology REST API mapper beans.
 *
 * <p>Business role:
 * Provides the REST mapper required by topology controllers for pipeline systems, pipelines,
 * facilities, topology nodes, pipeline segments, pipeline appurtenances, topology connections, and
 * equipment.
 *
 * <p>Architecture role:
 * This API-layer configuration wires the mapper to the topology catalog resolver so REST contracts can
 * accept type codes and return localized type labels without depending on persistence.
 *
 * <p>Validation:
 * Spring creates the mapper only when no other TopologyRestMapper bean already exists.
 */
@Configuration(proxyBeanMethods = false)
public class TopologyApiRestConfiguration {

    /**
     * Creates the topology REST mapper bean.
     *
     * @param resolveTopologyCatalogTypeUseCase catalog resolver use case
     * @return topology REST mapper
     */
    @Bean
    @ConditionalOnMissingBean
    public TopologyRestMapper topologyRestMapper(
            ResolveTopologyCatalogTypeUseCase resolveTopologyCatalogTypeUseCase) {

        return new TopologyRestMapper(resolveTopologyCatalogTypeUseCase);
    }
}
