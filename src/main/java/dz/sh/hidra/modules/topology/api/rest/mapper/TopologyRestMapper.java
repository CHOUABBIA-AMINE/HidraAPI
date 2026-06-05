/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps topology REST contracts to application commands, queries, and responses.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
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
import dz.sh.hidra.modules.topology.api.rest.response.EquipmentResponse;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.api.rest.response.GeoCoordinateResponse;
import dz.sh.hidra.modules.topology.api.rest.response.OperationalOwnerReferenceResponse;
import dz.sh.hidra.modules.topology.api.rest.response.OrganizationUnitReferenceResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineAppurtenanceResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSegmentResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyConnectionResponse;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyNodeResponse;
import dz.sh.hidra.modules.topology.application.command.CreateFacilityCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineAppurtenanceCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSegmentCommand;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyConnectionCommand;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyNodeCommand;
import dz.sh.hidra.modules.topology.application.command.RegisterEquipmentCommand;
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
import dz.sh.hidra.modules.topology.application.query.GetFacilityByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineAppurtenanceByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineSystemByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetTopologyNodeByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.EquipmentType;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Maps topology REST request/response contracts to application commands, queries, and DTOs.
 *
 * <p>Business role:
 * This mapper translates HTTP contracts for pipeline systems, pipelines, physical facilities,
 * topology nodes, pipeline segments, pipeline appurtenances, topology connections, and equipment
 * without leaking REST objects into the application layer.
 *
 * <p>Architecture role:
 * This is an API-layer mapper. It must not access repositories, persistence entities, identity
 * implementation, organization implementation, measurement, flow, risk, workflow, or platform
 * infrastructure.
 *
 * <p>Validation:
 * Bean Validation validates raw REST inputs before mapping. Topology value objects validate business
 * formats during command/query creation.
 *
 * <p>Usage:
 * Controllers use this mapper to call application ports and shape REST responses.
 */
public final class TopologyRestMapper {

    public CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest request) {
        Objects.requireNonNull(request, "Create pipeline system request must not be null.");
        return new CreatePipelineSystemCommand(
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                request.description(),
                ProductType.valueOf(requiredEnum(request.productType())),
                optionalOperationalOwnerReference(request.operationalOwnerReference()));
    }

    public CreatePipelineCommand toCommand(CreatePipelineRequest request) {
        Objects.requireNonNull(request, "Create pipeline request must not be null.");
        return new CreatePipelineCommand(
                PipelineSystemId.of(request.pipelineSystemId()),
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                request.description(),
                ProductType.valueOf(requiredEnum(request.productType())),
                DiameterInInches.of(request.nominalDiameterInches()),
                LengthInKilometers.of(request.designLengthKm()));
    }

    public CreateFacilityCommand toCommand(CreateFacilityRequest request) {
        Objects.requireNonNull(request, "Create facility request must not be null.");
        return new CreateFacilityCommand(
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                FacilityType.valueOf(requiredEnum(request.facilityType())),
                ProductType.valueOf(requiredEnum(request.productType())),
                optionalGeoCoordinate(request.coordinate()),
                optionalOrganizationUnitReference(request.organizationUnitReference()));
    }

    public CreateTopologyNodeCommand toCommand(CreateTopologyNodeRequest request) {
        Objects.requireNonNull(request, "Create topology node request must not be null.");
        return new CreateTopologyNodeCommand(
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                NodeType.valueOf(requiredEnum(request.nodeType())),
                optionalFacilityId(request.facilityId()),
                optionalPipelineAppurtenanceId(request.pipelineAppurtenanceId()),
                optionalGeoCoordinate(request.coordinate()),
                request.elevationMeters());
    }

    public CreatePipelineSegmentCommand toCommand(CreatePipelineSegmentRequest request) {
        Objects.requireNonNull(request, "Create pipeline segment request must not be null.");
        return new CreatePipelineSegmentCommand(
                PipelineId.of(request.pipelineId()),
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                TopologyNodeId.of(request.fromNodeId()),
                TopologyNodeId.of(request.toNodeId()),
                LengthInKilometers.of(request.lengthKm()),
                DiameterInInches.of(request.diameterInches()));
    }

    public CreatePipelineAppurtenanceCommand toCommand(CreatePipelineAppurtenanceRequest request) {
        Objects.requireNonNull(request, "Create pipeline appurtenance request must not be null.");
        return new CreatePipelineAppurtenanceCommand(
                PipelineId.of(request.pipelineId()),
                TopologyNodeId.of(request.nodeId()),
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                PipelineAppurtenanceType.valueOf(requiredEnum(request.appurtenanceType())),
                optionalValveType(request.valveType()),
                PipelineKilometerPoint.of(request.pipelineKilometerPoint()),
                optionalGeoCoordinate(request.coordinate()),
                request.description());
    }

    public CreateTopologyConnectionCommand toCommand(CreateTopologyConnectionRequest request) {
        Objects.requireNonNull(request, "Create topology connection request must not be null.");
        return new CreateTopologyConnectionCommand(
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                TopologyNodeId.of(request.fromNodeId()),
                TopologyNodeId.of(request.toNodeId()),
                ConnectionType.valueOf(requiredEnum(request.connectionType())),
                TopologyAssetType.valueOf(requiredEnum(request.linkedAssetType())),
                request.linkedAssetId());
    }

    public RegisterEquipmentCommand toCommand(RegisterEquipmentRequest request) {
        Objects.requireNonNull(request, "Register equipment request must not be null.");
        return new RegisterEquipmentCommand(
                TopologyCode.of(request.code()),
                TopologyName.of(request.name()),
                EquipmentType.valueOf(requiredEnum(request.equipmentType())),
                TopologyAssetType.valueOf(requiredEnum(request.parentAssetType())),
                request.parentAssetId());
    }

    public GetPipelineSystemByIdQuery toGetPipelineSystemByIdQuery(String pipelineSystemId) {
        return new GetPipelineSystemByIdQuery(PipelineSystemId.of(pipelineSystemId));
    }

    public GetPipelineByIdQuery toGetPipelineByIdQuery(String pipelineId) {
        return new GetPipelineByIdQuery(PipelineId.of(pipelineId));
    }

    public GetFacilityByIdQuery toGetFacilityByIdQuery(String facilityId) {
        return new GetFacilityByIdQuery(FacilityId.of(facilityId));
    }

    public GetTopologyNodeByIdQuery toGetTopologyNodeByIdQuery(String topologyNodeId) {
        return new GetTopologyNodeByIdQuery(TopologyNodeId.of(topologyNodeId));
    }

    public GetPipelineAppurtenanceByIdQuery toGetPipelineAppurtenanceByIdQuery(String pipelineAppurtenanceId) {
        return new GetPipelineAppurtenanceByIdQuery(PipelineAppurtenanceId.of(pipelineAppurtenanceId));
    }

    public ListPipelineSystemsQuery toListPipelineSystemsQuery(
            String searchText,
            String productType,
            String status,
            int page,
            int size) {

        return new ListPipelineSystemsQuery(
                searchText,
                optionalProductType(productType),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public ListPipelinesQuery toListPipelinesQuery(
            String searchText,
            String pipelineSystemId,
            String productType,
            String status,
            int page,
            int size) {

        return new ListPipelinesQuery(
                searchText,
                optionalPipelineSystemId(pipelineSystemId),
                optionalProductType(productType),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public ListFacilitiesQuery toListFacilitiesQuery(
            String searchText,
            String facilityType,
            String productType,
            String status,
            int page,
            int size) {

        return new ListFacilitiesQuery(
                searchText,
                optionalFacilityType(facilityType),
                optionalProductType(productType),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public ListTopologyNodesQuery toListTopologyNodesQuery(
            String searchText,
            String nodeType,
            String facilityId,
            String pipelineAppurtenanceId,
            String status,
            int page,
            int size) {

        return new ListTopologyNodesQuery(
                searchText,
                optionalNodeType(nodeType),
                optionalFacilityId(facilityId),
                optionalPipelineAppurtenanceId(pipelineAppurtenanceId),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public ListPipelineSegmentsQuery toListPipelineSegmentsQuery(
            String searchText,
            String pipelineId,
            String fromNodeId,
            String toNodeId,
            String status,
            int page,
            int size) {

        return new ListPipelineSegmentsQuery(
                searchText,
                optionalPipelineId(pipelineId),
                optionalTopologyNodeId(fromNodeId),
                optionalTopologyNodeId(toNodeId),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public ListPipelineAppurtenancesQuery toListPipelineAppurtenancesQuery(
            String searchText,
            String pipelineId,
            String appurtenanceType,
            String valveType,
            String status,
            int page,
            int size) {

        return new ListPipelineAppurtenancesQuery(
                searchText,
                optionalPipelineId(pipelineId),
                optionalPipelineAppurtenanceType(appurtenanceType),
                optionalValveType(valveType),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public ListTopologyConnectionsQuery toListTopologyConnectionsQuery(
            String searchText,
            String fromNodeId,
            String toNodeId,
            String connectionType,
            String linkedAssetType,
            String status,
            int page,
            int size) {

        return new ListTopologyConnectionsQuery(
                searchText,
                optionalTopologyNodeId(fromNodeId),
                optionalTopologyNodeId(toNodeId),
                optionalConnectionType(connectionType),
                optionalTopologyAssetType(linkedAssetType),
                optionalTopologyStatus(status),
                PageRequest.of(page, size));
    }

    public PipelineSystemResponse toResponse(PipelineSystemDto dto) {
        Objects.requireNonNull(dto, "Pipeline system DTO must not be null.");
        return new PipelineSystemResponse(
                dto.pipelineSystemId(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.productType(),
                dto.status(),
                toOperationalOwnerReferenceResponse(dto.operationalOwnerReference()),
                dto.createdAt(),
                dto.updatedAt());
    }

    public PipelineResponse toResponse(PipelineDto dto) {
        Objects.requireNonNull(dto, "Pipeline DTO must not be null.");
        return new PipelineResponse(
                dto.pipelineId(),
                dto.pipelineSystemId(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.productType(),
                dto.nominalDiameterInches(),
                dto.designLengthKm(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public FacilityResponse toResponse(FacilityDto dto) {
        Objects.requireNonNull(dto, "Facility DTO must not be null.");
        return new FacilityResponse(
                dto.facilityId(),
                dto.code(),
                dto.name(),
                dto.facilityType(),
                dto.productType(),
                dto.status(),
                toResponse(dto.coordinate()),
                toResponse(dto.organizationUnitReference()),
                dto.createdAt(),
                dto.updatedAt());
    }

    public TopologyNodeResponse toResponse(TopologyNodeDto dto) {
        Objects.requireNonNull(dto, "Topology node DTO must not be null.");
        return new TopologyNodeResponse(
                dto.topologyNodeId(),
                dto.code(),
                dto.name(),
                dto.nodeType(),
                dto.facilityId(),
                dto.pipelineAppurtenanceId(),
                toResponse(dto.coordinate()),
                dto.elevationMeters(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public PipelineSegmentResponse toResponse(PipelineSegmentDto dto) {
        Objects.requireNonNull(dto, "Pipeline segment DTO must not be null.");
        return new PipelineSegmentResponse(
                dto.pipelineSegmentId(),
                dto.pipelineId(),
                dto.code(),
                dto.name(),
                dto.fromNodeId(),
                dto.toNodeId(),
                dto.lengthKm(),
                dto.diameterInches(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public PipelineAppurtenanceResponse toResponse(PipelineAppurtenanceDto dto) {
        Objects.requireNonNull(dto, "Pipeline appurtenance DTO must not be null.");
        return new PipelineAppurtenanceResponse(
                dto.pipelineAppurtenanceId(),
                dto.pipelineId(),
                dto.nodeId(),
                dto.code(),
                dto.name(),
                dto.appurtenanceType(),
                dto.valveType(),
                dto.pipelineKilometerPoint(),
                dto.status(),
                toResponse(dto.coordinate()),
                dto.description(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public TopologyConnectionResponse toResponse(TopologyConnectionDto dto) {
        Objects.requireNonNull(dto, "Topology connection DTO must not be null.");
        return new TopologyConnectionResponse(
                dto.topologyConnectionId(),
                dto.code(),
                dto.name(),
                dto.fromNodeId(),
                dto.toNodeId(),
                dto.connectionType(),
                dto.linkedAssetType(),
                dto.linkedAssetId(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public EquipmentResponse toResponse(EquipmentDto dto) {
        Objects.requireNonNull(dto, "Equipment DTO must not be null.");
        return new EquipmentResponse(
                dto.equipmentId(),
                dto.code(),
                dto.name(),
                dto.equipmentType(),
                dto.parentAssetType(),
                dto.parentAssetId(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public GeoCoordinateResponse toResponse(GeoCoordinateDto dto) {
        if (dto == null) {
            return null;
        }

        return new GeoCoordinateResponse(dto.latitude(), dto.longitude());
    }

    public OrganizationUnitReferenceResponse toResponse(OrganizationUnitReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new OrganizationUnitReferenceResponse(
                dto.referenceType(),
                dto.referenceId(),
                dto.referenceCode(),
                dto.referenceName());
    }

    public OperationalOwnerReferenceResponse toOperationalOwnerReferenceResponse(OrganizationUnitReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new OperationalOwnerReferenceResponse(
                dto.referenceType(),
                dto.referenceId(),
                dto.referenceCode(),
                dto.referenceName());
    }

    public PageResult<PipelineSystemResponse> toPipelineSystemResponsePage(PageResult<PipelineSystemDto> pageResult) {
        Objects.requireNonNull(pageResult, "Pipeline system page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<PipelineResponse> toPipelineResponsePage(PageResult<PipelineDto> pageResult) {
        Objects.requireNonNull(pageResult, "Pipeline page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<FacilityResponse> toFacilityResponsePage(PageResult<FacilityDto> pageResult) {
        Objects.requireNonNull(pageResult, "Facility page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<TopologyNodeResponse> toTopologyNodeResponsePage(PageResult<TopologyNodeDto> pageResult) {
        Objects.requireNonNull(pageResult, "Topology node page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<PipelineSegmentResponse> toPipelineSegmentResponsePage(PageResult<PipelineSegmentDto> pageResult) {
        Objects.requireNonNull(pageResult, "Pipeline segment page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<PipelineAppurtenanceResponse> toPipelineAppurtenanceResponsePage(PageResult<PipelineAppurtenanceDto> pageResult) {
        Objects.requireNonNull(pageResult, "Pipeline appurtenance page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<TopologyConnectionResponse> toTopologyConnectionResponsePage(PageResult<TopologyConnectionDto> pageResult) {
        Objects.requireNonNull(pageResult, "Topology connection page result must not be null.");
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    private static GeoCoordinate optionalGeoCoordinate(GeoCoordinateRequest request) {
        if (request == null) {
            return null;
        }

        return GeoCoordinate.of(
                request.latitude().doubleValue(),
                request.longitude().doubleValue());
    }

    private static OrganizationUnitReference optionalOrganizationUnitReference(OrganizationUnitReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return OrganizationUnitReference.of(
                request.referenceType(),
                request.referenceId(),
                request.referenceCode(),
                request.referenceName());
    }

    private static OperationalOwnerReference optionalOperationalOwnerReference(OperationalOwnerReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return OperationalOwnerReference.of(
                request.ownerType(),
                request.ownerId(),
                request.ownerCode(),
                request.ownerName());
    }

    private static PipelineSystemId optionalPipelineSystemId(String value) {
        return blank(value) ? null : PipelineSystemId.of(value);
    }

    private static PipelineId optionalPipelineId(String value) {
        return blank(value) ? null : PipelineId.of(value);
    }

    private static FacilityId optionalFacilityId(String value) {
        return blank(value) ? null : FacilityId.of(value);
    }

    private static TopologyNodeId optionalTopologyNodeId(String value) {
        return blank(value) ? null : TopologyNodeId.of(value);
    }

    private static PipelineAppurtenanceId optionalPipelineAppurtenanceId(String value) {
        return blank(value) ? null : PipelineAppurtenanceId.of(value);
    }

    private static ProductType optionalProductType(String value) {
        return blank(value) ? null : ProductType.valueOf(requiredEnum(value));
    }

    private static FacilityType optionalFacilityType(String value) {
        return blank(value) ? null : FacilityType.valueOf(requiredEnum(value));
    }

    private static NodeType optionalNodeType(String value) {
        return blank(value) ? null : NodeType.valueOf(requiredEnum(value));
    }

    private static PipelineAppurtenanceType optionalPipelineAppurtenanceType(String value) {
        return blank(value) ? null : PipelineAppurtenanceType.valueOf(requiredEnum(value));
    }

    private static ValveType optionalValveType(String value) {
        return blank(value) ? null : ValveType.valueOf(requiredEnum(value));
    }

    private static ConnectionType optionalConnectionType(String value) {
        return blank(value) ? null : ConnectionType.valueOf(requiredEnum(value));
    }

    private static TopologyAssetType optionalTopologyAssetType(String value) {
        return blank(value) ? null : TopologyAssetType.valueOf(requiredEnum(value));
    }

    private static TopologyStatus optionalTopologyStatus(String value) {
        return blank(value) ? null : TopologyStatus.valueOf(requiredEnum(value));
    }

    private static String requiredEnum(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
