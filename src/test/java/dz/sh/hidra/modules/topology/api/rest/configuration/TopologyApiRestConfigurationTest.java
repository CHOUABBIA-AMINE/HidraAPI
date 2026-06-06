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

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;

/**
 * Unit tests for TopologyApiRestConfiguration.
 *
 * <p>Business role:
 * Verifies that the API REST configuration can provide the topology REST mapper bean required by
 * controllers.
 *
 * <p>Architecture role:
 * This is a direct configuration unit test. It does not start Spring Boot.
 *
 * <p>Validation:
 * Prevents the missing mapper bean startup issue by checking the configuration factory method.
 */
class TopologyApiRestConfigurationTest {

    @Test
    void shouldCreateTopologyRestMapperBean() {
        TopologyApiRestConfiguration configuration = new TopologyApiRestConfiguration();

        TopologyRestMapper mapper = configuration.topologyRestMapper();

        assertNotNull(mapper);
    }
}
