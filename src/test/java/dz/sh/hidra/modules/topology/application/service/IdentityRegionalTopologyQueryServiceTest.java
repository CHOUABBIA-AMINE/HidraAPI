/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRegionalTopologyQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Verifies T33 regional read scope, deny precedence and owner evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyRelationshipPort;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IdentityRegionalTopologyQueryServiceTest {
    private final RegionalTopologyRelationshipPort port = mock(RegionalTopologyRelationshipPort.class);
    private final IdentityRegionalTopologyQueryService service = new IdentityRegionalTopologyQueryService(port);
    private final Instant at = Instant.parse("2026-11-20T12:00:00Z");

    @Test void currentOwnerVerifiedSegmentInheritsOnlyTheExistingPipelineAndSystem() {
        var ancestry = List.of(new IdentityRegionalTopologyContract.Scope("PIPELINE_SYSTEM", "sys"),
                new IdentityRegionalTopologyContract.Scope("PIPELINE", "pipe"));
        var resolution = new IdentityRegionalTopologyContract.Resolution(
                IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT, "seg", "company", ancestry);
        when(port.resolve(IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT, "seg", "company", at))
                .thenReturn(Optional.of(resolution));
        assertEquals(Optional.of(resolution), service.resolve(
                IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT, "seg", "company", at));
    }

    @Test void foreignOwnerAndUnprovenRelationshipsRemainInvisible() {
        when(port.resolve(IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT, "seg", "company", at))
                .thenReturn(Optional.of(new IdentityRegionalTopologyContract.Resolution(
                        IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT, "seg", "foreign",
                        List.of(new IdentityRegionalTopologyContract.Scope("PIPELINE", "p")))));
        assertTrue(service.resolve(IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT,
                "seg", "company", at).isEmpty());
        when(port.resolve(any(), anyString(), anyString(), any())).thenThrow(
                new IllegalStateException("private owner catalog unavailable"));
        assertTrue(service.resolve(IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT,
                "seg", "company", at).isEmpty());
    }

    @Test void invalidOrMissingOwnerQueriesCannotCreateSyntheticLineage() {
        assertTrue(service.resolve(null, "seg", "co", at).isEmpty());
        assertTrue(service.resolve(IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT,
                "", "co", at).isEmpty());
        assertTrue(service.resolve(IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT,
                "seg", "co", null).isEmpty());
        verifyNoInteractions(port);
    }
}
