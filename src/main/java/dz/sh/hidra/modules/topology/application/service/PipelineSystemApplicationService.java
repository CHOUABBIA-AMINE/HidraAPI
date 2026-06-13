/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.util.Objects;
@Service
public final class PipelineSystemApplicationService implements CreatePipelineSystemUseCase {
    private final PipelineSystemRepositoryPort repositoryPort;
    public PipelineSystemApplicationService(PipelineSystemRepositoryPort repositoryPort) { this.repositoryPort = Objects.requireNonNull(repositoryPort, "Pipeline system repository port must not be null."); }
    public PipelineSystemSummaryDto createPipelineSystem(CreatePipelineSystemCommand command) {
        Instant now = Instant.now();
        PipelineSystem system = new PipelineSystem(TopologyId.newId().value(), command.code(), command.nameAr(), command.nameFr(), command.nameEn(), command.systemType() == null ? PipelineSystemType.TRANSPORT : command.systemType(), TopologyStatus.DRAFT, command.description(), null, null, now, now);
        return TopologyApplicationMapper.toSummary(repositoryPort.save(system));
    }
}
