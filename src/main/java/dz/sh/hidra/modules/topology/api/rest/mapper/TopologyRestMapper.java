/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
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

import java.time.Instant;
import java.util.List;
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
import dz.sh.hidra.modules.topology.api.rest.response.TopologyTypeReferenceResponse;
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
import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyConnectionDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyCatalogTypeUseCase;
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
import dz.sh.hidra.modules.topology.application.query.ResolveTopologyCatalogTypeQuery;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.EquipmentTypeReference;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualDescription;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualName;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveTypeReference;

/**
 * Maps topology REST request/response contracts to application commands, queries, and DTOs.
 */
public final class TopologyRestMapper {

    private static final String DEFAULT_LOCALE = "en";
    private static final String PRODUCT_TYPE = "PRODUCT_TYPE";
    private static final String FACILITY_TYPE = "FACILITY_TYPE";
    private static final String NODE_TYPE = "NODE_TYPE";
    private static final String APPURTENANCE_TYPE = "PIPELINE_APPURTENANCE_TYPE";
    private static final String VALVE_TYPE = "VALVE_TYPE";
    private static final String EQUIPMENT_TYPE = "EQUIPMENT_TYPE";
    private static final String CONNECTION_TYPE = "CONNECTION_TYPE";

    private final ResolveTopologyCatalogTypeUseCase resolveTopologyCatalogTypeUseCase;

    public TopologyRestMapper(ResolveTopologyCatalogTypeUseCase resolveTopologyCatalogTypeUseCase) {
        this.resolveTopologyCatalogTypeUseCase = Objects.requireNonNull(resolveTopologyCatalogTypeUseCase, "Resolve topology catalog type use case must not be null.");
    }

    @Deprecated(forRemoval = true)
    public TopologyRestMapper() {
        this.resolveTopologyCatalogTypeUseCase = null;
    }

    public CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Create pipeline system request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new CreatePipelineSystemCommand(TopologyCode.of(request.code()), TopologyName.of(request.name()), request.description(), productTypeReferenceForCreation(request.productTypeCode(), locale), optionalOperationalOwnerReference(request.operationalOwnerReference()));
    }

    public CreatePipelineCommand toCommand(CreatePipelineRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public CreatePipelineCommand toCommand(CreatePipelineRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Create pipeline request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new CreatePipelineCommand(
                PipelineSystemId.of(request.pipelineSystemId()),
                TopologyCode.of(request.code()),
                TopologyMultilingualName.of(request.nameAr(), request.nameFr(), request.nameEn()),
                TopologyMultilingualDescription.of(request.descriptionAr(), request.descriptionFr(), request.descriptionEn()),
                productTypeReferenceForCreation(request.productTypeCode(), locale),
                DiameterInInches.of(request.nominalDiameterInches()),
                LengthInKilometers.of(request.designLengthKm()));
    }

    public CreateFacilityCommand toCommand(CreateFacilityRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public CreateFacilityCommand toCommand(CreateFacilityRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Create facility request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new CreateFacilityCommand(TopologyCode.of(request.code()), TopologyName.of(request.name()), facilityTypeReferenceForCreation(request.facilityTypeCode(), locale), productTypeReferenceForCreation(request.productTypeCode(), locale), optionalGeoCoordinate(request.coordinate()), optionalOrganizationUnitReference(request.organizationUnitReference()));
    }

    public CreateTopologyNodeCommand toCommand(CreateTopologyNodeRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public CreateTopologyNodeCommand toCommand(CreateTopologyNodeRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Create topology node request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new CreateTopologyNodeCommand(TopologyCode.of(request.code()), TopologyName.of(request.name()), nodeTypeReferenceForCreation(request.nodeTypeCode(), locale), optionalFacilityId(request.facilityId()), optionalPipelineAppurtenanceId(request.pipelineAppurtenanceId()), optionalGeoCoordinate(request.coordinate()), request.elevationMeters());
    }

    public CreatePipelineSegmentCommand toCommand(CreatePipelineSegmentRequest request) {
        Objects.requireNonNull(request, "Create pipeline segment request must not be null.");
        return new CreatePipelineSegmentCommand(PipelineId.of(request.pipelineId()), TopologyCode.of(request.code()), TopologyName.of(request.name()), TopologyNodeId.of(request.fromNodeId()), TopologyNodeId.of(request.toNodeId()), LengthInKilometers.of(request.lengthKm()), DiameterInInches.of(request.diameterInches()));
    }

    public CreatePipelineAppurtenanceCommand toCommand(CreatePipelineAppurtenanceRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public CreatePipelineAppurtenanceCommand toCommand(CreatePipelineAppurtenanceRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Create pipeline appurtenance request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new CreatePipelineAppurtenanceCommand(PipelineId.of(request.pipelineId()), TopologyNodeId.of(request.nodeId()), TopologyCode.of(request.code()), TopologyName.of(request.name()), appurtenanceTypeReferenceForCreation(request.appurtenanceTypeCode(), locale), optionalValveTypeReferenceForCreation(request.valveTypeCode(), locale), PipelineKilometerPoint.of(request.pipelineKilometerPoint()), optionalGeoCoordinate(request.coordinate()), request.description());
    }

    public CreateTopologyConnectionCommand toCommand(CreateTopologyConnectionRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public CreateTopologyConnectionCommand toCommand(CreateTopologyConnectionRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Create topology connection request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new CreateTopologyConnectionCommand(TopologyCode.of(request.code()), TopologyName.of(request.name()), TopologyNodeId.of(request.fromNodeId()), TopologyNodeId.of(request.toNodeId()), connectionTypeReferenceForCreation(request.connectionTypeCode(), locale), TopologyAssetType.valueOf(requiredEnum(request.linkedAssetType())), request.linkedAssetId());
    }

    public RegisterEquipmentCommand toCommand(RegisterEquipmentRequest request) { return toCommand(request, DEFAULT_LOCALE); }

    public RegisterEquipmentCommand toCommand(RegisterEquipmentRequest request, String acceptLanguage) {
        Objects.requireNonNull(request, "Register equipment request must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new RegisterEquipmentCommand(TopologyCode.of(request.code()), TopologyName.of(request.name()), equipmentTypeReferenceForCreation(request.equipmentTypeCode(), locale), TopologyAssetType.valueOf(requiredEnum(request.parentAssetType())), request.parentAssetId());
    }

    public GetPipelineSystemByIdQuery toGetPipelineSystemByIdQuery(String pipelineSystemId) { return new GetPipelineSystemByIdQuery(PipelineSystemId.of(pipelineSystemId)); }
    public GetPipelineByIdQuery toGetPipelineByIdQuery(String pipelineId) { return new GetPipelineByIdQuery(PipelineId.of(pipelineId)); }
    public GetFacilityByIdQuery toGetFacilityByIdQuery(String facilityId) { return new GetFacilityByIdQuery(FacilityId.of(facilityId)); }
    public GetTopologyNodeByIdQuery toGetTopologyNodeByIdQuery(String topologyNodeId) { return new GetTopologyNodeByIdQuery(TopologyNodeId.of(topologyNodeId)); }
    public GetPipelineAppurtenanceByIdQuery toGetPipelineAppurtenanceByIdQuery(String pipelineAppurtenanceId) { return new GetPipelineAppurtenanceByIdQuery(PipelineAppurtenanceId.of(pipelineAppurtenanceId)); }

    public ListPipelineSystemsQuery toListPipelineSystemsQuery(String searchText, String productTypeCode, String status, int page, int size) { return toListPipelineSystemsQuery(searchText, productTypeCode, status, page, size, DEFAULT_LOCALE); }

    public ListPipelineSystemsQuery toListPipelineSystemsQuery(String searchText, String productTypeCode, String status, int page, int size, String acceptLanguage) {
        String locale = preferredLocale(acceptLanguage);
        return new ListPipelineSystemsQuery(searchText, optionalProductType(productTypeCode, locale), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public ListPipelinesQuery toListPipelinesQuery(String searchText, String pipelineSystemId, String productTypeCode, String status, int page, int size) { return toListPipelinesQuery(searchText, pipelineSystemId, productTypeCode, status, page, size, DEFAULT_LOCALE); }

    public ListPipelinesQuery toListPipelinesQuery(String searchText, String pipelineSystemId, String productTypeCode, String status, int page, int size, String acceptLanguage) {
        String locale = preferredLocale(acceptLanguage);
        return new ListPipelinesQuery(searchText, optionalPipelineSystemId(pipelineSystemId), optionalProductType(productTypeCode, locale), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public ListFacilitiesQuery toListFacilitiesQuery(String searchText, String facilityTypeCode, String productTypeCode, String status, int page, int size) { return toListFacilitiesQuery(searchText, facilityTypeCode, productTypeCode, status, page, size, DEFAULT_LOCALE); }

    public ListFacilitiesQuery toListFacilitiesQuery(String searchText, String facilityTypeCode, String productTypeCode, String status, int page, int size, String acceptLanguage) {
        String locale = preferredLocale(acceptLanguage);
        return new ListFacilitiesQuery(searchText, optionalFacilityType(facilityTypeCode, locale), optionalProductType(productTypeCode, locale), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public ListTopologyNodesQuery toListTopologyNodesQuery(String searchText, String nodeTypeCode, String facilityId, String pipelineAppurtenanceId, String status, int page, int size) { return toListTopologyNodesQuery(searchText, nodeTypeCode, facilityId, pipelineAppurtenanceId, status, page, size, DEFAULT_LOCALE); }

    public ListTopologyNodesQuery toListTopologyNodesQuery(String searchText, String nodeTypeCode, String facilityId, String pipelineAppurtenanceId, String status, int page, int size, String acceptLanguage) {
        String locale = preferredLocale(acceptLanguage);
        return new ListTopologyNodesQuery(searchText, optionalNodeType(nodeTypeCode, locale), optionalFacilityId(facilityId), optionalPipelineAppurtenanceId(pipelineAppurtenanceId), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public ListPipelineSegmentsQuery toListPipelineSegmentsQuery(String searchText, String pipelineId, String fromNodeId, String toNodeId, String status, int page, int size) {
        return new ListPipelineSegmentsQuery(searchText, optionalPipelineId(pipelineId), optionalTopologyNodeId(fromNodeId), optionalTopologyNodeId(toNodeId), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public ListPipelineAppurtenancesQuery toListPipelineAppurtenancesQuery(String searchText, String pipelineId, String appurtenanceTypeCode, String valveTypeCode, String status, int page, int size) { return toListPipelineAppurtenancesQuery(searchText, pipelineId, appurtenanceTypeCode, valveTypeCode, status, page, size, DEFAULT_LOCALE); }

    public ListPipelineAppurtenancesQuery toListPipelineAppurtenancesQuery(String searchText, String pipelineId, String appurtenanceTypeCode, String valveTypeCode, String status, int page, int size, String acceptLanguage) {
        String locale = preferredLocale(acceptLanguage);
        return new ListPipelineAppurtenancesQuery(searchText, optionalPipelineId(pipelineId), optionalPipelineAppurtenanceType(appurtenanceTypeCode, locale), optionalValveType(valveTypeCode, locale), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public ListTopologyConnectionsQuery toListTopologyConnectionsQuery(String searchText, String fromNodeId, String toNodeId, String connectionTypeCode, String linkedAssetType, String status, int page, int size) { return toListTopologyConnectionsQuery(searchText, fromNodeId, toNodeId, connectionTypeCode, linkedAssetType, status, page, size, DEFAULT_LOCALE); }

    public ListTopologyConnectionsQuery toListTopologyConnectionsQuery(String searchText, String fromNodeId, String toNodeId, String connectionTypeCode, String linkedAssetType, String status, int page, int size, String acceptLanguage) {
        String locale = preferredLocale(acceptLanguage);
        return new ListTopologyConnectionsQuery(searchText, optionalTopologyNodeId(fromNodeId), optionalTopologyNodeId(toNodeId), optionalConnectionType(connectionTypeCode, locale), optionalTopologyAssetType(linkedAssetType), optionalTopologyStatus(status), PageRequest.of(page, size));
    }

    public PipelineSystemResponse toResponse(PipelineSystemDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public PipelineSystemResponse toResponse(PipelineSystemDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Pipeline system DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new PipelineSystemResponse(dto.pipelineSystemId(), dto.code(), dto.name(), dto.description(), toTypeResponse(PRODUCT_TYPE, dto.productType(), locale), dto.status(), toOperationalOwnerReferenceResponse(dto.operationalOwnerReference()), dto.createdAt(), dto.updatedAt());
    }

    public PipelineResponse toResponse(PipelineDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public PipelineResponse toResponse(PipelineDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Pipeline DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new PipelineResponse(dto.pipelineId(), dto.pipelineSystemId(), dto.code(), dto.nameAr(), dto.nameFr(), dto.nameEn(), dto.descriptionAr(), dto.descriptionFr(), dto.descriptionEn(), toTypeResponse(PRODUCT_TYPE, dto.productType(), locale), dto.nominalDiameterInches(), dto.designLengthKm(), dto.status(), dto.createdAt(), dto.updatedAt());
    }

    public FacilityResponse toResponse(FacilityDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public FacilityResponse toResponse(FacilityDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Facility DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new FacilityResponse(dto.facilityId(), dto.code(), dto.name(), toTypeResponse(FACILITY_TYPE, dto.facilityType(), locale), toTypeResponse(PRODUCT_TYPE, dto.productType(), locale), dto.status(), toResponse(dto.coordinate()), toResponse(dto.organizationUnitReference()), dto.createdAt(), dto.updatedAt());
    }

    public TopologyNodeResponse toResponse(TopologyNodeDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public TopologyNodeResponse toResponse(TopologyNodeDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Topology node DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new TopologyNodeResponse(dto.topologyNodeId(), dto.code(), dto.name(), toTypeResponse(NODE_TYPE, dto.nodeType(), locale), dto.facilityId(), dto.pipelineAppurtenanceId(), toResponse(dto.coordinate()), dto.elevationMeters(), dto.status(), dto.createdAt(), dto.updatedAt());
    }

    public PipelineSegmentResponse toResponse(PipelineSegmentDto dto) {
        Objects.requireNonNull(dto, "Pipeline segment DTO must not be null.");
        return new PipelineSegmentResponse(dto.pipelineSegmentId(), dto.pipelineId(), dto.code(), dto.name(), dto.fromNodeId(), dto.toNodeId(), dto.lengthKm(), dto.diameterInches(), dto.status(), dto.createdAt(), dto.updatedAt());
    }

    public PipelineAppurtenanceResponse toResponse(PipelineAppurtenanceDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public PipelineAppurtenanceResponse toResponse(PipelineAppurtenanceDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Pipeline appurtenance DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new PipelineAppurtenanceResponse(dto.pipelineAppurtenanceId(), dto.pipelineId(), dto.nodeId(), dto.code(), dto.name(), toTypeResponse(APPURTENANCE_TYPE, dto.appurtenanceType(), locale), toOptionalTypeResponse(VALVE_TYPE, dto.valveType(), locale), dto.pipelineKilometerPoint(), dto.status(), toResponse(dto.coordinate()), dto.description(), dto.createdAt(), dto.updatedAt());
    }

    public TopologyConnectionResponse toResponse(TopologyConnectionDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public TopologyConnectionResponse toResponse(TopologyConnectionDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Topology connection DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new TopologyConnectionResponse(dto.topologyConnectionId(), dto.code(), dto.name(), dto.fromNodeId(), dto.toNodeId(), toTypeResponse(CONNECTION_TYPE, dto.connectionType(), locale), dto.linkedAssetType(), dto.linkedAssetId(), dto.status(), dto.createdAt(), dto.updatedAt());
    }

    public EquipmentResponse toResponse(EquipmentDto dto) { return toResponse(dto, DEFAULT_LOCALE); }

    public EquipmentResponse toResponse(EquipmentDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Equipment DTO must not be null.");
        String locale = preferredLocale(acceptLanguage);
        return new EquipmentResponse(dto.equipmentId(), dto.code(), dto.name(), toTypeResponse(EQUIPMENT_TYPE, dto.equipmentType(), locale), dto.parentAssetType(), dto.parentAssetId(), dto.status(), dto.createdAt(), dto.updatedAt());
    }

    public GeoCoordinateResponse toResponse(GeoCoordinateDto dto) { return dto == null ? null : new GeoCoordinateResponse(dto.latitude(), dto.longitude()); }

    public OrganizationUnitReferenceResponse toResponse(OrganizationUnitReferenceDto dto) {
        if (dto == null) { return null; }
        return new OrganizationUnitReferenceResponse(dto.referenceType(), dto.referenceId(), dto.referenceCode(), dto.referenceName());
    }

    public OperationalOwnerReferenceResponse toOperationalOwnerReferenceResponse(OrganizationUnitReferenceDto dto) {
        if (dto == null) { return null; }
        return new OperationalOwnerReferenceResponse(dto.referenceType(), dto.referenceId(), dto.referenceCode(), dto.referenceName());
    }

    public PageResult<PipelineSystemResponse> toPipelineSystemResponsePage(PageResult<PipelineSystemDto> pageResult) { return toPipelineSystemResponsePage(pageResult, DEFAULT_LOCALE); }

    public PageResult<PipelineSystemResponse> toPipelineSystemResponsePage(PageResult<PipelineSystemDto> pageResult, String acceptLanguage) {
        Objects.requireNonNull(pageResult, "Pipeline system page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(dto -> toResponse(dto, acceptLanguage)).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    public PageResult<PipelineResponse> toPipelineResponsePage(PageResult<PipelineDto> pageResult) { return toPipelineResponsePage(pageResult, DEFAULT_LOCALE); }

    public PageResult<PipelineResponse> toPipelineResponsePage(PageResult<PipelineDto> pageResult, String acceptLanguage) {
        Objects.requireNonNull(pageResult, "Pipeline page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(dto -> toResponse(dto, acceptLanguage)).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    public PageResult<FacilityResponse> toFacilityResponsePage(PageResult<FacilityDto> pageResult) { return toFacilityResponsePage(pageResult, DEFAULT_LOCALE); }

    public PageResult<FacilityResponse> toFacilityResponsePage(PageResult<FacilityDto> pageResult, String acceptLanguage) {
        Objects.requireNonNull(pageResult, "Facility page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(dto -> toResponse(dto, acceptLanguage)).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    public PageResult<TopologyNodeResponse> toTopologyNodeResponsePage(PageResult<TopologyNodeDto> pageResult) { return toTopologyNodeResponsePage(pageResult, DEFAULT_LOCALE); }

    public PageResult<TopologyNodeResponse> toTopologyNodeResponsePage(PageResult<TopologyNodeDto> pageResult, String acceptLanguage) {
        Objects.requireNonNull(pageResult, "Topology node page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(dto -> toResponse(dto, acceptLanguage)).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    public PageResult<PipelineSegmentResponse> toPipelineSegmentResponsePage(PageResult<PipelineSegmentDto> pageResult) {
        Objects.requireNonNull(pageResult, "Pipeline segment page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(this::toResponse).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    public PageResult<PipelineAppurtenanceResponse> toPipelineAppurtenanceResponsePage(PageResult<PipelineAppurtenanceDto> pageResult) { return toPipelineAppurtenanceResponsePage(pageResult, DEFAULT_LOCALE); }

    public PageResult<PipelineAppurtenanceResponse> toPipelineAppurtenanceResponsePage(PageResult<PipelineAppurtenanceDto> pageResult, String acceptLanguage) {
        Objects.requireNonNull(pageResult, "Pipeline appurtenance page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(dto -> toResponse(dto, acceptLanguage)).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    public PageResult<TopologyConnectionResponse> toTopologyConnectionResponsePage(PageResult<TopologyConnectionDto> pageResult) { return toTopologyConnectionResponsePage(pageResult, DEFAULT_LOCALE); }

    public PageResult<TopologyConnectionResponse> toTopologyConnectionResponsePage(PageResult<TopologyConnectionDto> pageResult, String acceptLanguage) {
        Objects.requireNonNull(pageResult, "Topology connection page result must not be null.");
        return PageResult.of(pageResult.items().stream().map(dto -> toResponse(dto, acceptLanguage)).toList(), pageResult.page(), pageResult.size(), pageResult.totalElements());
    }

    private ProductTypeReference productTypeReferenceForCreation(String code, String locale) { TopologyCatalogDto dto = resolveForCreation(PRODUCT_TYPE, code, locale); return ProductTypeReference.of(dto.id(), dto.code()); }

    private ProductTypeReference optionalProductType(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForRead(PRODUCT_TYPE, code, locale); return ProductTypeReference.of(dto.id(), dto.code()); }

    private FacilityTypeReference facilityTypeReferenceForCreation(String code, String locale) { TopologyCatalogDto dto = resolveForCreation(FACILITY_TYPE, code, locale); return FacilityTypeReference.of(dto.id(), dto.code()); }

    private FacilityTypeReference optionalFacilityType(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForRead(FACILITY_TYPE, code, locale); return FacilityTypeReference.of(dto.id(), dto.code()); }

    private NodeTypeReference nodeTypeReferenceForCreation(String code, String locale) { TopologyCatalogDto dto = resolveForCreation(NODE_TYPE, code, locale); return NodeTypeReference.of(dto.id(), dto.code()); }

    private NodeTypeReference optionalNodeType(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForRead(NODE_TYPE, code, locale); return NodeTypeReference.of(dto.id(), dto.code()); }

    private PipelineAppurtenanceTypeReference appurtenanceTypeReferenceForCreation(String code, String locale) { TopologyCatalogDto dto = resolveForCreation(APPURTENANCE_TYPE, code, locale); return PipelineAppurtenanceTypeReference.of(dto.id(), dto.code()); }

    private PipelineAppurtenanceTypeReference optionalPipelineAppurtenanceType(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForRead(APPURTENANCE_TYPE, code, locale); return PipelineAppurtenanceTypeReference.of(dto.id(), dto.code()); }

    private ValveTypeReference optionalValveTypeReferenceForCreation(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForCreation(VALVE_TYPE, code, locale); return ValveTypeReference.of(dto.id(), dto.code()); }

    private ValveTypeReference optionalValveType(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForRead(VALVE_TYPE, code, locale); return ValveTypeReference.of(dto.id(), dto.code()); }

    private ConnectionTypeReference connectionTypeReferenceForCreation(String code, String locale) { TopologyCatalogDto dto = resolveForCreation(CONNECTION_TYPE, code, locale); return ConnectionTypeReference.of(dto.id(), dto.code()); }

    private ConnectionTypeReference optionalConnectionType(String code, String locale) { if (blank(code)) { return null; } TopologyCatalogDto dto = resolveForRead(CONNECTION_TYPE, code, locale); return ConnectionTypeReference.of(dto.id(), dto.code()); }

    private EquipmentTypeReference equipmentTypeReferenceForCreation(String code, String locale) { TopologyCatalogDto dto = resolveForCreation(EQUIPMENT_TYPE, code, locale); return EquipmentTypeReference.of(dto.id(), dto.code()); }

    private TopologyTypeReferenceResponse toTypeResponse(String catalogName, String code, String locale) { TopologyCatalogDto dto = resolveForRead(catalogName, code, locale); return new TopologyTypeReferenceResponse(dto.id(), dto.code(), dto.localizedName(), dto.locale()); }

    private TopologyTypeReferenceResponse toOptionalTypeResponse(String catalogName, String code, String locale) { if (blank(code)) { return null; } return toTypeResponse(catalogName, code, locale); }

    private TopologyCatalogDto resolveForCreation(String catalogName, String code, String locale) { return resolveCatalogType(ResolveTopologyCatalogTypeQuery.forCreation(catalogName, TopologyCode.of(code), locale)); }

    private TopologyCatalogDto resolveForRead(String catalogName, String code, String locale) { return resolveCatalogType(ResolveTopologyCatalogTypeQuery.forRead(catalogName, TopologyCode.of(code), locale)); }

    private TopologyCatalogDto resolveCatalogType(ResolveTopologyCatalogTypeQuery query) {
        if (resolveTopologyCatalogTypeUseCase == null) {
            String code = query.code().value();
            String locale = query.locale() == null ? DEFAULT_LOCALE : query.locale();
            return new TopologyCatalogDto(code, query.catalogName(), code, "ACTIVE", 0, true, locale, code, null, List.of(), Instant.EPOCH, Instant.EPOCH);
        }
        return resolveTopologyCatalogTypeUseCase.resolveTopologyCatalogType(query);
    }

    private static GeoCoordinate optionalGeoCoordinate(GeoCoordinateRequest request) { return request == null ? null : GeoCoordinate.of(request.latitude().doubleValue(), request.longitude().doubleValue()); }

    private static OrganizationUnitReference optionalOrganizationUnitReference(OrganizationUnitReferenceRequest request) {
        if (request == null) { return null; }
        return OrganizationUnitReference.of(request.referenceType(), request.referenceId(), request.referenceCode(), request.referenceName());
    }

    private static OperationalOwnerReference optionalOperationalOwnerReference(OperationalOwnerReferenceRequest request) {
        if (request == null) { return null; }
        return OperationalOwnerReference.of(request.ownerType(), request.ownerId(), request.ownerCode(), request.ownerName());
    }

    private static PipelineSystemId optionalPipelineSystemId(String value) { return blank(value) ? null : PipelineSystemId.of(value); }
    private static PipelineId optionalPipelineId(String value) { return blank(value) ? null : PipelineId.of(value); }
    private static FacilityId optionalFacilityId(String value) { return blank(value) ? null : FacilityId.of(value); }
    private static TopologyNodeId optionalTopologyNodeId(String value) { return blank(value) ? null : TopologyNodeId.of(value); }
    private static PipelineAppurtenanceId optionalPipelineAppurtenanceId(String value) { return blank(value) ? null : PipelineAppurtenanceId.of(value); }
    private static TopologyAssetType optionalTopologyAssetType(String value) { return blank(value) ? null : TopologyAssetType.valueOf(requiredEnum(value)); }
    private static TopologyStatus optionalTopologyStatus(String value) { return blank(value) ? null : TopologyStatus.valueOf(requiredEnum(value)); }

    private static String preferredLocale(String acceptLanguage) {
        if (blank(acceptLanguage)) { return DEFAULT_LOCALE; }
        String firstLanguage = acceptLanguage.split(",", 2)[0].trim();
        if (firstLanguage.isBlank()) { return DEFAULT_LOCALE; }
        return firstLanguage.replace('_', '-').toLowerCase(Locale.ROOT);
    }

    private static String requiredEnum(String value) { return value.trim().toUpperCase(Locale.ROOT); }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
}
