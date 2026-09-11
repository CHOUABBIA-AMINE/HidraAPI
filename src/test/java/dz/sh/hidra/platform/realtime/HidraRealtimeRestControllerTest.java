/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeRestControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Verifies realtime capability publication without inventing unsupported domain event destinations.
 *
 */
package dz.sh.hidra.platform.realtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HidraRealtimeRestControllerTest {

    @Test
    void publishesTransportAndRecoveryMetadataWithoutUnverifiedDomainPublishers() {
        HidraRealtimeRestController controller = new HidraRealtimeRestController(
                new HidraSseRegistry(),
                "/api/v1/realtime/ws",
                "/api/v1/realtime/sse",
                30_000L
        );

        var capabilities = controller.capabilities();

        assertEquals("/api/v1/realtime/ws", capabilities.stompEndpoint());
        assertEquals("query-after-reconnect", capabilities.recoveryStrategy());
        assertEquals("transport-configured-no-domain-publishers", capabilities.publicationStatus());
        assertEquals(2, capabilities.brokerPrefixes().size());
        assertTrue(capabilities.eventFamilies().isEmpty());
    }
}
