/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySimulationPhysicalNetworkRevisionQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Verifies exact identity normalization, complete DTO mapping and integrity failure propagation.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort.StoredRevision;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopologySimulationPhysicalNetworkRevisionQueryServiceTest {
    @Test
    void mapsEveryOrderedFieldAndStoredDigestWithOneExactOwnerRead() {
        var repository=mock(TopologyPhysicalNetworkRevisionRepositoryPort.class);
        var value=new TopologyPhysicalNetworkRevision("s","r",ScopeType.PIPELINE_SYSTEM,"q",Instant.EPOCH,
                Instant.ofEpochSecond(-1,123),Instant.ofEpochSecond(1,456),Origin.DECLARED_PARAMETER,"evidence",
                List.of(new Node("b",new BigDecimal("-1.00")),new Node("a",BigDecimal.ZERO),new Node("c",BigDecimal.ONE)),
                List.of(new PipeSegment("p","a","b",new BigDecimal("1E+2"),new BigDecimal("0.50"),BigDecimal.ZERO)),
                List.of(new EquipmentLink("e","c","a",EquipmentKind.COMPRESSOR)));
        when(repository.findStored("s","r")).thenReturn(Optional.of(new StoredRevision(value,"HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1","a".repeat(64))));
        var exported=new TopologySimulationPhysicalNetworkRevisionQueryService(repository).find(" s "," r ").orElseThrow();
        assertEquals(value.sourceId(),exported.sourceId()); assertEquals(value.revisionId(),exported.revisionId());
        assertEquals(value.scopeType().name(),exported.scopeType()); assertEquals(value.scopeId(),exported.scopeId());
        assertEquals(value.recordedAt(),exported.recordedAt()); assertEquals(value.effectiveFrom(),exported.effectiveFrom());
        assertEquals(value.effectiveUntil(),exported.effectiveUntil()); assertEquals(value.origin().name(),exported.origin());
        assertEquals(value.evidenceReference(),exported.evidenceReference());
        assertEquals(List.of("b","a","c"),exported.nodes().stream().map(n->n.id()).toList());
        for(int i=0;i<value.nodes().size();i++) assertEquals(value.nodes().get(i).elevationMeters(),exported.nodes().get(i).elevationMeters());
        var p=exported.pipeSegments().get(0); var owner=value.pipeSegments().get(0);
        assertEquals(owner.id(),p.id()); assertEquals(owner.fromNodeId(),p.fromNodeId()); assertEquals(owner.toNodeId(),p.toNodeId());
        assertEquals(owner.lengthMeters(),p.lengthMeters()); assertEquals(owner.internalDiameterMeters(),p.internalDiameterMeters());
        assertEquals(owner.absoluteRoughnessMeters(),p.absoluteRoughnessMeters());
        var e=exported.equipmentLinks().get(0);
        assertEquals("e",e.id()); assertEquals("c",e.fromNodeId()); assertEquals("a",e.toNodeId()); assertEquals("COMPRESSOR",e.kind());
        assertEquals("HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1",exported.payloadFormat()); assertEquals("a".repeat(64),exported.sha256());
        assertNotSame(value.nodes(),exported.nodes());
        assertThrows(UnsupportedOperationException.class,()->exported.nodes().clear());
        verify(repository).findStored("s","r"); verifyNoMoreInteractions(repository);
    }

    @Test
    void preservesMissingExactRevisionWithoutFallback() {
        var repository=mock(TopologyPhysicalNetworkRevisionRepositoryPort.class);
        when(repository.findStored("s","missing")).thenReturn(Optional.empty());
        assertTrue(new TopologySimulationPhysicalNetworkRevisionQueryService(repository).find("s","missing").isEmpty());
        verify(repository).findStored("s","missing"); verifyNoMoreInteractions(repository);
    }

    @Test
    void rejectsMalformedLookupsBeforeOwnerCall() {
        var repository=mock(TopologyPhysicalNetworkRevisionRepositoryPort.class);
        var service=new TopologySimulationPhysicalNetworkRevisionQueryService(repository);
        for(String bad:Arrays.asList(null,""," ","\t\n")) {
            assertThrows(InvalidTopologyValueException.class,()->service.find(bad,"r"));
            assertThrows(InvalidTopologyValueException.class,()->service.find("s",bad));
        }
        verifyNoInteractions(repository);
    }

    @Test
    void doesNotTranslateIntegrityFailureIntoMissing() {
        var repository=mock(TopologyPhysicalNetworkRevisionRepositoryPort.class);
        var failure=new InvalidTopologyValueException("Corrupt stored revision.");
        when(repository.findStored("s","r")).thenThrow(failure);
        assertSame(failure,assertThrows(InvalidTopologyValueException.class,
                ()->new TopologySimulationPhysicalNetworkRevisionQueryService(repository).find("s","r")));
        verify(repository).findStored("s","r"); verifyNoMoreInteractions(repository);
    }
}
