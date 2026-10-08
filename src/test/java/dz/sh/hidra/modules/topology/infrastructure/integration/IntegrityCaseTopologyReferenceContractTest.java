/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseTopologyReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrityCaseTopologyReferenceContractTest {
    final PipelineJpaRepository pipeline=mock(PipelineJpaRepository.class);
    final PipelineSegmentJpaRepository segment=mock(PipelineSegmentJpaRepository.class);
    final FacilityJpaRepository facility=mock(FacilityJpaRepository.class);
    final EquipmentJpaRepository equipment=mock(EquipmentJpaRepository.class);
    final TopologyNodeJpaRepository node=mock(TopologyNodeJpaRepository.class);
    final TopologyConnectionJpaRepository connection=mock(TopologyConnectionJpaRepository.class);
    final IntegrityCaseTopologyReferenceQueryAdapter owner=new IntegrityCaseTopologyReferenceQueryAdapter(pipeline,segment,facility,equipment,node,connection);
    @Test void unknownNamespaceBlankAndMissingTargetDenyReference() {
        assertTrue(owner.resolve(null,"id").isEmpty());assertTrue(owner.resolve("PIPELINE",null).isEmpty());assertTrue(owner.resolve("UNKNOWN","id").isEmpty());
        when(pipeline.findById("missing")).thenReturn(Optional.empty());assertTrue(owner.resolve("PIPELINE","missing").isEmpty());
    }
    @Test void eachSupportedNamespaceResolvesItsOwnRealOwnerRepository() {
        var p=mock(PipelineJpaEntity.class);when(p.id()).thenReturn("id");when(p.code()).thenReturn("P");when(pipeline.findById("id")).thenReturn(Optional.of(p));
        var s=mock(PipelineSegmentJpaEntity.class);when(s.id()).thenReturn("id");when(s.code()).thenReturn("S");when(segment.findById("id")).thenReturn(Optional.of(s));
        var f=mock(FacilityJpaEntity.class);when(f.id()).thenReturn("id");when(f.code()).thenReturn("F");when(facility.findById("id")).thenReturn(Optional.of(f));
        var e=mock(EquipmentJpaEntity.class);when(e.id()).thenReturn("id");when(e.code()).thenReturn("E");when(equipment.findById("id")).thenReturn(Optional.of(e));
        var n=mock(TopologyNodeJpaEntity.class);when(n.id()).thenReturn("id");when(n.code()).thenReturn("N");when(node.findById("id")).thenReturn(Optional.of(n));
        var c=mock(TopologyConnectionJpaEntity.class);when(c.id()).thenReturn("id");when(c.code()).thenReturn("C");when(connection.findById("id")).thenReturn(Optional.of(c));
        for(var type:new String[]{"PIPELINE","SEGMENT","FACILITY","EQUIPMENT","NODE","CONNECTION"}) assertEquals("id",owner.resolve(type,"id").orElseThrow().id());
        assertEquals("P",owner.resolve("PIPELINE","id").orElseThrow().code());assertEquals("S",owner.resolve("SEGMENT","id").orElseThrow().code());
    }
    @Test void sameIdInAnotherNamespaceDoesNotProveTheRequestedType() {
        var p=mock(PipelineJpaEntity.class);when(p.id()).thenReturn("id");when(pipeline.findById("id")).thenReturn(Optional.of(p));when(segment.findById("id")).thenReturn(Optional.empty());
        assertTrue(owner.resolve("SEGMENT","id").isEmpty());
    }
}
