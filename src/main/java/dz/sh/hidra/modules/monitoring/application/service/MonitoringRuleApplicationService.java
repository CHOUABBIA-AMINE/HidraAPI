/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.monitoring.application.command.CreateMonitoringRuleCommand;
import dz.sh.hidra.modules.monitoring.application.dto.MonitoringRuleSummaryDto;
import dz.sh.hidra.modules.monitoring.application.mapper.MonitoringApplicationMapper;
import dz.sh.hidra.modules.monitoring.application.port.in.CreateMonitoringRuleUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRule;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringId;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for monitoring rules.
 */
public final class MonitoringRuleApplicationService implements CreateMonitoringRuleUseCase {

    private final MonitoringRuleRepositoryPort repositoryPort;

    public MonitoringRuleApplicationService(MonitoringRuleRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Monitoring rule repository port must not be null.");
    }

    @Override
    public MonitoringRuleSummaryDto createMonitoringRule(CreateMonitoringRuleCommand command) {
        Objects.requireNonNull(command, "Create monitoring rule command must not be null.");
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
