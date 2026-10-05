/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 *
 * @Description : Application service for monitoring rules.
 *
 */
package dz.sh.hidra.modules.monitoring.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTelemetryPointReferenceContract;
import dz.sh.hidra.modules.monitoring.application.command.CreateMonitoringRuleCommand;
import dz.sh.hidra.modules.monitoring.application.dto.MonitoringRuleSummaryDto;
import dz.sh.hidra.modules.monitoring.application.mapper.MonitoringApplicationMapper;
import dz.sh.hidra.modules.monitoring.application.port.in.CreateMonitoringRuleUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRule;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringId;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for monitoring rules.
 */
@Service
public final class MonitoringRuleApplicationService implements CreateMonitoringRuleUseCase {

    private final MonitoringRuleRepositoryPort repositoryPort;
    private final MonitoringTelemetryPointReferenceContract telemetryPointReferenceContract;

    @Autowired
    public MonitoringRuleApplicationService(
            MonitoringRuleRepositoryPort repositoryPort,
            MonitoringTelemetryPointReferenceContract telemetryPointReferenceContract
    ) {
        this.repositoryPort = Objects.requireNonNull(
                repositoryPort,
                "Monitoring rule repository port must not be null."
        );
        this.telemetryPointReferenceContract = Objects.requireNonNull(
                telemetryPointReferenceContract,
                "Monitoring TelemetryPoint reference contract must not be null."
        );
    }

    public MonitoringRuleApplicationService(MonitoringRuleRepositoryPort repositoryPort) {
        this(repositoryPort, telemetryPointId -> false);
    }

    @Override
    public MonitoringRuleSummaryDto createMonitoringRule(CreateMonitoringRuleCommand command) {
        Objects.requireNonNull(command, "Create monitoring rule command must not be null.");
        if (command.telemetryPointId() != null
                && !command.telemetryPointId().isBlank()
                && !telemetryPointReferenceContract.exists(command.telemetryPointId())) {
            throw new InvalidMonitoringValueException(
                    "MonitoringRule telemetry point must reference an existing TelemetryPoint."
            );
        }
        Instant now = Instant.now();
        MonitoringRule rule = new MonitoringRule(
                MonitoringId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.ruleType() == null ? MonitoringRuleType.PLAN_COMPARISON : command.ruleType(),
                command.evaluationFrequencyId(),
                command.topologyAssetType(),
                command.topologyAssetId(),
                command.topologyAssetCode(),
                command.telemetryPointId(),
                command.planningTargetTypeId(),
                command.expression(),
                MonitoringLifecycleStatus.DRAFT,
                command.createdByActorId(),
                now,
                now
        );
        return MonitoringApplicationMapper.toSummary(repositoryPort.save(rule));
    }
}
