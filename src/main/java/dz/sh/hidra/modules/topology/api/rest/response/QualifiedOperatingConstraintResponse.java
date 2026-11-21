/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedOperatingConstraintResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Allowlisted pressure restriction response, suppressing hidden values and evidence.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.QualifiedOperatingConstraintReadUseCase.Result;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class QualifiedOperatingConstraintResponse {
    private QualifiedOperatingConstraintResponse() {}
    public record QualifiedOperatingConstraintView(String status, String restrictionClass, String scopeType,
                       String pipelineId, String sectionId, String conditionCode,
                       UUID revisionId, BigDecimal upperPressure, String pressureUnit,
                       String pressureBasis, Instant effectiveFrom, Instant effectiveUntil,
                       UUID sourceBaselineReceiptId, String sourceManifestDigest,
                       String engineeringSourceRevision, String decisionReference) { }
    public static QualifiedOperatingConstraintView of(Result result) {
        if (result.restriction() == null)
            return new QualifiedOperatingConstraintView(result.status().name(), null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null);
        Revision row = result.restriction();
        Scope scope = row.scope();
        Evidence e = row.evidence();
        return new QualifiedOperatingConstraintView(result.status().name(), "UPPER_OPERATING_PRESSURE",
                scope.pipelineLevel() ? "PIPELINE" : "SECTION",
                scope.pipelineId(), scope.sectionId(), scope.conditionCode(),
                row.id(), row.upperPressure(), row.pressureUnit(), row.basis().name(),
                row.effectiveFrom(), row.effectiveUntil(), e.baselineReceiptId(),
                e.baselineDigest(), e.engineeringSourceRevision(), e.decisionActionId());
    }
}
