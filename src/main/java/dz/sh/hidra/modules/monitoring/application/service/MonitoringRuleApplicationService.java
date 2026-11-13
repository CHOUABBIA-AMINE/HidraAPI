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
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleAuthorizationPort;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRevisionPort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
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
public class MonitoringRuleApplicationService implements CreateMonitoringRuleUseCase {

    private final MonitoringRuleRepositoryPort repositoryPort;
    private final MonitoringTelemetryPointReferenceContract telemetryPointReferenceContract;
    private final MonitoringRuleAuthorizationPort authorization;
    private final MonitoringRuleRevisionPort revisions;

    @Autowired
    public MonitoringRuleApplicationService(MonitoringRuleRepositoryPort repositoryPort,
            MonitoringTelemetryPointReferenceContract telemetryPointReferenceContract,
            MonitoringRuleAuthorizationPort authorization,MonitoringRuleRevisionPort revisions) {
        this.repositoryPort=Objects.requireNonNull(repositoryPort);
        this.telemetryPointReferenceContract=Objects.requireNonNull(telemetryPointReferenceContract);
        this.authorization=Objects.requireNonNull(authorization);
        this.revisions=Objects.requireNonNull(revisions);
    }
    // Tests using legacy JVM constructors have no native permission and remain fail-closed.
    public MonitoringRuleApplicationService(MonitoringRuleRepositoryPort repositoryPort,
            MonitoringTelemetryPointReferenceContract telemetryPointReferenceContract) {
        this.repositoryPort=Objects.requireNonNull(repositoryPort);
        this.telemetryPointReferenceContract=Objects.requireNonNull(telemetryPointReferenceContract);
        this.authorization=null;
        this.revisions=null;
    }

    public MonitoringRuleApplicationService(MonitoringRuleRepositoryPort repositoryPort) {
        this(repositoryPort, telemetryPointId -> false);
    }

    @Override
    @Transactional
    public MonitoringRuleSummaryDto createMonitoringRule(CreateMonitoringRuleCommand command) {
        Objects.requireNonNull(command, "Create monitoring rule command must not be null.");
        // Only the direct legacy test constructor evaluates an unknown point before native authority.
        if (authorization == null && command.telemetryPointId() != null
                && !command.telemetryPointId().isBlank()
                && !telemetryPointReferenceContract.exists(command.telemetryPointId())) {
            throw new InvalidMonitoringValueException(
                    "MonitoringRule telemetry point must reference an existing TelemetryPoint."
            );
        }
        if (authorization == null || revisions == null)
            throw new AccessDeniedException("Protected Monitoring rule unavailable.");
        var scope=authorization.require(MonitoringRuleAuthorizationPort.Operation.CREATE,
                command.topologyAssetType(),command.topologyAssetId(),null);
        if (command.telemetryPointId() != null && !command.telemetryPointId().isBlank()
                && !telemetryPointReferenceContract.exists(command.telemetryPointId()))
            throw new InvalidMonitoringValueException("MonitoringRule telemetry point must reference an existing TelemetryPoint.");
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
                scope.actor(),
                now,
                now
        );
        var saved=repositoryPort.save(rule);
        revisions.register(saved.id(),scope.company(),saved.topologyAssetType(),saved.topologyAssetId(),scope.actor());
        return MonitoringApplicationMapper.toSummary(saved);
    }
}
