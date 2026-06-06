/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyApiRestConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.configuration
 *
 * @Description : Unit tests for topology API REST configuration.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyCatalogTypeUseCase;

/**
 * Unit tests for TopologyApiRestConfiguration.
 */
class TopologyApiRestConfigurationTest {

    @Test
    void shouldCreateTopologyRestMapperBeanWithCatalogResolver() {
        TopologyApiRestConfiguration configuration = new TopologyApiRestConfiguration();

        TopologyRestMapper mapper = configuration.topologyRestMapper(catalogResolver());

        assertNotNull(mapper);
    }

    private static ResolveTopologyCatalogTypeUseCase catalogResolver() {
        return query -> new TopologyCatalogDto(
                query.code().value(),
                query.catalogName(),
                query.code().value(),
                "ACTIVE",
                0,
                true,
                query.locale() == null ? "en" : query.locale(),
                query.code().value(),
                null,
                List.of(),
                Instant.EPOCH,
                Instant.EPOCH);
    }
}
