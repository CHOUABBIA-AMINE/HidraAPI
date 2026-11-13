/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 *
 * @Description : Coordinates scoped evaluation, replay and durable readback.
 *
 */
package dz.sh.hidra.modules.monitoring.application.service;

import dz.sh.hidra.modules.monitoring.application.port.in.AuthoritativeDeviationUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.AuthoritativeDeviationEvidencePort;
import dz.sh.hidra.modules.monitoring.application.port.out.AuthoritativeDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.application.port.out.AuthoritativeDeviationRepositoryPort.Key;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleAuthorizationPort;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleAuthorizationPort.Operation;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRevisionPort;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Outcome;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthoritativeDeviationApplicationService implements AuthoritativeDeviationUseCase {
    private final MonitoringRuleRevisionPort rules;
    private final MonitoringRuleAuthorizationPort authorization;
    private final AuthoritativeDeviationEvidencePort evidence;
    private final AuthoritativeDeviationRepositoryPort receipts;

    public AuthoritativeDeviationApplicationService(MonitoringRuleRevisionPort rules,
            MonitoringRuleAuthorizationPort authorization, AuthoritativeDeviationEvidencePort evidence,
            AuthoritativeDeviationRepositoryPort receipts) {
        this.rules = rules;
        this.authorization = authorization;
        this.evidence = evidence;
        this.receipts = receipts;
    }

    @Override
    @Transactional
    public AuthoritativeDeviationEvaluation evaluate(String ruleId, Request request) {
        var scope = authorize(ruleId, Operation.EVALUATE);
        // The command returns the protected receipt and its evidence.
        authorize(ruleId, Operation.READ);
        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        if (request == null || request.evaluatedAt() == null || request.evaluatedAt().isAfter(now)
                || request.evaluatedAt().getEpochSecond() < 0) {
            throw new IllegalArgumentException("A valid historical or current evaluation instant is required.");
        }
        var key = new Key(ruleId, identifier(request.targetId()), identifier(request.trustedReadingId()),
                request.evaluatedAt().truncatedTo(ChronoUnit.MICROS));
        var prior = receipts.byRequest(key);
        if (prior.isPresent()) return prior.get();

        var rule = rules.effective(ruleId, key.evaluatedAt()).orElseThrow(
                () -> new NoSuchElementException("No effective Monitoring rule revision."));
        if (!scope.company().equals(rule.companyPartyId())) throw unavailable();
        authorization.requirePoint(rule.telemetryPointId(), rule.assetType(), rule.assetId(), rule.unitId(), now);
        var policy = receipts.maximumAgeSeconds(ruleId, rule.revision());
        if (policy.isEmpty() || !rules.policyAvailable(rule.evidencePolicyCode())) {
            return receipts.save(key, receipt(rule, scope.actor(), key, now, Outcome.POLICY_UNAVAILABLE, null, null));
        }

        var target = evidence.target(key.targetId(), rule, key.evaluatedAt()).orElse(null);
        var reading = evidence.observation(key.trustedReadingId(), rule).orElse(null);
        Outcome outcome = classify(rule, key.evaluatedAt(), policy.getAsLong(), target, reading);
        return receipts.save(key, receipt(rule, scope.actor(), key, now, outcome, target, reading));
    }

    private static Outcome classify(MonitoringRuleRevision rule, Instant at, long maximumAge,
            AuthoritativeDeviationEvidencePort.Expected target, AuthoritativeDeviationEvidencePort.Actual reading) {
        if (target == null || reading == null) return Outcome.MISSING_EVIDENCE;
        var observation = reading.provenance();
        if (!reading.qualified() || observation.trustedAt().isAfter(at)) return Outcome.UNCERTAIN;
        if (!rule.telemetryPointId().equals(target.pointId()) || !rule.unitId().equals(target.unitId())
                || !rule.unitId().equals(reading.unitId()) || target.provenance().value() == null
                || observation.value() == null) return Outcome.INCOMPATIBLE;
        if (observation.observedAt().isAfter(at)) return Outcome.UNCERTAIN;
        if (Duration.between(observation.observedAt(), at).compareTo(Duration.ofSeconds(maximumAge)) > 0) {
            return Outcome.STALE;
        }
        return Outcome.COMPUTED;
    }

    private static AuthoritativeDeviationEvaluation receipt(MonitoringRuleRevision rule, String actor,
            Key key, Instant recordedAt, Outcome outcome, AuthoritativeDeviationEvidencePort.Expected target,
            AuthoritativeDeviationEvidencePort.Actual reading) {
        return new AuthoritativeDeviationEvaluation(UUID.randomUUID().toString(), rule.ruleId(), rule.revision(),
                key.evaluatedAt(), recordedAt, actor, rule.unitId(), outcome,
                target == null ? null : target.provenance(), reading == null ? null : reading.provenance());
    }

    @Override
    @Transactional
    public AuthoritativeDeviationEvaluation find(String ruleId, String evaluationId) {
        authorize(ruleId, Operation.READ);
        return receipts.find(ruleId, identifier(evaluationId)).orElseThrow(
                () -> new NoSuchElementException("Protected Monitoring evaluation unavailable."));
    }

    @Override
    @Transactional
    public List<AuthoritativeDeviationEvaluation> history(String ruleId, int limit) {
        authorize(ruleId, Operation.READ);
        if (limit < 1 || limit > 50) throw new IllegalArgumentException("Invalid history limit.");
        return receipts.history(ruleId, limit);
    }

    private MonitoringRuleAuthorizationPort.Scope authorize(String id, Operation operation) {
        var scope = rules.scope(identifier(id)).orElseThrow(AuthoritativeDeviationApplicationService::unavailable);
        return authorization.require(operation, scope.assetType(), scope.assetId(), scope.company());
    }

    private static String identifier(String value) {
        if (value == null || value.isBlank() || value.length() > 80 || !value.equals(value.strip())) {
            throw new IllegalArgumentException("Invalid evidence identifier.");
        }
        return value;
    }

    private static AccessDeniedException unavailable() {
        return new AccessDeniedException("Protected Monitoring rule unavailable.");
    }
}
