/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestTestData
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Test data factory for topology REST mapper and controller tests.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.api.rest.request.CreateFacilityRequest;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineAppurtenanceRequest;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineRequest;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSegmentRequest;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSystemRequest;
import dz.sh.hidra.modules.topology.api.rest.request.CreateTopologyConnectionRequest;
import dz.sh.hidra.modules.topology.api.rest.request.CreateTopologyNodeRequest;
import dz.sh.hidra.modules.topology.api.rest.request.GeoCoordinateRequest;
import dz.sh.hidra.modules.topology.api.rest.request.OperationalOwnerReferenceRequest;
import dz.sh.hidra.modules.topology.api.rest.request.OrganizationUnitReferenceRequest;
import dz.sh.hidra.modules.topology.api.rest.request.RegisterEquipmentRequest;
import dz.sh.hidra.modules.topology.application.dto.EquipmentDto;
import dz.sh.hidra.modules.topology.application.dto.FacilityDto;
import dz.sh.hidra.modules.topology.application.dto.GeoCoordinateDto;
import dz.sh.hidra.modules.topology.application.dto.OrganizationUnitReferenceDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineAppurtenanceDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSegmentDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyConnectionDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;

/**
 * Test data factory for topology REST mapper and controller tests.
 */
public final class TopologyRestTestData {

    public static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");
    public static final Instant UPDATED_AT = Instant.parse("2026-01-02T00:00:00Z");

    private TopologyRestTestData() {
    }

    public static GeoCoordinateRequest coordinateRequest() {
        return new GeoCoordinateRequest(new BigDecimal("31.6167"), new BigDecimal("2.2167"));
    }

    public static GeoCoordinateDto coordinateDto() {
        return new GeoCoordinateDto(new BigDecimal("31.6167"), new BigDecimal("2.2167"));
    }

    public static OperationalOwnerReferenceRequest operationalOwnerReferenceRequest() {
        return new OperationalOwnerReferenceRequest(
                "ORGANIZATION_UNIT",
                "ou-east",
                "TRC-OPS-EAST",
                "Operational East Region");
    }

    public static OrganizationUnitReferenceRequest organizationUnitReferenceRequest() {
        return new OrganizationUnitReferenceRequest(
                "ORGANIZATION_UNIT",
                "ou-station",
                "TRC-OPS-EAST-CS-01",
                "Compression Station East 01");
    }

    public static OrganizationUnitReferenceDto organizationUnitReferenceDto() {
        return new OrganizationUnitReferenceDto(
                "ORGANIZATION_UNIT",
                "ou-station",
                "TRC-OPS-EAST-CS-01",
                "Compression Station East 01");
    }

    public static CreatePipelineSystemRequest createPipelineSystemRequest() {
        return new CreatePipelineSystemRequest(
                "GZ1",
                "Gas Pipeline System GZ1",
                " Main gas transportation system ",
                "gas",
                operationalOwnerReferenceRequest());
    }

    public static CreatePipelineRequest createPipelineRequest() {
        return new CreatePipelineRequest(
                "ps-1",
                "GZ1-LINE-A",
                "خط الغاز الرئيسي أ",
                "Ligne principale gaz A",
                "GZ1 Main Line A",
                "خط نقل رئيسي للغاز.",
                "Ligne principale de transport de gaz.",
                "Main gas transportation line.",
                "gas",
                new BigDecimal("42.000"),
                new BigDecimal("512.300"));
    }

    public static CreateFacilityRequest createFacilityRequest() {
        return new CreateFacilityRequest(
                "CS-EAST-01",
                "Compression Station East 01",
                "compression_station",
                "gas",
                coordinateRequest(),
                organizationUnitReferenceRequest());
    }

    public static CreateTopologyNodeRequest createTopologyNodeRequest() {
        return new CreateTopologyNodeRequest(
                "NODE-CS-EAST-01-IN",
                "Compression Station East 01 Inlet",
                "facility_inlet",
                "fac-1",
                null,
                coordinateRequest(),
                new BigDecimal("725.300"));
    }

    public static CreatePipelineSegmentRequest createPipelineSegmentRequest() {
        return new CreatePipelineSegmentRequest(
                "pipe-1",
                "GZ1-SEG-001",
                "GZ1 Segment KP000-KP025",
                "node-1",
                "node-2",
                new BigDecimal("25.000"),
                new BigDecimal("42.000"));
    }

    public static CreatePipelineAppurtenanceRequest createPipelineAppurtenanceRequest() {
        return new CreatePipelineAppurtenanceRequest(
                "pipe-1",
                "node-valve-1",
                "GZ1-BV-001",
                "Block Valve KP 25",
                "valve",
                "block_valve",
                new BigDecimal("25.000"),
                coordinateRequest(),
                " Main line block valve ");
    }

    public static CreatePipelineAppurtenanceRequest createInjectionPointRequest() {
        return new CreatePipelineAppurtenanceRequest(
                "pipe-1",
                "node-inj-1",
                "GZ1-INJ-001",
                "Injection Point KP 30",
                "injection_point",
                null,
                new BigDecimal("30.000"),
                coordinateRequest(),
                " Injection point ");
    }

    public static CreateTopologyConnectionRequest createTopologyConnectionRequest() {
        return new CreateTopologyConnectionRequest(
                "CONN-GZ1-001",
                "GZ1 Segment Connection 001",
                "node-1",
                "node-2",
                "pipeline_segment",
                "segment",
                "seg-1");
    }

    public static RegisterEquipmentRequest registerEquipmentRequest() {
        return new RegisterEquipmentRequest(
                "CMP-CS-EAST-01-A",
                "Compressor A",
                "compressor",
                "facility",
                "fac-1");
    }

    public static PipelineSystemDto pipelineSystemDto() {
        return new PipelineSystemDto(
                "ps-1",
                "GZ1",
                "Gas Pipeline System GZ1",
                "Main gas transportation system",
                "GAS",
                "PLANNED",
                organizationUnitReferenceDto(),
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineDto pipelineDto() {
        return new PipelineDto(
                "pipe-1",
                "ps-1",
                "GZ1-LINE-A",
                "خط الغاز الرئيسي أ",
                "Ligne principale gaz A",
                "GZ1 Main Line A",
                "خط نقل رئيسي للغاز.",
                "Ligne principale de transport de gaz.",
                "Main gas transportation line.",
                "GAS",
                new BigDecimal("42.000"),
                new BigDecimal("512.300"),
                "PLANNED",
                CREATED_AT,
                UPDATED_AT);
    }

    public static FacilityDto facilityDto() {
        return new FacilityDto(
                "fac-1",
                "CS-EAST-01",
                "Compression Station East 01",
                "COMPRESSION_STATION",
                "GAS",
                "PLANNED",
                coordinateDto(),
                organizationUnitReferenceDto(),
                CREATED_AT,
                UPDATED_AT);
    }

    public static TopologyNodeDto topologyNodeDto() {
        return new TopologyNodeDto(
                "node-1",
                "NODE-CS-EAST-01-IN",
                "Compression Station East 01 Inlet",
                "FACILITY_INLET",
                "fac-1",
                null,
                coordinateDto(),
                new BigDecimal("725.300"),
                "PLANNED",
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineSegmentDto pipelineSegmentDto() {
        return new PipelineSegmentDto(
                "seg-1",
                "pipe-1",
                "GZ1-SEG-001",
                "GZ1 Segment KP000-KP025",
                "node-1",
                "node-2",
                new BigDecimal("25.000"),
                new BigDecimal("42.000"),
                "PLANNED",
                CREATED_AT,
                UPDATED_AT);
    }

    public static PipelineAppurtenanceDto pipelineAppurtenanceDto() {
        return new PipelineAppurtenanceDto(
                "app-1",
                "pipe-1",
                "node-valve-1",
                "GZ1-BV-001",
                "Block Valve KP 25",
                "VALVE",
                "BLOCK_VALVE",
                new BigDecimal("25.000"),
                coordinateDto(),
                "Main line block valve",
                "PLANNED",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TopologyConnectionDto topologyConnectionDto() {
        return new TopologyConnectionDto(
                "conn-1",
                "CONN-GZ1-001",
                "GZ1 Segment Connection 001",
                "node-1",
                "node-2",
                "PIPELINE_SEGMENT",
                "SEGMENT",
                "seg-1",
                "PLANNED",
                CREATED_AT,
                UPDATED_AT);
    }

    public static EquipmentDto equipmentDto() {
        return new EquipmentDto(
                "eq-1",
                "CMP-CS-EAST-01-A",
                "Compressor A",
                "COMPRESSOR",
                "FACILITY",
                "fac-1",
                "PLANNED",
                CREATED_AT,
                UPDATED_AT);
    }

    public static PageResult<PipelineSystemDto> pipelineSystemPage() {
        return PageResult.of(List.of(pipelineSystemDto()), 0, 20, 1);
    }

    public static PageResult<PipelineDto> pipelinePage() {
        return PageResult.of(List.of(pipelineDto()), 0, 20, 1);
    }

    public static PageResult<FacilityDto> facilityPage() {
        return PageResult.of(List.of(facilityDto()), 0, 20, 1);
    }

    public static PageResult<TopologyNodeDto> topologyNodePage() {
        return PageResult.of(List.of(topologyNodeDto()), 0, 20, 1);
    }

    public static PageResult<PipelineSegmentDto> pipelineSegmentPage() {
        return PageResult.of(List.of(pipelineSegmentDto()), 0, 20, 1);
    }

    public static PageResult<PipelineAppurtenanceDto> pipelineAppurtenancePage() {
        return PageResult.of(List.of(pipelineAppurtenanceDto()), 0, 20, 1);
    }

    public static PageResult<TopologyConnectionDto> topologyConnectionPage() {
        return PageResult.of(List.of(topologyConnectionDto()), 0, 20, 1);
    }

    public static PageResult<EquipmentDto> equipmentPage() {
        return PageResult.of(List.of(equipmentDto()), 0, 20, 1);
    }
}
