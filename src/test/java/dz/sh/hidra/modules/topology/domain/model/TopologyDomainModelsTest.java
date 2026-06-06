/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyDomainModelsTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Unit tests for topology domain models.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Unit tests for topology domain models.
 */
class TopologyDomainModelsTest {

    @Test
    void shouldCreatePipelineSystemAsPlannedAsset() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();

        assertNotNull(pipelineSystem.id());
        assertEquals(TopologyStatus.PLANNED, pipelineSystem.status());
        assertEquals("GAS", pipelineSystem.productType().name());
        assertEquals("Main system", pipelineSystem.description());
        assertEquals("TRC-OPS-EAST", pipelineSystem.operationalOwnerReference().ownerCode());
    }

    @Test
    void shouldCreateFacilityWithCoordinateAndOrganizationReference() {
        Facility facility = TopologyDomainTestData.facility();

        assertNotNull(facility.id());
        assertEquals(TopologyStatus.PLANNED, facility.status());
        assertEquals("COMPRESSION_STATION", facility.facilityType().name());
        assertEquals("TRC-OPS-EAST-CS-01", facility.organizationUnitReference().referenceCode());
        assertEquals(31.6167, facility.coordinate().latitude());
    }

    @Test
    void shouldCreatePipelineAndPreserveParentSystemReference() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);

        assertEquals(pipelineSystem.id(), pipeline.pipelineSystemId());
        assertEquals(TopologyStatus.PLANNED, pipeline.status());
        assertEquals("GAS", pipeline.productType().name());
        assertEquals("Main pipeline", pipeline.description());
    }

    @Test
    void shouldCreatePipelineAppurtenancesForValveInjectionExtractionAndPurgePoints() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);
        TopologyNode valveNode = TopologyDomainTestData.freeNode(dz.sh.hidra.modules.topology.domain.value.NodeType.PIPELINE_VALVE_POINT, "VALVE");
        TopologyNode injectionNode = TopologyDomainTestData.freeNode(dz.sh.hidra.modules.topology.domain.value.NodeType.INJECTION_POINT, "INJ");
        TopologyNode extractionNode = TopologyDomainTestData.freeNode(dz.sh.hidra.modules.topology.domain.value.NodeType.EXTRACTION_POINT, "EXT");
        TopologyNode purgeNode = TopologyDomainTestData.freeNode(dz.sh.hidra.modules.topology.domain.value.NodeType.PURGE_POINT, "PURGE");

        assertEquals("VALVE", TopologyDomainTestData.valve(pipeline, valveNode).appurtenanceType().name());
        assertEquals("INJECTION_POINT", TopologyDomainTestData.appurtenance(
                pipeline,
                injectionNode,
                PipelineAppurtenanceType.INJECTION_POINT,
                null,
                "INJ").appurtenanceType().name());
        assertEquals("EXTRACTION_POINT", TopologyDomainTestData.appurtenance(
                pipeline,
                extractionNode,
                PipelineAppurtenanceType.EXTRACTION_POINT,
                null,
                "EXT").appurtenanceType().name());
        assertEquals("PURGE_POINT", TopologyDomainTestData.appurtenance(
                pipeline,
                purgeNode,
                PipelineAppurtenanceType.PURGE_POINT,
                null,
                "PURGE").appurtenanceType().name());
    }

    @Test
    void shouldRejectInconsistentPipelineAppurtenanceValveType() {
        assertThrows(BusinessRuleViolationException.class, () -> PipelineAppurtenance.create(
                PipelineId.newId(),
                TopologyNodeId.newId(),
                TopologyCode.of("APP-BAD-VALVE"),
                TopologyName.of("Bad Valve"),
                PipelineAppurtenanceType.VALVE,
                null,
                PipelineKilometerPoint.of(1.0),
                null,
                null));

        assertThrows(BusinessRuleViolationException.class, () -> PipelineAppurtenance.create(
                PipelineId.newId(),
                TopologyNodeId.newId(),
                TopologyCode.of("APP-BAD-INJ"),
                TopologyName.of("Bad Injection"),
                PipelineAppurtenanceType.INJECTION_POINT,
                ValveType.BLOCK_VALVE,
                PipelineKilometerPoint.of(1.0),
                null,
                null));
    }

    @Test
    void shouldRejectRestoredPipelineWhenUpdatedAtIsBeforeCreatedAt() {
        Instant createdAt = Instant.parse("2026-01-02T00:00:00Z");
        Instant updatedAt = Instant.parse("2026-01-01T00:00:00Z");

        assertThrows(BusinessRuleViolationException.class, () -> Pipeline.restore(
                PipelineId.newId(),
                PipelineSystemId.newId(),
                TopologyCode.of("PIPE-BAD-DATES"),
                TopologyName.of("Bad Date Pipeline"),
                null,
                ProductType.GAS,
                DiameterInInches.of(42.0),
                LengthInKilometers.of(20.0),
                TopologyStatus.ACTIVE,
                createdAt,
                updatedAt));
    }
}
