/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing pipeline system use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.dto.OrganizationUnitReferenceDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemDto;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSystemsUseCase;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.GetPipelineSystemByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;

/**
 * Implements pipeline system create, get, and list use cases.
 *
 * <p>Business role:
 * Coordinates creation and retrieval of physical hydrocarbon transportation systems.
 *
 * <p>Architecture role:
 * This application service depends on inbound use-case contracts, outbound ports, domain models, and
 * application DTOs only. It does not depend on REST, JPA, Spring, identity, organization
 * implementation, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Code uniqueness is checked through the outbound port. Domain invariants remain protected by
 * topology domain values and models.
 *
 * <p>Usage:
 * Wire this class as the implementation for pipeline system use-case ports.
 */
public final class PipelineSystemApplicationService
        implements CreatePipelineSystemUseCase, GetPipelineSystemUseCase, ListPipelineSystemsUseCase {

    private final PipelineSystemRepositoryPort pipelineSystemRepository;

    public PipelineSystemApplicationService(PipelineSystemRepositoryPort pipelineSystemRepository) {
        this.pipelineSystemRepository = Objects.requireNonNull(
                pipelineSystemRepository,
                "Pipeline system repository port must not be null.");
    }

    @Override
    public PipelineSystemDto createPipelineSystem(CreatePipelineSystemCommand command) {
        Objects.requireNonNull(command, "Create pipeline system command must not be null.");

        if (pipelineSystemRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Pipeline system code already exists.");
        }

        PipelineSystem pipelineSystem = PipelineSystem.create(
                command.code(),
                command.name(),
                command.description(),
                command.productType(),
                command.operationalOwnerReference());

        return toDto(pipelineSystemRepository.save(pipelineSystem));
    }

    @Override
    public PipelineSystemDto getPipelineSystem(GetPipelineSystemByIdQuery query) {
        Objects.requireNonNull(query, "Get pipeline system query must not be null.");

        return pipelineSystemRepository.findById(query.id())
                .map(PipelineSystemApplicationService::toDto)
                .orElseThrow(() -> new BusinessRuleViolationException("Pipeline system was not found."));
    }

    @Override
    public PageResult<PipelineSystemDto> listPipelineSystems(ListPipelineSystemsQuery query) {
        Objects.requireNonNull(query, "List pipeline systems query must not be null.");

        return mapPage(pipelineSystemRepository.findAll(query));
    }

    private static PipelineSystemDto toDto(PipelineSystem pipelineSystem) {
        return new PipelineSystemDto(
                pipelineSystem.id().value(),
                pipelineSystem.code().value(),
                pipelineSystem.name().value(),
                pipelineSystem.description(),
                pipelineSystem.productType().name(),
                pipelineSystem.status().name(),
                toOperationalOwnerReferenceDto(pipelineSystem.operationalOwnerReference()),
                pipelineSystem.createdAt(),
                pipelineSystem.updatedAt());
    }

    private static OrganizationUnitReferenceDto toOperationalOwnerReferenceDto(OperationalOwnerReference reference) {
        if (reference == null) {
            return null;
        }

        return new OrganizationUnitReferenceDto(
                reference.ownerType(),
                reference.ownerId(),
                reference.ownerCode(),
                reference.ownerName());
    }

    private static PageResult<PipelineSystemDto> mapPage(PageResult<PipelineSystem> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(PipelineSystemApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
