/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing pipeline appurtenance use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.math.BigDecimal;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineAppurtenanceCommand;
import dz.sh.hidra.modules.topology.application.dto.GeoCoordinateDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineAppurtenanceDto;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineAppurtenancesUseCase;
import dz.sh.hidra.modules.topology.application.port.out.PipelineAppurtenanceRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.GetPipelineAppurtenanceByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.service.PipelineAppurtenanceDomainService;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;

/**
 * Implements pipeline appurtenance create, get, and list use cases.
 *
 * <p>Business role:
 * Coordinates registration of valves, injection points, extraction points, purge points, vents,
 * drains, scraper points, metering points, sampling points, hot taps, bypass points, and connection
 * points along pipelines.
 *
 * <p>Architecture role:
 * This application service does not implement operations, permits, telemetry, hydraulic
 * calculations, risk, maintenance, or workflow behavior.
 *
 * <p>Validation:
 * Parent pipeline, topology node, code uniqueness, valve type consistency, KP, and node compatibility
 * are enforced before saving.
 *
 * <p>Usage:
 * Wire this class as the implementation for pipeline appurtenance use-case ports.
 */
public final class PipelineAppurtenanceApplicationService
        implements CreatePipelineAppurtenanceUseCase, GetPipelineAppurtenanceUseCase, ListPipelineAppurtenancesUseCase {

    private final PipelineAppurtenanceRepositoryPort appurtenanceRepository;
    private final PipelineRepositoryPort pipelineRepository;
    private final TopologyNodeRepositoryPort topologyNodeRepository;
    private final PipelineAppurtenanceDomainService appurtenanceDomainService;

    public PipelineAppurtenanceApplicationService(
            PipelineAppurtenanceRepositoryPort appurtenanceRepository,
            PipelineRepositoryPort pipelineRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            PipelineAppurtenanceDomainService appurtenanceDomainService) {

        this.appurtenanceRepository = Objects.requireNonNull(
                appurtenanceRepository,
                "Pipeline appurtenance repository port must not be null.");
        this.pipelineRepository = Objects.requireNonNull(pipelineRepository, "Pipeline repository port must not be null.");
        this.topologyNodeRepository = Objects.requireNonNull(
                topologyNodeRepository,
                "Topology node repository port must not be null.");
        this.appurtenanceDomainService = Objects.requireNonNull(
                appurtenanceDomainService,
                "Pipeline appurtenance domain service must not be null.");
    }

    @Override
    public PipelineAppurtenanceDto createPipelineAppurtenance(CreatePipelineAppurtenanceCommand command) {
        Objects.requireNonNull(command, "Create pipeline appurtenance command must not be null.");

        if (appurtenanceRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Pipeline appurtenance code already exists.");
        }

        Pipeline pipeline = pipelineRepository.findById(command.pipelineId())
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline was not found."));
        TopologyNode node = topologyNodeRepository.findById(command.nodeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline appurtenance node was not found."));

        PipelineAppurtenance appurtenance = PipelineAppurtenance.create(
                command.pipelineId(),
                command.nodeId(),
                command.code(),
                command.name(),
                command.appurtenanceType(),
                command.valveType(),
                command.pipelineKilometerPoint(),
                command.coordinate(),
                command.description());

        appurtenanceDomainService.validateRegistration(pipeline, appurtenance, node);

        return toDto(appurtenanceRepository.save(appurtenance));
    }

    @Override
    public PipelineAppurtenanceDto getPipelineAppurtenance(GetPipelineAppurtenanceByIdQuery query) {
        Objects.requireNonNull(query, "Get pipeline appurtenance query must not be null.");

        return appurtenanceRepository.findById(query.id())
                .map(PipelineAppurtenanceApplicationService::toDto)
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline appurtenance was not found."));
    }

    @Override
    public PageResult<PipelineAppurtenanceDto> listPipelineAppurtenances(ListPipelineAppurtenancesQuery query) {
        Objects.requireNonNull(query, "List pipeline appurtenances query must not be null.");

        return mapPage(appurtenanceRepository.findAll(query));
    }

    private static PipelineAppurtenanceDto toDto(PipelineAppurtenance appurtenance) {
        return new PipelineAppurtenanceDto(
                appurtenance.id().value(),
                appurtenance.pipelineId().value(),
                appurtenance.nodeId().value(),
                appurtenance.code().value(),
                appurtenance.name().value(),
                appurtenance.appurtenanceType().name(),
                appurtenance.valveType() == null ? null : appurtenance.valveType().name(),
                appurtenance.pipelineKilometerPoint().value(),
                appurtenance.status().name(),
                toGeoCoordinateDto(appurtenance.coordinate()),
                appurtenance.description(),
                appurtenance.createdAt(),
                appurtenance.updatedAt());
    }

    private static GeoCoordinateDto toGeoCoordinateDto(GeoCoordinate coordinate) {
        if (coordinate == null) {
            return null;
        }

        return new GeoCoordinateDto(
                BigDecimal.valueOf(coordinate.latitude()).stripTrailingZeros(),
                BigDecimal.valueOf(coordinate.longitude()).stripTrailingZeros());
    }

    private static PageResult<PipelineAppurtenanceDto> mapPage(PageResult<PipelineAppurtenance> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(PipelineAppurtenanceApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
