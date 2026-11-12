/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityAssessmentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Authorizes assessment and immutable evidence readback.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.port.in.QualifyTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort.Assessment;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingScopeAuthorizationPort;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class TelemetryQualityAssessmentApplicationService implements QualifyTelemetryReadingUseCase {
    private final TelemetryQualityAssessmentPort store;
    private final TelemetryReadingScopeAuthorizationPort security;
    public TelemetryQualityAssessmentApplicationService(TelemetryQualityAssessmentPort store,
            TelemetryReadingScopeAuthorizationPort security) {
        this.store = Objects.requireNonNull(store);
        this.security = Objects.requireNonNull(security);
    }
    @Override
    @Transactional
    public Assessment qualify(String company, String point, String reading) {
        Instant at = Instant.now().truncatedTo(ChronoUnit.MICROS);
        var scope = store.current(point, at);
        String actor = security.requireActor(scope,
                TelemetryReadingScopeAuthorizationPort.Operation.QUALIFY, company);
        return store.assess(scope, reading, actor, at);
    }
    @Override
    @Transactional
    public Assessment find(String company, String point, String reading) {
        var scope = store.current(point, Instant.now());
        security.requireActor(scope, TelemetryReadingScopeAuthorizationPort.Operation.TRUSTED_READ, company);
        return store.find(point, reading);
    }
}
