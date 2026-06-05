/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing topology connection use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyConnectionCommand;
import dz.sh.hidra.modules.topology.application.dto.TopologyConnectionDto;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyConnectionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyConnectionsUseCase;
import dz.sh.hidra.modules.topology.application.port.out.TopologyConnectionRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;

/**
 * Implements topology connection create and list use cases.
 *
 * <p>Business role:
 * Coordinates explicit physical graph edges between topology nodes.
 *
 * <p>Architecture role:
 * This application service depends on topology ports and domain services only.
 *
 * <p>Validation:
 * Code uniqueness, endpoint existence, endpoint consistency, and linked asset consistency are
 * enforced before saving.
 *
 * <p>Usage:
 * Wire this class as the implementation for topology connection use-case ports.
 */
public final class TopologyConnectionApplicationService
        implements CreateTopologyConnectionUseCase, ListTopologyConnectionsUseCase {

    private final TopologyConnectionRepositoryPort topologyConnectionRepository;
    private final TopologyNodeRepositoryPort topologyNodeRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public TopologyConnectionApplicationService(
            TopologyConnectionRepositoryPort topologyConnectionRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        this.topologyConnectionRepository = Objects.requireNonNull(
                topologyConnectionRepository,
                "Topology connection repository port must not be null.");
        this.topologyNodeRepository = Objects.requireNonNull(
                topologyNodeRepository,
                "Topology node repository port must not be null.");
        this.registrationDomainService = Objects.requireNonNull(
                registrationDomainService,
                "Topology registration domain service must not be null.");
    }

    @Override
    public TopologyConnectionDto createTopologyConnection(CreateTopologyConnectionCommand command) {
        Objects.requireNonNull(command, "Create topology connection command must not be null.");

        if (topologyConnectionRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Topology connection code already exists.");
        }

        TopologyNode fromNode = topologyNodeRepository.findById(command.fromNodeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Topology connection from-node was not found."));
        TopologyNode toNode = topologyNodeRepository.findById(command.toNodeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Topology connection to-node was not found."));

        TopologyConnection connection = TopologyConnection.create(
                command.code(),
                command.name(),
                command.fromNodeId(),
                command.toNodeId(),
                command.connectionType(),
                command.linkedAssetType(),
                command.linkedAssetId());

        registrationDomainService.validateTopologyConnectionRegistration(connection, fromNode, toNode);

        return toDto(topologyConnectionRepository.save(connection));
    }

    @Override
    public PageResult<TopologyConnectionDto> listTopologyConnections(ListTopologyConnectionsQuery query) {
        Objects.requireNonNull(query, "List topology connections query must not be null.");

        return mapPage(topologyConnectionRepository.findAll(query));
    }

    private static TopologyConnectionDto toDto(TopologyConnection connection) {
        return new TopologyConnectionDto(
                connection.id().value(),
                connection.code().value(),
                connection.name().value(),
                connection.fromNodeId().value(),
                connection.toNodeId().value(),
                connection.connectionType().name(),
                connection.linkedAssetType().name(),
                connection.linkedAssetId(),
                connection.status().name(),
                connection.createdAt(),
                connection.updatedAt());
    }

    private static PageResult<TopologyConnectionDto> mapPage(PageResult<TopologyConnection> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(TopologyConnectionApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
