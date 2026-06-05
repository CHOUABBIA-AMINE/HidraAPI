/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing pipeline segment use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSegmentCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineSegmentDto;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSegmentUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSegmentsUseCase;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSegmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;

/**
 * Implements pipeline segment create and list use cases.
 *
 * <p>Business role:
 * Coordinates registration of physical pipe sections between topology nodes.
 *
 * <p>Architecture role:
 * This application service depends on topology ports and domain services only.
 *
 * <p>Validation:
 * Parent pipeline existence, endpoint node existence, code uniqueness, and endpoint consistency are
 * enforced before saving.
 *
 * <p>Usage:
 * Wire this class as the implementation for pipeline segment use-case ports.
 */
public final class PipelineSegmentApplicationService
        implements CreatePipelineSegmentUseCase, ListPipelineSegmentsUseCase {

    private final PipelineSegmentRepositoryPort pipelineSegmentRepository;
    private final PipelineRepositoryPort pipelineRepository;
    private final TopologyNodeRepositoryPort topologyNodeRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public PipelineSegmentApplicationService(
            PipelineSegmentRepositoryPort pipelineSegmentRepository,
            PipelineRepositoryPort pipelineRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        this.pipelineSegmentRepository = Objects.requireNonNull(
                pipelineSegmentRepository,
                "Pipeline segment repository port must not be null.");
        this.pipelineRepository = Objects.requireNonNull(pipelineRepository, "Pipeline repository port must not be null.");
        this.topologyNodeRepository = Objects.requireNonNull(
                topologyNodeRepository,
                "Topology node repository port must not be null.");
        this.registrationDomainService = Objects.requireNonNull(
                registrationDomainService,
                "Topology registration domain service must not be null.");
    }

    @Override
    public PipelineSegmentDto createPipelineSegment(CreatePipelineSegmentCommand command) {
        Objects.requireNonNull(command, "Create pipeline segment command must not be null.");

        if (pipelineSegmentRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Pipeline segment code already exists.");
        }

        Pipeline pipeline = pipelineRepository.findById(command.pipelineId())
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline was not found."));
        TopologyNode fromNode = topologyNodeRepository.findById(command.fromNodeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline segment from-node was not found."));
        TopologyNode toNode = topologyNodeRepository.findById(command.toNodeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline segment to-node was not found."));

        PipelineSegment segment = PipelineSegment.create(
                command.pipelineId(),
                command.code(),
                command.name(),
                command.fromNodeId(),
                command.toNodeId(),
                command.length(),
                command.diameter());

        registrationDomainService.validatePipelineSegmentRegistration(pipeline, segment, fromNode, toNode);

        return toDto(pipelineSegmentRepository.save(segment));
    }

    @Override
    public PageResult<PipelineSegmentDto> listPipelineSegments(ListPipelineSegmentsQuery query) {
        Objects.requireNonNull(query, "List pipeline segments query must not be null.");

        return mapPage(pipelineSegmentRepository.findAll(query));
    }

    private static PipelineSegmentDto toDto(PipelineSegment segment) {
        return new PipelineSegmentDto(
                segment.id().value(),
                segment.pipelineId().value(),
                segment.code().value(),
                segment.name().value(),
                segment.fromNodeId().value(),
                segment.toNodeId().value(),
                segment.length().value(),
                segment.diameter().value(),
                segment.status().name(),
                segment.createdAt(),
                segment.updatedAt());
    }

    private static PageResult<PipelineSegmentDto> mapPage(PageResult<PipelineSegment> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(PipelineSegmentApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
