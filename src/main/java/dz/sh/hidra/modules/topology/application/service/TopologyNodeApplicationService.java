/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing topology node use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.math.BigDecimal;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyNodeCommand;
import dz.sh.hidra.modules.topology.application.dto.GeoCoordinateDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyNodesUseCase;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.GetTopologyNodeByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;

/**
 * Implements topology node create, get, and list use cases.
 *
 * <p>Business role:
 * Coordinates graph vertex creation for facility nodes, junctions, valve points, injection points,
 * extraction points, purge points, vents, drains, scraper points, receipt points, and delivery points.
 *
 * <p>Architecture role:
 * This application service depends on topology ports and domain services only.
 *
 * <p>Validation:
 * Node code uniqueness is checked. Facility node registration is validated when a facility reference
 * is supplied.
 *
 * <p>Usage:
 * Wire this class as the implementation for topology node use-case ports.
 */
public final class TopologyNodeApplicationService
        implements CreateTopologyNodeUseCase, GetTopologyNodeUseCase, ListTopologyNodesUseCase {

    private final TopologyNodeRepositoryPort topologyNodeRepository;
    private final FacilityRepositoryPort facilityRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public TopologyNodeApplicationService(
            TopologyNodeRepositoryPort topologyNodeRepository,
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        this.topologyNodeRepository = Objects.requireNonNull(
                topologyNodeRepository,
                "Topology node repository port must not be null.");
        this.facilityRepository = Objects.requireNonNull(facilityRepository, "Facility repository port must not be null.");
        this.registrationDomainService = Objects.requireNonNull(
                registrationDomainService,
                "Topology registration domain service must not be null.");
    }

    @Override
    public TopologyNodeDto createTopologyNode(CreateTopologyNodeCommand command) {
        Objects.requireNonNull(command, "Create topology node command must not be null.");

        if (topologyNodeRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Topology node code already exists.");
        }

        TopologyNode node = TopologyNode.create(
                command.code(),
                command.name(),
                command.nodeType(),
                command.facilityId(),
                command.pipelineAppurtenanceId(),
                command.coordinate(),
                command.elevationMeters());

        if (command.facilityId() != null) {
            Facility facility = facilityRepository.findById(command.facilityId())
                    .orElseThrow(() -> new BusinessRuleViolationException("Facility was not found."));
            registrationDomainService.validateFacilityNodeRegistration(facility, node);
        }

        return toDto(topologyNodeRepository.save(node));
    }

    @Override
    public TopologyNodeDto getTopologyNode(GetTopologyNodeByIdQuery query) {
        Objects.requireNonNull(query, "Get topology node query must not be null.");

        return topologyNodeRepository.findById(query.id())
                .map(TopologyNodeApplicationService::toDto)
                .orElseThrow(() -> new BusinessRuleViolationException("Topology node was not found."));
    }

    @Override
    public PageResult<TopologyNodeDto> listTopologyNodes(ListTopologyNodesQuery query) {
        Objects.requireNonNull(query, "List topology nodes query must not be null.");

        return mapPage(topologyNodeRepository.findAll(query));
    }

    private static TopologyNodeDto toDto(TopologyNode node) {
        return new TopologyNodeDto(
                node.id().value(),
                node.code().value(),
                node.name().value(),
                node.nodeType().name(),
                node.facilityId() == null ? null : node.facilityId().value(),
                node.pipelineAppurtenanceId() == null ? null : node.pipelineAppurtenanceId().value(),
                toGeoCoordinateDto(node.coordinate()),
                node.elevationMeters(),
                node.status().name(),
                node.createdAt(),
                node.updatedAt());
    }

    private static GeoCoordinateDto toGeoCoordinateDto(GeoCoordinate coordinate) {
        if (coordinate == null) {
            return null;
        }

        return new GeoCoordinateDto(
                BigDecimal.valueOf(coordinate.latitude()).stripTrailingZeros(),
                BigDecimal.valueOf(coordinate.longitude()).stripTrailingZeros());
    }

    private static PageResult<TopologyNodeDto> mapPage(PageResult<TopologyNode> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(TopologyNodeApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
