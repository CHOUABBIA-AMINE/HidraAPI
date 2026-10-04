/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service for pipeline systems.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemSummaryDto;
import dz.sh.hidra.modules.topology.application.mapper.TopologyApplicationMapper;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import dz.sh.hidra.modules.topology.domain.value.TopologyId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import java.time.Instant;
import java.util.Objects;

@Service
public final class PipelineSystemApplicationService implements CreatePipelineSystemUseCase {

    private final PipelineSystemRepositoryPort repositoryPort;

    public PipelineSystemApplicationService(PipelineSystemRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(
                repositoryPort,
                "Pipeline system repository port must not be null."
        );
    }

    public PipelineSystemSummaryDto createPipelineSystem(CreatePipelineSystemCommand command) {
        Objects.requireNonNull(command, "Create pipeline system command must not be null.");

        String systemTypeCode = command.systemTypeCode();
        if (systemTypeCode == null || systemTypeCode.isBlank()) {
            throw new InvalidTopologyValueException(
                    "PipelineSystem system type code must not be blank."
            );
        }

        String normalizedSystemTypeCode = systemTypeCode.trim();
        PipelineSystemType systemType = repositoryPort.findTypeByCode(normalizedSystemTypeCode)
                .orElseThrow(() -> new InvalidTopologyValueException(
                        "Unknown PipelineSystem system type code: " + normalizedSystemTypeCode
                ));

        Instant now = Instant.now();
        PipelineSystem system = new PipelineSystem(
                TopologyId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                systemType,
                TopologyStatus.DRAFT,
                command.description(),
                null,
                null,
                now,
                now
        );

        return TopologyApplicationMapper.toSummary(repositoryPort.save(system));
    }
}
