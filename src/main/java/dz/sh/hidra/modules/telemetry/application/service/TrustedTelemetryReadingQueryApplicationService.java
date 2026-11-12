/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Serializes immutable trusted publication and authorizes protected readback.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.port.in.TrustTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.TrustedTelemetryReadingQueryUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingScopeAuthorizationPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustedReadPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustedReadPort.Published;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrustedTelemetryReadingQueryApplicationService implements TrustedTelemetryReadingQueryUseCase {
    private final TelemetryQualityAssessmentPort assessment;
    private final TelemetryReadingScopeAuthorizationPort security;
    private final TelemetryTrustedReadPort published;
    private final TrustTelemetryReadingUseCase trust;
    public TrustedTelemetryReadingQueryApplicationService(TelemetryQualityAssessmentPort assessment,
            TelemetryReadingScopeAuthorizationPort security, TelemetryTrustedReadPort published,
            TrustTelemetryReadingUseCase trust) {
        this.assessment = Objects.requireNonNull(assessment);
        this.security = Objects.requireNonNull(security);
        this.published = Objects.requireNonNull(published);
        this.trust = Objects.requireNonNull(trust);
    }
    @Override
    @Transactional
    public Published publish(String company, String point, String reading) {
        var scope = assessment.current(point, Instant.now());
        security.requireActor(scope, TelemetryReadingScopeAuthorizationPort.Operation.QUALIFY, company);
        // Serializes two publishers on the raw row, without altering immutable ingestion receipts.
        assessment.lock(point, reading);
        var current = published.byRaw(reading);
        if (current.isPresent()) {
            if (!current.get().reading().pointId().equals(point)) throw new InvalidTelemetryValueException("Protected telemetry reading unavailable.");
            var old = current.get();
            return new Published(old.reading(), old.reasonCode(), old.receivedAt(), old.assessedAt(), true);
        }
        var quality = assessment.find(point, reading);
        if (quality.status() != AssessmentStatus.PASSED) {
            throw new InvalidTelemetryValueException("Reading quality does not permit trusted publication.");
        }
        assessment.requireCurrentEligibility(scope, quality, Instant.now());
        // The existing HMR-053 trigger independently rechecks PASSED evidence and binding.
        var saved = trust.trust(reading, quality.id(), null);
        if (saved.bindingId() == null) {
            throw new InvalidTelemetryValueException("Commissioned binding required.");
        }
        return published.byRaw(reading).orElseThrow(
                () -> new IllegalStateException("Trusted evidence was not persisted."));
    }
    @Override
    @Transactional
    public Published find(String company, String point, String id) {
        var scope = assessment.current(point, Instant.now());
        security.requireActor(scope, TelemetryReadingScopeAuthorizationPort.Operation.TRUSTED_READ, company);
        return published.byId(point, id).orElseThrow(
                () -> new java.util.NoSuchElementException("Protected telemetry reading unavailable."));
    }
    @Override
    @Transactional
    public List<Published> history(String company, String point, int limit) {
        if (limit < 1 || limit > 50) throw new InvalidTelemetryValueException("Invalid bounded history limit.");
        var scope = assessment.current(point, Instant.now());
        security.requireActor(scope, TelemetryReadingScopeAuthorizationPort.Operation.TRUSTED_READ, company);
        return published.history(point, limit);
    }
}
