/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing pipeline use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineDto;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelinesUseCase;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.GetPipelineByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualDescription;

/**
 * Implements pipeline create, get, and list use cases.
 */
public final class PipelineApplicationService
        implements CreatePipelineUseCase, GetPipelineUseCase, ListPipelinesUseCase {

    private final PipelineRepositoryPort pipelineRepository;
    private final PipelineSystemRepositoryPort pipelineSystemRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public PipelineApplicationService(
            PipelineRepositoryPort pipelineRepository,
            PipelineSystemRepositoryPort pipelineSystemRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        this.pipelineRepository = Objects.requireNonNull(pipelineRepository, "Pipeline repository port must not be null.");
        this.pipelineSystemRepository = Objects.requireNonNull(
                pipelineSystemRepository,
                "Pipeline system repository port must not be null.");
        this.registrationDomainService = Objects.requireNonNull(
                registrationDomainService,
                "Topology registration domain service must not be null.");
    }

    @Override
    public PipelineDto createPipeline(CreatePipelineCommand command) {
        Objects.requireNonNull(command, "Create pipeline command must not be null.");

        if (pipelineRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Pipeline code already exists.");
        }

        PipelineSystem pipelineSystem = pipelineSystemRepository.findById(command.pipelineSystemId())
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline system was not found."));

        Pipeline pipeline = Pipeline.create(
                command.pipelineSystemId(),
                command.code(),
                command.name(),
                command.description(),
                command.productType(),
                command.nominalDiameter(),
                command.designLength());

        registrationDomainService.validatePipelineRegistration(pipelineSystem, pipeline);

        return toDto(pipelineRepository.save(pipeline));
    }

    @Override
    public PipelineDto getPipeline(GetPipelineByIdQuery query) {
        Objects.requireNonNull(query, "Get pipeline query must not be null.");

        return pipelineRepository.findById(query.id())
                .map(PipelineApplicationService::toDto)
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline was not found."));
    }

    @Override
    public PageResult<PipelineDto> listPipelines(ListPipelinesQuery query) {
        Objects.requireNonNull(query, "List pipelines query must not be null.");

        return mapPage(pipelineRepository.findAll(query));
    }

    private static PipelineDto toDto(Pipeline pipeline) {
        TopologyMultilingualDescription description = pipeline.description();
        return new PipelineDto(
                pipeline.id().value(),
                pipeline.pipelineSystemId().value(),
                pipeline.code().value(),
                pipeline.name().nameAr(),
                pipeline.name().nameFr(),
                pipeline.name().nameEn(),
                description == null ? null : description.descriptionAr(),
                description == null ? null : description.descriptionFr(),
                description == null ? null : description.descriptionEn(),
                pipeline.productType().name(),
                pipeline.nominalDiameter().value(),
                pipeline.designLength().value(),
                pipeline.status().name(),
                pipeline.createdAt(),
                pipeline.updatedAt());
    }

    private static PageResult<PipelineDto> mapPage(PageResult<Pipeline> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(PipelineApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
