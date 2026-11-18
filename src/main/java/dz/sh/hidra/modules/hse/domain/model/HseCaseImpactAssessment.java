/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCaseImpactAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 * @Description : Immutable assessment of an enrolled HSE case.
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.HseImpactDomain;
import dz.sh.hidra.modules.hse.domain.value.HseImpactSeverity;
import java.time.Instant;
import java.util.Objects;

public record HseCaseImpactAssessment(String id, String caseId, HseImpactDomain domain,
        String typeId, HseImpactSeverity severity, String description, String actorId, Instant at) {
    public HseCaseImpactAssessment {
        required(id, 80);
        required(caseId, 80);
        required(typeId, 80);
        required(actorId, 80);
        required(description, 500);
        Objects.requireNonNull(domain);
        Objects.requireNonNull(severity);
        Objects.requireNonNull(at);
    }

    private static void required(String value, int maximum) {
        if (value == null || value.isBlank() || value.length() > maximum) {
            throw new IllegalArgumentException("Invalid HSE case impact assessment.");
        }
    }
}
