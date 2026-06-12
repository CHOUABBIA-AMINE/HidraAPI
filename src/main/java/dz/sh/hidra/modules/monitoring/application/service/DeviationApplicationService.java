/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 *
 * @Description : Application service for monitoring deviations.
 *
 */
package dz.sh.hidra.modules.monitoring.application.service;

import dz.sh.hidra.modules.monitoring.application.command.RecordDeviationCommand;
import dz.sh.hidra.modules.monitoring.application.dto.DeviationSummaryDto;
import dz.sh.hidra.modules.monitoring.application.mapper.MonitoringApplicationMapper;
import dz.sh.hidra.modules.monitoring.application.port.in.RecordDeviationUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.PlanActualDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.PlanActualDeviation;
import dz.sh.hidra.modules.monitoring.domain.service.DeviationSeverityClassifier;
import dz.sh.hidra.modules.monitoring.domain.value.DeviationStatus;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for monitoring deviations.
 */
public final class DeviationApplicationService implements RecordDeviationUseCase {

    private final PlanActualDeviationRepositoryPort repositoryPort;
    private final DeviationSeverityClassifier severityClassifier;

    public DeviationApplicationService(
            PlanActualDeviationRepositoryPort repositoryPort,
            DeviationSeverityClassifier severityClassifier
    ) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Plan actual deviation repository port must not be null.");
        this.severityClassifier = Objects.requireNonNull(severityClassifier, "Deviation severity classifier must not be null.");
    }

    @Override
    public DeviationSummaryDto recordDeviation(RecordDeviationCommand command) {
        Objects.requireNonNull(command, "Record deviation command must not be null.");
        PlanActualDeviation deviation = new PlanActualDeviation(
                MonitoringId.newId().value(),
                command.evaluationId(),
                command.planTargetId(),
                command.expectedFlowStateId(),
                command.trustedTelemetryReadingId(),
                command.telemetryPointId(),
                command.topologyAssetType(),
                command.topologyAssetId(),
                command.topologyAssetCode(),
                command.actualValue(),
                command.expectedValue(),
                command.differenceValue(),
                command.differencePercent(),
                command.unitId(),
                command.severity() == null ? severityClassifier.classify(command.differencePercent()) : command.severity(),
                DeviationStatus.OPEN,
                Instant.now(),
                null,
                command.reasonCode(),
                command.reasonMessage()
        );
        return MonitoringApplicationMapper.toSummary(repositoryPort.save(deviation));
    }
}
