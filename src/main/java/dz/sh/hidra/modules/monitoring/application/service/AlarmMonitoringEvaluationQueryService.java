/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmMonitoringEvaluationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 *
 * @Description : Checks Monitoring owner authority and exports a scalar deviation projection.
 *
 */
package dz.sh.hidra.modules.monitoring.application.service;

import dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract;
import dz.sh.hidra.modules.monitoring.application.port.in.AuthoritativeDeviationUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.AuthoritativeDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRevisionPort;
import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** A global receipt ID is resolved inside its owner; no foreign repository imports. */
@Service
public final class AlarmMonitoringEvaluationQueryService implements AlarmMonitoringEvaluationContract {
    private final AuthoritativeDeviationUseCase deviations;
    private final AuthoritativeDeviationRepositoryPort receipts;
    private final MonitoringRuleRevisionPort rules;

    public AlarmMonitoringEvaluationQueryService(AuthoritativeDeviationUseCase deviations,
            AuthoritativeDeviationRepositoryPort receipts, MonitoringRuleRevisionPort rules) {
        this.deviations = deviations;
        this.receipts = receipts;
        this.rules = rules;
    }

    @Override
    @Transactional(readOnly = true)
    public Evaluation authorized(String evaluationId) {
        if (evaluationId == null || evaluationId.isBlank() || evaluationId.length() > 80
                || !evaluationId.equals(evaluationId.strip())) throw unavailable();
        // Lookup is internal and discloses nothing. The subsequent owner use case checks
        // the exact current Monitoring READ grant before returning the actual receipt.
        String ruleId = receipts.ruleIdByEvaluationId(evaluationId).orElseThrow(
                AlarmMonitoringEvaluationQueryService::unavailable);
        var evaluation = deviations.find(ruleId, evaluationId);
        var scope = rules.scope(ruleId).orElseThrow(AlarmMonitoringEvaluationQueryService::unavailable);
        var revision = rules.exact(ruleId, evaluation.ruleRevision())
                .orElseThrow(AlarmMonitoringEvaluationQueryService::unavailable);
        if (!scope.company().equals(revision.companyPartyId())
                || !scope.assetType().equals(revision.assetType())
                || !scope.assetId().equals(revision.assetId())) throw unavailable();
        var target = evaluation.target();
        var observation = evaluation.observation();
        return new Evaluation(evaluation.id(), ruleId, evaluation.ruleRevision(),
                scope.company(), scope.assetType(), scope.assetId(), revision.telemetryPointId(),
                evaluation.unitId(), evaluation.outcome().name(),
                evaluation.actual(), evaluation.expected(), evaluation.difference(),
                target == null ? null : target.id(), target == null ? null : target.approvalId(),
                observation == null ? null : observation.id(),
                evaluation.evaluatedAt(), evaluation.recordedAt());
    }

    private static AccessDeniedException unavailable() {
        return new AccessDeniedException("Protected Monitoring evaluation unavailable.");
    }
}
