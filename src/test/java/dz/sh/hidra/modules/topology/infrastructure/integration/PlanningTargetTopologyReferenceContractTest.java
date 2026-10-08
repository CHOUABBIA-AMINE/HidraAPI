/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTargetTopologyReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Verifies accepted Batch 19 owner evidence and semantic integrity.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanningTargetTopologyReferenceContractTest {
    @Test void everyTypedOwnerNamespaceResolvesWithoutAliasingOrGuessing() {
        var systems=mock(PipelineSystemJpaRepository.class);var pipelines=mock(PipelineJpaRepository.class);
        var segments=mock(PipelineSegmentJpaRepository.class);var facilities=mock(FacilityJpaRepository.class);
        var equipment=mock(EquipmentJpaRepository.class);var nodes=mock(TopologyNodeJpaRepository.class);
        var connections=mock(TopologyConnectionJpaRepository.class);
        var system=mock(PipelineSystemJpaEntity.class);when(system.id()).thenReturn("system");when(system.code()).thenReturn("SYS");
        var pipeline=mock(PipelineJpaEntity.class);when(pipeline.id()).thenReturn("pipeline");when(pipeline.code()).thenReturn("PIPE");
        var segment=mock(PipelineSegmentJpaEntity.class);when(segment.id()).thenReturn("segment");when(segment.code()).thenReturn("SEG");
        var facility=mock(FacilityJpaEntity.class);when(facility.id()).thenReturn("facility");when(facility.code()).thenReturn("FAC");
        var machine=mock(EquipmentJpaEntity.class);when(machine.id()).thenReturn("equipment");when(machine.code()).thenReturn("EQ");
        var node=mock(TopologyNodeJpaEntity.class);when(node.id()).thenReturn("node");when(node.code()).thenReturn("N");
        var connection=mock(TopologyConnectionJpaEntity.class);when(connection.id()).thenReturn("connection");when(connection.code()).thenReturn("C");
        when(systems.findById("system")).thenReturn(Optional.of(system));when(pipelines.findById("pipeline")).thenReturn(Optional.of(pipeline));
        when(segments.findById("segment")).thenReturn(Optional.of(segment));when(facilities.findById("facility")).thenReturn(Optional.of(facility));
        when(equipment.findById("equipment")).thenReturn(Optional.of(machine));when(nodes.findById("node")).thenReturn(Optional.of(node));
        when(connections.findById("connection")).thenReturn(Optional.of(connection));
        var provider=new PlanningTargetTopologyReferenceQueryAdapter(systems,pipelines,segments,facilities,equipment,nodes,connections);
        assertEquals("SYS",provider.resolve("PIPELINE_SYSTEM","system").orElseThrow().code());
        assertEquals("PIPE",provider.resolve("PIPELINE","pipeline").orElseThrow().code());
        assertEquals("SEG",provider.resolve("SEGMENT","segment").orElseThrow().code());
        assertEquals("FAC",provider.resolve("FACILITY","facility").orElseThrow().code());
        assertEquals("EQ",provider.resolve("EQUIPMENT","equipment").orElseThrow().code());
        assertEquals("N",provider.resolve("NODE","node").orElseThrow().code());
        assertEquals("C",provider.resolve("CONNECTION","connection").orElseThrow().code());
        when(pipelines.findById("mismatch")).thenReturn(Optional.of(pipeline));
        assertTrue(provider.resolve("PIPELINE","mismatch").isEmpty());
        assertTrue(provider.resolve("FACILITY","pipeline").isEmpty());assertTrue(provider.resolve("PIPELINE_SEGMENT","segment").isEmpty());
        assertTrue(provider.resolve("PIPELINE","missing").isEmpty());assertTrue(provider.resolve(null,"pipeline").isEmpty());
    }
}
