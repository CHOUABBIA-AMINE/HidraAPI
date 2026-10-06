/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Applies the Telemetry trust gate and derives downstream values and binding snapshots.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.port.in.TrustTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustEvidencePort;
import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TrustedTelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryTrustPolicy;
import dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrustedTelemetryReadingApplicationService implements TrustTelemetryReadingUseCase {
    private final TelemetryTrustEvidencePort evidence;
    private final TrustedTelemetryReadingRepositoryPort repository;
    public TrustedTelemetryReadingApplicationService(TelemetryTrustEvidencePort evidence,
            TrustedTelemetryReadingRepositoryPort repository) {
        this.evidence = Objects.requireNonNull(evidence);
        this.repository = Objects.requireNonNull(repository);
    }
    @Override
    @Transactional
    public TrustedTelemetryReading trust(String readingId, String assessmentId, String bindingId) {
        if (readingId == null || readingId.isBlank() || assessmentId == null || assessmentId.isBlank()) {
            throw new InvalidTelemetryValueException("Reading and assessment identities are required.");
        }
        Instant trustedAt = Instant.now();
        var facts = evidence.load(readingId.trim(), assessmentId.trim(), trustedAt);
        var raw = facts.reading();
        if (!raw.id().equals(readingId.trim()) || !facts.assessmentId().equals(assessmentId.trim())
                || !raw.id().equals(facts.assessedReadingId()) || !raw.pointId().equals(facts.assessedPointId())) {
            throw new InvalidTelemetryValueException("Reading, point and assessment identities must agree.");
        }
        if (facts.assessmentStatus() != AssessmentStatus.PASSED || !TelemetryTrustPolicy.accepts(facts.trustLevel())) {
            throw new InvalidTelemetryValueException("Trust requires a PASSED assessment with MEDIUM, HIGH or CERTIFIED trust.");
        }
        if (facts.pointStatus() != TelemetryLifecycleStatus.ACTIVE) {
            throw new InvalidTelemetryValueException("Only ACTIVE Telemetry points may produce trusted readings.");
        }
        if (!facts.qualityEligible() || !facts.unitExists() || !facts.batchExists()) {
            throw new InvalidTelemetryValueException("Quality code or optional unit/batch provenance is ineligible.");
        }
        TelemetryTrustEvidencePort.Binding binding = null;
        if (bindingId != null && !bindingId.isBlank()) {
            String selectedId = bindingId.trim();
            binding = facts.bindings().stream().filter(x -> x.id().equals(selectedId)).findFirst()
                    .orElseThrow(() -> new InvalidTelemetryValueException("Selected binding is not active at trust time."));
        } else if (facts.bindings().size() == 1) {
            binding = facts.bindings().get(0);
        } else if (facts.bindings().size() > 1) {
            throw new InvalidTelemetryValueException("Multiple active binding roles require explicit binding selection.");
        }
        return repository.save(new TrustedTelemetryReading(UUID.randomUUID().toString(), raw.id(), raw.pointId(),
                raw.numericValue(), raw.textValue(), raw.booleanValue(), facts.effectiveUnitId(), facts.qualityCodeId(),
                facts.trustLevel(), raw.sourceTimestamp(), trustedAt, facts.assessmentId(),
                binding == null ? null : binding.assetType(), binding == null ? null : binding.assetId(),
                binding == null ? null : binding.assetCode(), binding == null ? null : binding.snapshotId(), raw.ingestionBatchId()));
    }
}
